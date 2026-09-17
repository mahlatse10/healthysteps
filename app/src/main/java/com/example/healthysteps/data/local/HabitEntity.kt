package com.example.healthysteps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val description: String = "",

    val category: String = "General",

    val targetDays: Int = 7,

    val completedDays: Int = 0,

    val currentStreak: Int = 0,

    val longestStreak: Int = 0,

    val xp: Int = 0,

    val reminderEnabled: Boolean = false,

    val reminderTime: String = ""
)