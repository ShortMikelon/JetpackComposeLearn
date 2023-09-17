package kz.asetkenes.learnandroid.ui.utils

class SideEffect<T>(private var value: T?) {
    fun get(): T? = value.also { value = null }
}