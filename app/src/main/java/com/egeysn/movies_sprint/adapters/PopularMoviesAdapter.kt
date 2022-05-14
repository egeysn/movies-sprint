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
import com.egeysn.movies_sprint.ui.main.MainViewModel

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
            binding.imageCv.layoutParams = LinearLayout.LayoutParams(imageWidth, (imageWidth * (1.5)).toInt())

            when (bindingAdapterPosition) {
                0 -> {
                    params.topMargin =
                        viewModel.utils.dpToPx(15f)
                    params.bottomMargin = 0
                    binding.root.layoutParams = params
                }
                size - 1 -> {
                    params.topMargin = 0
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
                listener?.onItemClicked(item.id)
            }
        }
    }
}

interface PopularMoviesItemListener {
    fun onItemClicked(id: Int?)
}
