package io.github.muzplash.provider.filtering

import java.util.stream.Stream

/**
 * A filter operation on a stream.
 * @param T The type of the streamed elements
 */
interface Filter<T> {

    /**
     * Filters a stream.
     * @param stream The original stream whose elements should be filtered.
     * @return A new filtered stream
     */
    fun filter(stream: Stream<T>): Stream<T>
}