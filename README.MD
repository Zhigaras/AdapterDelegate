# AdapterDelegate

AdapterDelegate is a library that simplifies working with `RecyclerView.Adapter` and `Payloads` in Android, providing a flexible and modular way to handle different types of list items using delegates.

## Features

- Support for multiple item types in a single `RecyclerView`
- Easy to handle Payloads

## Installation

Add the dependency to your `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.zhigaras:adapterdelegate:latest_version'
}
```

## Usage

### 1. Creating a model

```kotlin
data class Post(
    val id: Long,
    val title: String = "",
    val text: String = "",
    val isLiked: Boolean = false
) : ListItem {
    override fun areItemTheSame(newItem: ListItem): Boolean {
        if (newItem !is Post) return false
        return id == newItem.id
    }

    override fun payload(newItem: ListItem): List<Payload<*>> {
        if (newItem !is Post) return emptyList()
        val payloads = mutableListOf<Payload<*>>()
        if (newItem.isLiked != isLiked) payloads.add(LikeChangedPayload(newItem.isLiked))
        if (newItem.text != text) payloads.add(TextChangedPayload(newItem.text))
        if (newItem.title != title) payloads.add(TitleChangedPayload(newItem.title))
        return payloads
    }
}
```

If your model is `data class` all that's left for you to do is to implement `areItemTheSame()` method
which will be used by DiffUtilCallback to check whether two objects represent the same item.
For example, if your items have unique ids, this method should check their id equality.

If your model is NOT `data class` you have to implement one more method - `areContentTheSame()`. 
DiffUtilCallback use this method to check whether two items have the same data.
This information is used to detect if the contents of an item have changed.

If you want to update specific views partially rather than redrawing the entire item:
1. Create classes describing every type of item change (every of them must implement `Payload` interface)
2. Implement `bindPayload()` method, where you can update view as you need accordingly.
3. Override `payload()` method in your model, check which fields was changed and return list of `Payloads`

### 2. Creating an `AdapterDelegate` and `ViewHolder`

```kotlin
class PostDelegate : AdapterDelegate<Post, PostDelegate.PostViewHolder>() {

    inner class PostViewHolder(private val binding: LayoutPostBinding) : ViewHolderDelegate<Post>(binding) {
        override fun bind(item: Post) {
            // simple binding
        }
    }

    // this method must return the same as ListItem.itemType(). The model-delegate linking is achieved by this.
    override fun viewType() = Post::class.hashCode()

    override fun createViewHolder(parent: ViewGroup) = PostViewHolder(
        LayoutPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
}
```

### 3. Creating RecyclerView adapter

```kotlin
val adapter = CompositeAdapter.Builder()
    .addDelegate(PostDelegate())
    // add other delegates
    .build()

binding.recyclerView.adapter = adapter
```

Then collect the list changes and submit them to adapter

```kotlin
lifecycleScope.launch { 
    viewModel.state.collect {
        adapter.submitList(it)
    }
}
```

That`s all. Yor list will be updated smoothly and without unnecessary redrawing.

## License

Copyright 2025 Ilya Zhigaras

Licensed under the Apache License, Version 2.0 (the "License");

you may not use this file except in compliance with the License.

You may obtain a copy of the License at

&#x20;  [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,

WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

See the License for the specific language governing permissions and

limitations under the License.

