package com.zhigaras.adapter_delegate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhigaras.adapter_delegate.models.Post
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val initialList = List(6) { Post(it.toLong()) }
    private val _state = MutableStateFlow(initialList)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            while (isActive) {
                delay(1000)
                _state.update { list ->
                    val random = Random.nextInt(list.lastIndex)
                    list.mapIndexed { index, post ->
                        if (index == random) {
                            post.copy(
                                title = RandomStringGenerator.generate(5),
                                author = RandomStringGenerator.generate(10),
                                text = RandomStringGenerator.generate(20),
                                isLiked = true
                            )
                        } else post.copy(
                            title = "",
                            author = "",
                            text = "",
                            isLiked = false
                        )
                    }
                }
            }
        }
    }
}
