package sk.pixwell.android.core.sample.githubbrowser.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repo.*
import sk.pixwell.android.core.sample.githubbrowser.R
import sk.pixwell.android.core.sample.githubbrowser.domain.model.RepoModel
import sk.pixwell.android.core.ui.widget.AutoUpdatableAdapter
import sk.pixwell.android.core.ui.widget.inflate
import sk.pixwell.android.core.ui.widget.loadImageAsync
import kotlin.properties.Delegates

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>(), AutoUpdatableAdapter {
    var items: List<RepoModel> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new)
    }

    override fun getItemCount() = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(parent.inflate(R.layout.item_repo))
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class RepoViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(repo: RepoModel) {
            name.text = repo.fullName
            description.text = repo.description
            avatar.loadImageAsync(repo.owner.avatarUrl)
        }
    }
}