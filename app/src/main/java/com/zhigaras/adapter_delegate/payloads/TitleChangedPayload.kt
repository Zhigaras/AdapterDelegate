package com.zhigaras.adapter_delegate.payloads

import com.zhigaras.adapter_delegate.databinding.LayoutPostBinding
import com.zhigaras.adapterdelegate.Payload

class TitleChangedPayload(private val newTitle: String) : Payload<LayoutPostBinding> {
    override fun bindPayload(binding: LayoutPostBinding) {
        binding.title.text = newTitle
    }
}
