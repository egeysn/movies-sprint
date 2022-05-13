package com.egeysn.movies_sprint.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.egeysn.movies_sprint.data.general.ParentModel
import com.egeysn.movies_sprint.databinding.ParentRecyclerItemBinding

class ParentAdapter(
    private val context: Context,
    private val items: List<ParentModel>,

) : RecyclerView.Adapter<ParentAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            ParentRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class MyViewHolder(private val binding: ParentRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ParentModel) {

            val position = bindingAdapterPosition
            val params = binding.root.layoutParams as RecyclerView.LayoutParams
            if (position == items.lastIndex) {
                binding.root.layoutParams = params
            } else {
                params.bottomMargin = 0
                binding.root.layoutParams = params
            }

            binding.titleTv.text = item.type

            when (item.type) {
                "tv" -> {
                    binding.titleTv.text = "TV"
                    binding.childRv.apply {
                        adapter = MoviesAdapter(item.results)
                    }
                }
                "person" -> {
                    binding.titleTv.text = "Person"
                    binding.childRv.apply {
                        adapter = PersonAdapter(item.results)
                    }
                }
                "movie" -> {
                    binding.titleTv.text = "Movie"
                    binding.childRv.apply {
                        adapter = MoviesAdapter(item.results)
                    }
                }
                else -> {
                    binding.titleTv.text = "Undefined"
                    binding.childRv.apply {
                        adapter = MoviesAdapter(item.results)
                    }
                }
            }
        }
    }
}
