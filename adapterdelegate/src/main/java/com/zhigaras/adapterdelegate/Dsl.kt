package com.zhigaras.adapterdelegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

@DslMarker
annotation class AdapterDsl

@AdapterDsl
class AdapterBuilder {
    val delegates = mutableListOf<AdapterDelegate<out ListItem, ViewHolderDelegate<ListItem>>>()

    inline fun <reified M : ListItem, VB : ViewBinding> delegate(
        crossinline inflate: (LayoutInflater, ViewGroup) -> VB,
        crossinline bind: VB.(M) -> Unit
    ) {
        delegates.add(adapterDelegate(inflate, bind))
    }

    fun build() = CompositeAdapter.Builder().apply { delegates.forEach { addDelegate(it) } }.build()
}

fun adapter(init: AdapterBuilder.() -> Unit): CompositeAdapter {
    return AdapterBuilder().apply(init).build()
}
