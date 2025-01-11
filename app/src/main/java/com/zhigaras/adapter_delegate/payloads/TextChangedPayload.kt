package com.zhigaras.adapter_delegate.payloads

import com.zhigaras.adapter_delegate.databinding.LayoutPostBinding
import com.zhigaras.adapterdelegate.Payload

class TextChangedPayload(private val newText: String) : Payload<LayoutPostBinding> {
    override fun bindPayload(binding: LayoutPostBinding) {
        binding.text.text = newText
    }
}
