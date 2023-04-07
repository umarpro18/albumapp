package com.example.album.utils

/**
 * A interface class responsible map data
 */

interface Mapper<I, O> {
    fun map(input: I): O
}