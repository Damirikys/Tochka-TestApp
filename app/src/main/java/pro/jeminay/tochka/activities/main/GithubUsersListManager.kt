package pro.jeminay.tochka.activities.main

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pro.jeminay.tochka.api.GithubApiService
import pro.jeminay.tochka.api.responses.GithubUser
import pro.jeminay.tochka.interfaces.OnScrollListener

class GithubUsersListManager(
    private val githubApiService: GithubApiService,
    private val listener: Listener
) : OnScrollListener {

    var yPosition = 0

    private var isCanLoadMore = true

    private val scrollThreshold = 3

    private var currentPage = 1

    private val composite = CompositeDisposable()

    override fun onScroll(recyclerView: RecyclerView) {
        val layoutManager =  recyclerView.layoutManager as LinearLayoutManager
        yPosition = layoutManager.findFirstCompletelyVisibleItemPosition()

        if (isCanLoadMore.not()) return

        val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
        if (lastVisiblePosition == layoutManager.itemCount - scrollThreshold - 1) {
            fetchNextPage()
        }
    }

    fun refresh(query: String) {
        currentPage = 1

        composite.add(
            githubApiService.searchUsers(query, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listener.onRefresh(it.items) }, { listener.onLoadFailed(it) })
        )
    }

    fun clear() {
        composite.clear()
    }

    private fun fetchNextPage() {
        val query = listener.query() ?: return

        isCanLoadMore = false

        composite.add(
            githubApiService.searchUsers(query, ++currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { isCanLoadMore = true }
                .subscribe({ listener.onLoadMore(it.items) }, { listener.onLoadFailed(it) })
        )
    }

    interface Listener {
        fun onRefresh(data: List<GithubUser>)
        fun onLoadMore(data: List<GithubUser>)
        fun onLoadFailed(t: Throwable)
        fun query(): String?
    }
}