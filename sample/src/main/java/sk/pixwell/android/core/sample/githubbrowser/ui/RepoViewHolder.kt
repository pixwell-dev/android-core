package sk.pixwell.android.core.sample.githubbrowser.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repo.*
import sk.pixwell.android.core.sample.githubbrowser.R
import sk.pixwell.android.core.sample.githubbrowser.domain.model.RepoModel
import sk.pixwell.android.core.ui.widget.loadImageAsync

class RepoViewHolder(
        override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(repo: RepoModel?) {
        name.text = repo?.fullName
        description.text = repo?.description
        avatar.loadImageAsync(repo?.owner?.avatarUrl)
    }


    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_repo, parent, false)
            return RepoViewHolder(view)
        }
    }
}