package com.example.authorapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.authorapp.R
import com.example.authorapp.data.model.Author
import com.example.authorapp.databinding.ListItemAuthorBinding

class AuthorListingAdapter(
    private val list: ArrayList<Author> = arrayListOf(),
    private val interaction: Interaction? = null) :

    RecyclerView.Adapter<AuthorListingAdapter.AuthorViewHolder>() {
    private lateinit var  binding : ListItemAuthorBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding=DataBindingUtil.inflate(
            layoutInflater,
            R.layout.list_item_author,
            parent,
            false
        )
        return AuthorViewHolder(binding ,interaction)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    fun addData(data: List<Author>) {
        data.forEach {
            if(!list.contains(it)){
                list.add(it)
            }
        }
        notifyItemRangeInserted(list.size, data.size)
    }

    fun clearAllData() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        val item = list[position]
        return holder.bind(item)
    }

    inner class AuthorViewHolder(
        val binding: ListItemAuthorBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Author) {
            Glide.with(binding.root.context).load(item.downloadUrl)
                .placeholder(R.drawable.placeholder_for_missing_posters).into(binding.ivAuthor)
            binding.tvAuthor.text=item.author
            binding.root.setOnClickListener {
                interaction?.onAuthorClicked(position, item)
            }
        }
    }
    interface Interaction {
        fun onAuthorClicked(position: Int, item: Author)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}