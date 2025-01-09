package com.zhigaras.adapterdelegate

/**
 * Interface for your list models. All list models must implement this and override at least one [areItemTheSame] method.
 *
 * **IMPORTANT**
 *
 * The default implementation of [areContentTheSame] method involves using data classes as list models!
 * If you do not use data classes then override [areContentTheSame] method please.
 *
 */
interface ListItem {
    
    fun itemType(): Int = this::class.hashCode()
    
    fun areItemTheSame(newItem: ListItem): Boolean
    
    fun areContentTheSame(newItem: ListItem): Boolean = this == newItem
    
    fun payload(newItem: ListItem): Payload<*> = Payload.None()
}
