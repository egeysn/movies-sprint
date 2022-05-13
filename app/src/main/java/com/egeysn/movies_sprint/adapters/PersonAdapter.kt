package com.egeysn.movies_sprint.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.egeysn.movies_sprint.BuildConfig
import com.egeysn.movies_sprint.R
import com.egeysn.movies_sprint.data.general.ResultsItem
import com.egeysn.movies_sprint.databinding.PersonsItemBinding


class PersonAdapter(
    private val items: List<ResultsItem>
) :
    ListAdapter<ResultsItem, PersonAdapter.ViewHolder>(PersonTaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], items.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = items.size

    class ViewHolder private constructor(private val binding: PersonsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResultsItem, size: Int) {

            val params = binding.root.layoutParams as RecyclerView.LayoutParams
            when (bindingAdapterPosition) {
                0 -> {
                    params.leftMargin =
                        dpToPx(binding.root.context, 10)
                    binding.root.layoutParams = params
                }
                size - 1 -> {
                    params.rightMargin =
                        dpToPx(binding.root.context, 20)
                    binding.root.layoutParams = params
                }
                else -> {
                    params.leftMargin = 20
                    binding.root.layoutParams = params
                }
            }

            // create a ProgressDrawable object which we will show as placeholder
            val progress = CircularProgressDrawable(binding.root.context)
            progress.setColorSchemeColors(
                R.color.secondary,
            )
            progress.centerRadius = 30f
            progress.strokeWidth = 5f
            progress.start()

            Glide.with(binding.root.context)
                .load(BuildConfig.BASE_IMAGE_URL + item.profile_path)
                .placeholder(progress)
                .centerCrop()
                .into(binding.imageIv)
        }

        private fun dpToPx(context: Context, dp: Int): Int {
            val dip: Float = dp.toFloat()
            val scale = context.resources.displayMetrics.density
            return (dip * scale + 0.5f).toInt()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PersonsItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class PersonTaskDiffCallback : DiffUtil.ItemCallback<ResultsItem>() {
    override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem == newItem
    }
}
