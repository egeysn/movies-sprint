package com.egeysn.movies_sprint.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.egeysn.movies_sprint.BuildConfig
import com.egeysn.movies_sprint.R
import com.egeysn.movies_sprint.data.general.ResultsItem
import com.egeysn.movies_sprint.databinding.PopularMoviesItemBinding

class PopularMoviesAdapter(
    private val context: Context,
    private val items: List<ResultsItem>
) :
    ListAdapter<ResultsItem, PopularMoviesAdapter.ViewHolder>(PersonTaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, items[position], items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = items.size

    class ViewHolder private constructor(private val binding: PopularMoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: ResultsItem, size: Int) {

            val params = binding.root.layoutParams as RecyclerView.LayoutParams
            val imageWidth = dpToPx(context, 110f)
            binding.imageCv.layoutParams = LinearLayout.LayoutParams(imageWidth, (imageWidth * (1.5)).toInt())

            when (bindingAdapterPosition) {
                0 -> {
                    params.topMargin =
                        dpToPx(binding.root.context, 15f)
                    params.bottomMargin = 0
                    binding.root.layoutParams = params
                }
                size - 1 -> {
                    params.topMargin = 0
                    params.bottomMargin =
                        dpToPx(binding.root.context, 30f)
                    binding.root.layoutParams = params
                }
                else -> {
                    params.topMargin = dpToPx(binding.root.context, 10f)
                    params.bottomMargin = 0
                    binding.root.layoutParams = params
                }
            }
            binding.titleTv.text = item.original_title
            binding.voteTv.text = "Point: ${item.vote_average}"

            // create a ProgressDrawable object which we will show as placeholder
            val progress = CircularProgressDrawable(binding.root.context)
            progress.setColorSchemeColors(
                R.color.red_accent,
            )
            progress.centerRadius = 30f
            progress.strokeWidth = 5f
            progress.start()

            Glide.with(context)
                .load(BuildConfig.BASE_IMAGE_URL + item.poster_path)
                .placeholder(progress)
                .centerCrop()
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(binding.imageIv)

            binding.root.setOnClickListener {
            }
        }

        private fun dpToPx(context: Context, dp: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PopularMoviesItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}
