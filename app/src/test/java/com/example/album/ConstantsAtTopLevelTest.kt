package com.example.album.feature.albums

import com.example.album.BuildConfig.BASE_URL
import com.example.album.ui.albums.PAGE_URL
import org.junit.Test

class ConstantsValidationTest {

    @Test
    fun validateBaseUrlConstantTest() {
        assert(BASE_URL == "https://itunes.apple.com/us/rss/topalbums/")
    }

    @Test
    fun validateSubPageUrlConstantTest() {
        assert(PAGE_URL == "limit=100/json")
    }

}