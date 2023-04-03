package com.example.album.utils


interface Mapper<I, O> {
    fun map(input: I): O
}