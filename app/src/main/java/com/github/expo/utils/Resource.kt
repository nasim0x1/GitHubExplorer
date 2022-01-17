package com.github.expo.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class loading<T> : Resource<T>()
    class success<T>(data: T? = null) : Resource<T>(data = data)
    class error<T>(message: String? = null) : Resource<T>(message = message)
}
