package com.egeysn.movies_sprint.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egeysn.movies_sprint.data.general.CastItem
import com.egeysn.movies_sprint.databinding.CastListItemBinding
import com.egeysn.movies_sprint.utils.GeneralUtils
import com.egeysn.movies_sprint.utils.GlideHelper

class CastsAdapter(
    private val context: Context,
    private val items: List<CastItem>,
    var listener: CastItemListener?,
) :
    ListAdapter<CastItem, CastsAdapter.ViewHolder>(CastsTaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CastListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder constructor(private val binding: CastListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CastItem, size: Int) {

            val utils = GeneralUtils.getInstance(context)
            val params = binding.root.layoutParams as RecyclerView.LayoutParams
            val imageWidth = utils.dpToPx(110f)
            binding.profileCv.layoutParams =
                LinearLayout.LayoutParams(imageWidth, (imageWidth * (1.5)).toInt())

            when (bindingAdapterPosition) {
                0 -> {
                    params.leftMargin =
                        utils.dpToPx(20f)
                    params.rightMargin = 0
                    binding.root.layoutParams = params
                }
                size - 1 -> {
                    params.rightMargin = utils.dpToPx(20f)
                    params.leftMargin =
                        utils.dpToPx(10f)
                    binding.root.layoutParams = params
                }
                else -> {
                    params.leftMargin = utils.dpToPx(10f)
                    params.rightMargin = 0
                    binding.root.layoutParams = params
                }
            }
            binding.apply {
                GlideHelper.loadImage(context, item.profile_path, profileIv)

                originalNameTv.text = item.original_name
                nameTv.text = item.character?.split("/")?.first() ?: ""

                root.setOnClickListener {
                    listener?.onItemClicked(item.id)
                }
            }
            /*  val genresNameList = item.genres.map { it?.name }.toList()
              val genresString = genresNameList.joinToString(",")*/
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class CastsTaskDiffCallback : DiffUtil.ItemCallback<CastItem>() {
    override fun areItemsTheSame(oldItem: CastItem, newItem: CastItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CastItem, newItem: CastItem): Boolean {
        return oldItem == newItem
    }
}

interface CastItemListener {
    fun onItemClicked(id: Int?)
}
