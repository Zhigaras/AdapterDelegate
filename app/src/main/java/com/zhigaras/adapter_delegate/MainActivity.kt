package com.zhigaras.adapter_delegate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.zhigaras.adapter_delegate.databinding.ActivityMainBinding
import com.zhigaras.adapter_delegate.delegates.PostDelegate
import com.zhigaras.adapterdelegate.CompositeAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this)[MainViewModel::class]

        val adapter = CompositeAdapter.Builder()
            .addDelegate(PostDelegate())
            // add other delegates
            .build()

        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.state.collect {
                adapter.submitList(it)
            }
        }
    }
}
