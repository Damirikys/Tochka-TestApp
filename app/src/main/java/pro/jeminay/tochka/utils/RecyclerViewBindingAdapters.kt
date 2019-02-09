package pro.jeminay.tochka.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import pro.jeminay.tochka.interfaces.OnScrollListener

@BindingAdapter(value = [
    "app:adapter",
    "app:layoutManager"
], requireAll = true)
fun setRecyclerViewAdapterAndLayoutManager(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<*>,
    layoutManager: RecyclerView.LayoutManager
) {
    recyclerView.swapAdapter(adapter, true)
    recyclerView.layoutManager = layoutManager
}

@BindingAdapter("app:scrollListener")
fun setRecyclerViewOnScrollListener(recyclerView: RecyclerView, listener: OnScrollListener) {
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            listener.onScroll(recyclerView)
        }
    })
}

@BindingAdapter("app:yPosition")
fun setRecyclerViewScrollYPosition(recyclerView: RecyclerView, position: Int) {
    recyclerView.scrollToPosition(position)
}