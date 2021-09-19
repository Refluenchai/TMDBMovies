package com.slicedwork.tmdbmovies.util

import android.app.Application

object TmdbApplication: Application() {
    var language: String = ""
    var apiKey: String = ""
}
