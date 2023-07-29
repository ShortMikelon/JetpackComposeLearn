package kz.asetkenes.learnandroid.ui.viewutils

import kotlinx.coroutines.flow.MutableStateFlow

class SideEffect<T>(value: T) {
    private var _value: T? = value
    fun get(): T? = _value.also { _value = null }
}

fun <T> MutableStateFlow<SideEffect<T>>.publishSideEffect(data: T) {
    this.value = SideEffect(data)
}