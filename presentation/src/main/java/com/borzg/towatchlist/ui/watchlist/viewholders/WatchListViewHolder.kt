package com.borzg.towatchlist.ui.watchlist.viewholders

import android.view.View
import android.widget.Button
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.borzg.domain.model.CinemaElement
import com.borzg.towatchlist.R
import kotlinx.coroutines.*

abstract class WatchListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewHolderJob = Job()
    private val viewHolderScope = CoroutineScope(Dispatchers.Main + viewHolderJob)
    var job: Job? = null

    var currentCinemaElement: CinemaElement? = null
    var onSwipeListener: ((CinemaElement) -> Unit)? = null

    abstract fun bind(
        cinemaElement: CinemaElement,
        onListItemClickListener: (CinemaElement) -> Unit,
        onAddButtonClickListener: suspend (CinemaElement, Boolean) -> Unit
    )

    private fun Button.animateAddToWatchListButtonChanges(isWatched: Boolean) {
        this.animateButtonTextChanges(defineAddToWatchListButtonText(isWatched, this))
    }

    private fun Button.setAddToWatchListButtonText(isWatched: Boolean) {
        text = defineAddToWatchListButtonText(isWatched, this)
    }

    fun invokeSwipe() {
        currentCinemaElement?.let { onSwipeListener?.invoke(it) }
    }

    fun ViewBinding.getString(@StringRes id: Int): String =
        root.resources.getString(id)

    fun bindWatchStateButton(
        button: Button,
        cinemaElement: CinemaElement,
        onAddButtonClickListener: suspend (CinemaElement, Boolean) -> Unit,
    ) {
        var isWatched = cinemaElement.isWatched
        button.setAddToWatchListButtonText(isWatched)
        button.setOnClickListener {
            job?.cancel()
            job = viewHolderScope.launch {
                isWatched = !isWatched
                (it as Button).animateAddToWatchListButtonChanges(isWatched)
                delay(300)
                onAddButtonClickListener.invoke(cinemaElement, isWatched)
            }
        }
    }

    private fun defineAddToWatchListButtonText(isWatched: Boolean, button: Button): String =
        if (isWatched) button.context.getString(R.string.viewed) else button.context.getString(R.string.mark_as_viewed)


    private fun Button.animateButtonTextChanges(newText: String) {
        val animation = this.animate().alpha(0f).withEndAction {
            text = newText
            val endAnimation = animate().alpha(1f)
            endAnimation.duration = 100
            endAnimation.start()
        }
        animation.duration = 100
        animation.start()
    }
}