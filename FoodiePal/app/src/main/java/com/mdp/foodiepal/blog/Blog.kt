package com.mdp.foodiepal.blog

data class Blog(val title: String, val description: String, val thumbnail: Int, val author: String, val publishedDate: String, val favorite: Boolean = false)
