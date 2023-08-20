package kz.asetkenes.learnandroid.common.core

import java.lang.Exception
import java.lang.IllegalStateException

sealed class Container<out T> {

    fun <R> map(mapper: ((T) -> R)? = null): Container<R> {
        val innerMapper: ((T) -> R)? = if (mapper == null) {
            null
        } else {
            {
                mapper(it)
            }
        }
        return innerMap(innerMapper)
    }

    abstract fun <R> innerMap(mapper: ((T) -> R)? = null): Container<R>

    abstract fun getOrThrowException(): T

    abstract fun getOrNull(): T?

    data class Success<T>(
        val value: T
    ) : Container<T>() {

        override fun <R> innerMap(mapper: ((T) -> R)?): Container<R> {
            if (mapper == null) throw IllegalStateException("Can't map Container.Success without mapper")
            return try {
                Success(mapper(value))
            } catch (ex: Exception) {
                Error(ex)
            }
        }

        override fun getOrThrowException(): T {
            return value
        }

        override fun getOrNull(): T? {
            return value
        }
    }

    data class Error(
        val exception: Exception
    ) : Container<Nothing>() {

        override fun <R> innerMap(mapper: ((Nothing) -> R)?): Container<R> {
            return this
        }

        override fun getOrThrowException(): Nothing {
            throw exception
        }

        override fun getOrNull(): Nothing? {
            return null
        }
    }

    object Pending : Container<Nothing>() {

        override fun <R> innerMap(mapper: ((Nothing) -> R)?): Container<R> {
            return this
        }

        override fun getOrThrowException(): Nothing {
            throw IllegalStateException("Container is Pending")
        }

        override fun getOrNull(): Nothing? {
            return null
        }
    }
}