package com.omer.ilhanli.applike.data.state

enum class State {
    PROGRESS,
    COMPLETE,
    FAILURE
}

data class Source<out T>(val state: State, val value: T?, val message: String?) {
    companion object {
        fun <T> complete(value: T? = null): Source<T> =
            Source(state = State.COMPLETE, value = value, message = null)

        fun <T> failure(value: T? = null, message: String): Source<T> =
            Source(state = State.FAILURE, value, message = message)

        fun <T> progress(value: T? = null): Source<T> =
            Source(state = State.PROGRESS, value, message = null)
    }
}