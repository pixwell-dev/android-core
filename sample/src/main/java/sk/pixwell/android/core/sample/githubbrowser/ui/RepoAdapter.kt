package sk.pixwell.android.core.sample.githubbrowser.ui

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repo.*
import sk.pixwell.android.core.sample.githubbrowser.R
import sk.pixwell.android.core.sample.githubbrowser.domain.model.OwnerModel
import sk.pixwell.android.core.sample.githubbrowser.domain.model.RepoModel
import sk.pixwell.android.core.ui.widget.inflate
import sk.pixwell.android.core.ui.widget.loadImageAsync

class RepoAdapter
    : PagedListAdapter<RepoModel, RepoAdapter.RepoViewHolder>(POST_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(parent.inflate(R.layout.item_repo))
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val value = getItem(position)
        if (value != null) {
            holder.bind(value)
        } else {
            holder.bind(RepoModel("1", OwnerModel(1234,
                    "bardzo12",
                    "http://www.travelcontinuously.com/wp-content/uploads/2018/04/empty-avatar.png",
                    "http://www.travelcontinuously.com/wp-content/uploads/2018/04/empty-avatar.png"), "Albert Hladky", "Short description", "http://www.travelcontinuously.com/wp-content/uploads/2018/04/empty-avatar.png"))
        }
    }

    inner class RepoViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(repo: RepoModel?) {
            name.text = repo?.fullName
            description.text = repo?.description
            uuid.text = repo?.viewId
            avatar.loadImageAsync(repo?.owner?.avatarUrl)
        }
    }

    companion object {
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<RepoModel>() {
            override fun areContentsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean =
                    oldItem.description == newItem.description

            override fun getChangePayload(oldItem: RepoModel, newItem: RepoModel): Any? {
                return if (sameExceptScore(oldItem, newItem)) {
                    PAYLOAD_SCORE
                } else {
                    null
                }
            }
        }

        private fun sameExceptScore(oldItem: RepoModel, newItem: RepoModel): Boolean {
            return oldItem.copy(url = newItem.url) == newItem
        }
    }
}