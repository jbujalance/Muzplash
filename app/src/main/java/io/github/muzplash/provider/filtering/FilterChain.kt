package io.github.muzplash.provider.filtering

import java.util.stream.Stream

/**
 * A stream filter that is made of the chaining of several filters.
 * @param T The type of the elements in the stream that are to be filtered.
 * @property filters A collection of filters to be applied on the stream.
 */
class FilterChain<T>(private val filters: Collection<Filter<T>>) : Filter<T> {

    override fun filter(stream: Stream<T>): Stream<T> {
        var filtered = stream
        for (filter in filters) {
            filtered = filter.filter(filtered)
        }
        return filtered
    }
}