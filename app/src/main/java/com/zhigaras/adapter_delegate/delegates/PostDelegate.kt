package com.zhigaras.adapter_delegate.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zhigaras.adapter_delegate.R
import com.zhigaras.adapter_delegate.databinding.LayoutPostBinding
import com.zhigaras.adapterdelegate.AdapterDelegate
import com.zhigaras.adapterdelegate.ViewHolderDelegate
import com.zhigaras.adapter_delegate.models.Post
import com.zhigaras.adapterdelegate.adapterDelegate

class PostDelegate : AdapterDelegate<Post, PostDelegate.PostViewHolder>() {

    inner class PostViewHolder(private val binding: LayoutPostBinding) : ViewHolderDelegate<Post>(binding) {
        override fun bind(item: Post) {
            with(binding) {
                title.text = item.title
                text.text = item.text
                author.text = item.author
                Glide.with(root)
                    .load(if (item.isLiked) R.drawable.ic_liked else R.drawable.ic_not_liked)
                    .into(likeIv)
            }
        }
    }

    override fun viewType() = Post::class.hashCode()

    override fun createViewHolder(parent: ViewGroup) = PostViewHolder(
        LayoutPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
}

// alternative
val postDelegate = adapterDelegate<Post, LayoutPostBinding>({ inflater, parent ->
    LayoutPostBinding.inflate(inflater, parent, true)
}) { item ->
    title.text = item.title
    text.text = item.text
    author.text = item.author
    Glide.with(root)
        .load(if (item.isLiked) R.drawable.ic_liked else R.drawable.ic_not_liked)
        .into(likeIv)
}
