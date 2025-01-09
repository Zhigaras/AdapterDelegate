package com.zhigaras.adapterdelegate

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * [AdapterDelegate.viewType] and [ListItem.itemType] methods should return the same for one model so check default
 * [ListItem.itemType] method implementation first or override it in accordance with delegate [AdapterDelegate.viewType] method.
 *
 */
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
