package sk.pixwell.android.core.ui.widget

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import sk.pixwell.android.core.domain.model.UiModel

interface AutoUpdatableAdapter {
    fun <T> RecyclerView.Adapter<*>.autoNotify(
        old: List<T>,
        new: List<T>,
        updateCallback: ListUpdateCallback? = null
    ) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = old[oldItemPosition]
                val newItem = new[newItemPosition]
                return when {
                    oldItem is UiModel && newItem is UiModel -> oldItem.viewId == newItem.viewId
                    else -> oldItem == newItem
                }
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return old[oldItemPosition] == new[newItemPosition]
            }

            override fun getOldListSize() = old.size

            override fun getNewListSize() = new.size
        })

        if (updateCallback != null) {
            diff.dispatchUpdatesTo(updateCallback)
        } else {
            diff.dispatchUpdatesTo(this)
        }
    }
}
