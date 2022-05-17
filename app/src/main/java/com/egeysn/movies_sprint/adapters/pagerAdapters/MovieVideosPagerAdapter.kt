package com.egeysn.movies_sprint.adapters.pagerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egeysn.movies_sprint.data.general.MovieVideosItem
import com.egeysn.movies_sprint.databinding.MovieVideosListItemBinding
import com.egeysn.movies_sprint.ui.movieDetail.MovieDetailViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MovieVideosPagerAdapter(
    private val viewModel: MovieDetailViewModel,
    private val items: List<MovieVideosItem?>
) :
    ListAdapter<MovieVideosItem, MovieVideosPagerAdapter.ViewHolder>(TaskDiffMovieVideosItemCallback()) {

    private var listener: MovieTrailersListener? = null

    fun setListener(listener: MovieTrailersListener) {
        this.listener = listener
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieVideosListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder constructor(private val binding: MovieVideosListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: MovieDetailViewModel, item: MovieVideosItem?) {

            if (item == null) return

        /*    val params = binding.youtubePlayerView.layoutParams as LinearLayout.LayoutParams
            val width = viewModel.utils.screenWidth()
            params.width = width
            params.height = (width * (1.5)).toInt()
            binding.youtubePlayerView.layoutParams = params*/

            binding.youtubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val videoId = item.key ?: ""
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                })



        }
    }
}
interface MovieTrailersListener {
    fun onItemClicked(youTubePlayer: YouTubePlayerView)
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class TaskDiffMovieVideosItemCallback : DiffUtil.ItemCallback<MovieVideosItem>() {
    override fun areItemsTheSame(oldItem: MovieVideosItem, newItem: MovieVideosItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieVideosItem, newItem: MovieVideosItem): Boolean {
        return oldItem == newItem
    }
}
