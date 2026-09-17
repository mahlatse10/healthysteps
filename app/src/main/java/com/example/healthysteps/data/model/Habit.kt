package com.example.healthysteps.data.model

data class Habit(
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