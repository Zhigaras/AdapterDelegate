package com.zhigaras.adapter_delegate.models

import com.zhigaras.adapterdelegate.ListItem
import com.zhigaras.adapterdelegate.Payload
import com.zhigaras.adapter_delegate.payloads.LikeChangedPayload
import com.zhigaras.adapter_delegate.payloads.TextChangedPayload
import com.zhigaras.adapter_delegate.payloads.TitleChangedPayload

data class Post(
    val id: Long,
    val title: String,
    val author: String,
    val date: Long,
    val text: String,
    val isLiked: Boolean
) : ListItem {
    override fun areItemTheSame(newItem: ListItem): Boolean {
        if (newItem !is Post) return false
        return id == newItem.id
    }

    override fun payload(newItem: ListItem): Payload<*> {
        if (newItem !is Post) return Payload.None()
        if (newItem.isLiked != isLiked) return LikeChangedPayload(newItem.isLiked)
        if (newItem.text != text) return TextChangedPayload(newItem.text)
        if (newItem.title != title) return TitleChangedPayload(newItem.title)
        return Payload.None()
    }
}
