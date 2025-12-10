package com.karandhillon.rickandmortydex.network

data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?, // 'next' can be null if it's the last page
    val prev: String?  // 'prev' can be null if it's the first page
)