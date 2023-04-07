package com.example.album

import com.example.album.BuildConfig.BASE_URL
import com.example.album.ui.albums.ARG_ALBUM
import com.example.album.ui.albums.PAGE_URL
import org.junit.Test

/**
 * Class responsible for unit tests, specifically testing app constants
 */

class ConstantsValidationTest {

    @Test
    fun validateBaseUrlConstantTest() {
        assert(BASE_URL == "https://itunes.apple.com/us/rss/topalbums/")
    }

    @Test
    fun validateSubPageUrlConstantTest() {
        assert(PAGE_URL == "limit=100/json")
    }

    @Test
    fun validateArgBundleConstantTest() {
        assert(ARG_ALBUM == "album")
    }
}