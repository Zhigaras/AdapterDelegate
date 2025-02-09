package com.zhigaras.adapterdelegate

import androidx.viewbinding.ViewBinding

fun interface Payload<in VB : ViewBinding> {
    fun bindPayload(binding: VB)
}
