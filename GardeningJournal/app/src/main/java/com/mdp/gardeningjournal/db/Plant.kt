package com.mdp.gardeningjournal.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Plant(
    var name: String,
    var type: String,
    var wateringFrequency: Int,
    var plantingDate: String
): Serializable
{
    @PrimaryKey(autoGenerate=true)
    var id: Int = 0
}
