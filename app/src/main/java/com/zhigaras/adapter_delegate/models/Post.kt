package com.zhigaras.adapter_delegate.models

import com.zhigaras.adapterdelegate.ListItem
import com.zhigaras.adapterdelegate.Payload
import com.zhigaras.adapter_delegate.payloads.LikeChangedPayload
import com.zhigaras.adapter_delegate.payloads.TextChangedPayload
import com.zhigaras.adapter_delegate.payloads.TitleChangedPayload

data class Post(
    val id: Long,
    val title: String = "",
    val author: String = "",
    val date: Long = 0,
    val text: String = "",
    val isLiked: Boolean = false
) : ListItem {
    override fun areItemTheSame(newItem: ListItem): Boolean {
        if (newItem !is Post) return false
        return id == newItem.id
    }

    override fun payload(newItem: ListItem): List<Payload<*>> {
        if (newItem !is Post) return emptyList()
        val payloads = mutableListOf<Payload<*>>()
        if (newItem.isLiked != isLiked) payloads.add(LikeChangedPayload(newItem.isLiked))
        if (newItem.text != text) payloads.add(TextChangedPayload(newItem.text))
        if (newItem.title != title) payloads.add(TitleChangedPayload(newItem.title))
        return payloads
    }
}
