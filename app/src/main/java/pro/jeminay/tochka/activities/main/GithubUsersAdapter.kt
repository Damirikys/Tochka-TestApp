package pro.jeminay.tochka.activities.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pro.jeminay.tochka.R
import pro.jeminay.tochka.api.responses.GithubUser
import pro.jeminay.tochka.databinding.GithubUserItemBinding

class GithubUsersAdapter : RecyclerView.Adapter<GithubUsersAdapter.ViewHolder>() {

    private val results: MutableList<GithubUser> = mutableListOf()

    fun replaceAll(data: List<GithubUser>) {
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }

    fun addAll(data: List<GithubUser>) {
        val lastPosition = Math.max(results.size, 0)
        results.addAll(data)
        notifyItemRangeInserted(lastPosition, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.github_user_item,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(results[position])

    inner class ViewHolder(
        private val binding: GithubUserItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: GithubUser) {
            binding.user = user
            binding.executePendingBindings()

            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 400
            binding.root.startAnimation(anim)
        }
    }
}