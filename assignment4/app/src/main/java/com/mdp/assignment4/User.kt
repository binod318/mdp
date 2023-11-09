package com.mdp.assignment4

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class User(
    val firstName: String,
    val lastName: String,
    val userName: String,
    val password: String
): Parcelable {
    constructor() : this("", "", "", "")
}