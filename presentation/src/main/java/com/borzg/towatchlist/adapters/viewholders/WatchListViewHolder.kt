package com.borzg.towatchlist.adapters.viewholders

import android.animation.Animator
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.borzg.domain.model.common.CinemaElement
import com.borzg.towatchlist.R
import kotlinx.coroutines.*

abstract class WatchListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewHolderJob = Job()
    private val viewHolderScope = CoroutineScope(Dispatchers.Main + viewHolderJob)
    var job: Job? = null

    abstract fun bind(
        cinemaElement: CinemaElement,
        onListItemClickListener: (CinemaElement) -> Unit,
        onAddButtonClickListener: suspend (CinemaElement, Boolean) -> Unit
    )

    private fun Button.animateAddToWatchListButtonChanges(isWatched: Boolean) {
        this.animateButtonChanges(defineAddToWatchListButtonText(isWatched, this))
    }

    private fun Button.setAddToWatchListButtonText(isWatched: Boolean) {
        this.text = defineAddToWatchListButtonText(isWatched, this)
    }

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
        if (isWatched) button.context.getString(R.string.watched) else button.context.getString(R.string.mark_as_watched)


    private fun Button.animateButtonChanges(newText: String) {
        val animation = this.animate().alpha(0f).setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                this@animateButtonChanges.text = newText
                val endAnimation = this@animateButtonChanges.animate().alpha(1f)
                endAnimation.duration = 100
                endAnimation.start()
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
        animation.duration = 100
        animation.start()
    }
}