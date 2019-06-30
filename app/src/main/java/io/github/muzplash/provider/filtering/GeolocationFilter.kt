package io.github.muzplash.provider.filtering

import io.github.muzplash.model.MuzplashSettings
import io.github.unsplash.model.UnsplashPhoto
import java.util.stream.Stream

/**
 * Only retains the geolocated photos.
 * @constructor Default constructor
 * @param settings The Muzplash settings holding the filtering configuration.
 */
class GeolocationFilter(settings: MuzplashSettings) : AbstractUnsplashPhotoFilter(settings) {

    override fun filterIsActive(): Boolean {
        return settings.isGeolocatedFiltered()
    }

    override fun filterStream(stream: Stream<UnsplashPhoto>): Stream<UnsplashPhoto> {
        return stream.filter{ it.isGeolocated() }
    }

}