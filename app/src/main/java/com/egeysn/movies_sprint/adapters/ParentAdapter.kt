package com.egeysn.movies_sprint.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
                // TODO ADD DP TO PX
                params.bottomMargin = 60
                binding.root.layoutParams = params
            } else {
                params.bottomMargin = 5
                binding.root.layoutParams = params
            }

            binding.titleTv.text = item.type
            binding.childRv.setHasFixedSize(true)

            val horizontalManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            when (item.type) {
                "tv" -> {
                    binding.titleTv.text = "TV Shows"
                    binding.childRv.layoutManager = horizontalManager
                    binding.childRv.apply {
                        adapter = MoviesAdapter(context, item.results)
                    }
                }
                "person" -> {
                    binding.titleTv.text = "Persons"
                    binding.childRv.layoutManager = horizontalManager
                    binding.childRv.apply {
                        adapter = PersonAdapter(context, item.results)
                    }
                }
                "movie" -> {
                    binding.titleTv.text = "Movies"
                    binding.childRv.layoutManager = horizontalManager
                    binding.childRv.apply {
                        adapter = MoviesAdapter(context, item.results)
                    }
                }
                else -> {
                    binding.titleTv.text = "Undefined"
                    binding.childRv.apply {
                        adapter = MoviesAdapter(context, item.results)
                    }
                }
            }
        }
    }
}
