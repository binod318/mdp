package com.mdp.assignment4

import androidx.annotation.DrawableRes

data class ShoppingGridItem(
    val name: String,
    @DrawableRes val categoryIcon: Int
)