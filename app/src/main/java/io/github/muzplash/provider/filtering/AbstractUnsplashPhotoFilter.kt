package io.github.muzplash.provider.filtering

import io.github.muzplash.model.MuzplashSettings
import io.github.unsplash.model.UnsplashPhoto
import java.util.stream.Stream

/**
 * Base class for any UnsplashsPhoto filter.
 * @property settings The Muzplash settings holding the filtering configurations.
 */
abstract class AbstractUnsplashPhotoFilter(protected val settings: MuzplashSettings) : Filter<UnsplashPhoto> {

    override fun filter(stream: Stream<UnsplashPhoto>): Stream<UnsplashPhoto> {
        return if(filterIsActive()) filterStream(stream) else stream
    }

    /**
     * Whether this filter should be applied or not, normally depending on the Muzplash settings.
     * @return `true` if this filter is to be applied on the stream, `false` if the stream should be returned in its original state.
     */
    protected abstract fun filterIsActive(): Boolean

    /**
     * Actually filters the stream.
     * This method assumes the filter is to be applied.
     * @param stream The stream to be filtered.
     * @return a new filtered stream
     */
    protected abstract fun filterStream(stream: Stream<UnsplashPhoto>): Stream<UnsplashPhoto>
}