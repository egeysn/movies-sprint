package com.egeysn.movies_sprint.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egeysn.movies_sprint.data.general.ResultsItem
import com.egeysn.movies_sprint.databinding.PopularMoviesItemBinding
import com.egeysn.movies_sprint.ui.main.MainViewModel
import com.egeysn.movies_sprint.utils.GlideHelper
import com.egeysn.movies_sprint.utils.toYear

class PopularMoviesAdapter(
    private val context: Context,
    private val viewModel: MainViewModel,
    private val items: List<ResultsItem>,
    var listener: PopularMoviesItemListener?,
) :
    ListAdapter<ResultsItem, PopularMoviesAdapter.ViewHolder>(PersonTaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, items[position], items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PopularMoviesItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder constructor(private val binding: PopularMoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // TODO Can apply other adapters? Search after complete project. (viewModel, utils)

        fun bind(viewModel: MainViewModel, item: ResultsItem, size: Int) {

            val params = binding.root.layoutParams as RecyclerView.LayoutParams
            val imageWidth = viewModel.utils.dpToPx(110f)
            binding.imageCv.layoutParams =
                LinearLayout.LayoutParams(imageWidth, (imageWidth * (1.5)).toInt())

            when (bindingAdapterPosition) {
                0 -> {
                    params.topMargin =
                        viewModel.utils.dpToPx(15f)
                    params.bottomMargin = 0
                    binding.root.layoutParams = params
                }
                size - 1 -> {
                    params.topMargin = viewModel.utils.dpToPx(10f)
                    params.bottomMargin =
                        viewModel.utils.dpToPx(30f)
                    binding.root.layoutParams = params
                }
                else -> {
                    params.topMargin = viewModel.utils.dpToPx(10f)
                    params.bottomMargin = 0
                    binding.root.layoutParams = params
                }
            }
            binding.apply {
                GlideHelper.loadImage(context, item.poster_path, imageIv)
                titleTv.text = item.original_title
                dateTv.text = "(${item.release_date?.toYear()})"
                ratingBar.rating = (item.vote_average ?: 0).toFloat() / 2f

                root.setOnClickListener {
                    listener?.onItemClicked(item.id)
                }
            }
            /*  val genresNameList = item.genres.map { it?.name }.toList()
              val genresString = genresNameList.joinToString(",")*/
        }
    }
}

interface PopularMoviesItemListener {
    fun onItemClicked(id: Int?)
}
