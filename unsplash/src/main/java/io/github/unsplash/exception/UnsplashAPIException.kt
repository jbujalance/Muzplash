package io.github.unsplash.exception

import io.github.unsplash.model.UnsplashError

class UnsplashAPIException(message: String, code: Int, error: UnsplashError): Exception(message)