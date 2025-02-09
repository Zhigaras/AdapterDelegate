package com.zhigaras.adapter_delegate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.zhigaras.adapter_delegate.databinding.ActivityMainBinding
import com.zhigaras.adapter_delegate.databinding.LayoutPostBinding
import com.zhigaras.adapter_delegate.models.Post
import com.zhigaras.adapterdelegate.adapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this)[MainViewModel::class]

//        val adapter = CompositeAdapter.Builder()
//            .addDelegate(PostDelegate())
//            // add other delegates
//            .build()

        val adapter = adapter {
            delegate<Post, LayoutPostBinding>(
                inflate = { inflater, parent -> LayoutPostBinding.inflate(inflater, parent, false) },
                bind = { item ->
                    title.text = item.title
                    text.text = item.text
                    author.text = item.author
                    Glide.with(root)
                        .load(if (item.isLiked) R.drawable.ic_liked else R.drawable.ic_not_liked)
                        .into(likeIv)
                }
            )
            // add other delegates
        }

        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.state.collect {
                adapter.submitList(it)
            }
        }
    }
}
