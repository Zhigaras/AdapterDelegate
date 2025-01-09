package com.zhigaras.adapterdelegate

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class AdapterDelegate<M : ListItem, in VH : ViewHolderDelegate<M>> {
    abstract fun viewType(): Int
    abstract fun createViewHolder(parent: ViewGroup): ViewHolderDelegate<M>
    fun bindViewHolder(item: M, viewHolder: VH) {
        viewHolder.bind(item)
    }

    fun bindViewHolder(viewHolder: VH, payloads: List<Payload<ViewBinding>>) {
        payloads.forEach { viewHolder.bind(it) }
    }
}
