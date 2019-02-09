package pro.jeminay.tochka.activities.main

import android.view.MenuItem
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.android.material.navigation.NavigationView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pro.jeminay.tochka.R
import pro.jeminay.tochka.api.GithubApiService
import pro.jeminay.tochka.api.responses.GithubUser
import pro.jeminay.tochka.auth.AuthManager
import pro.jeminay.tochka.data.UserRepo
import pro.jeminay.tochka.utils.SingleLiveEvent
import pro.jeminay.tochka.utils.toObservable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(
    authManager: AuthManager,
    githubApiService: GithubApiService,
    private val userRepository: UserRepo
) : ViewModel(), GithubUsersListManager.Listener, NavigationView.OnNavigationItemSelectedListener {

    val requestFailedEvent = SingleLiveEvent<Unit>()

    val logoutEvent = SingleLiveEvent<Unit>()

    val user = authManager.requireUser()

    val searchQuery = ObservableField<String>("")

    val adapter = GithubUsersAdapter()

    val githubInfiniteScroll = GithubUsersListManager(githubApiService, this)

    private val composite = CompositeDisposable()

    init {
        composite.addAll(
            searchQuery.toObservable()
                .subscribeOn(Schedulers.computation())
                .throttleFirst(600L, TimeUnit.MILLISECONDS)
                .filter { it.isNotEmpty() }
                .distinct()
                .subscribe(githubInfiniteScroll::refresh)
        )
    }

    override fun onRefresh(data: List<GithubUser>) {
        adapter.replaceAll(data)
    }

    override fun onLoadMore(data: List<GithubUser>) {
        adapter.addAll(data)
    }

    override fun onLoadFailed(t: Throwable) {
        requestFailedEvent.call()
    }

    override fun query(): String? = searchQuery.get()

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_logout) {
            composite.add(
                userRepository.deleteUser()
                    .doOnSuccess { logoutEvent.call() }
                    .subscribe()
            )

            return true
        }

        return false
    }

    override fun onCleared() {
        githubInfiniteScroll.clear()
        composite.clear()
    }
}