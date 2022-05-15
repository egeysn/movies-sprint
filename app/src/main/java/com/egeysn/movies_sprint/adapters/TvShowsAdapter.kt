package com.egeysn.movies_sprint.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.egeysn.movies_sprint.BuildConfig
import com.egeysn.movies_sprint.R
import com.egeysn.movies_sprint.data.general.ResultsItem
import com.egeysn.movies_sprint.databinding.MoviesItemBinding
import com.egeysn.movies_sprint.utils.GlideHelper

class TvShowsAdapter(
    private val context: Context,
    private val items: List<ResultsItem>
) :
    ListAdapter<ResultsItem, TvShowsAdapter.ViewHolder>(PersonTaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, items[position], items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = items.size

    class ViewHolder private constructor(private val binding: MoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: ResultsItem, size: Int) {

            val params = binding.root.layoutParams as RecyclerView.LayoutParams
            when (bindingAdapterPosition) {
                0 -> {
                    params.leftMargin =
                        dpToPx(binding.root.context, 15)
                    binding.root.layoutParams = params
                }
                size - 1 -> {
                    params.leftMargin =
                        dpToPx(binding.root.context, 15)
                    params.rightMargin =
                        dpToPx(binding.root.context, 15)
                    binding.root.layoutParams = params
                }
                else -> {
                    params.leftMargin = 30
                    binding.root.layoutParams = params
                }
            }

            GlideHelper.loadImage(context, item.poster_path, binding.imageIv)

            binding.root.setOnClickListener { }
        }

        private fun dpToPx(context: Context, dp: Int): Int {
            val dip: Float = dp.toFloat()
            val scale = context.resources.displayMetrics.density
            return (dip * scale + 0.5f).toInt()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MoviesItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}
