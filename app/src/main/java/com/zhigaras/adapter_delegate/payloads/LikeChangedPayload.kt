package com.zhigaras.adapter_delegate.payloads

import com.bumptech.glide.Glide
import com.zhigaras.adapter_delegate.R
import com.zhigaras.adapter_delegate.databinding.LayoutPostBinding
import com.zhigaras.adapterdelegate.Payload

class LikeChangedPayload(private val isLiked: Boolean) : Payload<LayoutPostBinding> {
    override fun bindPayload(binding: LayoutPostBinding) {
        Glide.with(binding.root)
            .load(if (isLiked) R.drawable.ic_liked else R.drawable.ic_not_liked)
            .into(binding.likeIv)
    }
}
