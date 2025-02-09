package com.zhigaras.adapterdelegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

inline fun <reified M : ListItem, VB : ViewBinding> adapterDelegate(
    crossinline inflate: (LayoutInflater, ViewGroup) -> VB,
    crossinline bind: VB.(M) -> Unit
): AdapterDelegate<M, ViewHolderDelegate<M>> {
    return object : AdapterDelegate<M, ViewHolderDelegate<M>>() {
        override fun viewType() = M::class.hashCode()
        override fun createViewHolder(parent: ViewGroup): ViewHolderDelegate<M> {
            val binding = inflate.invoke(LayoutInflater.from(parent.context), parent)
            return object : ViewHolderDelegate<M>(binding) {
                override fun bind(item: M) = bind.invoke(binding, item)
            }
        }
    }
}

fun <VB : ViewBinding> makePayload(bind: VB.() -> Unit) = Payload(bind)
