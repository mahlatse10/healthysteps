package com.example.healthysteps.data.model

data class Achievement(
    val id: Int,
    val title: String,
    val description: String,
    val requiredXp: Int,
    val unlocked: Boolean = false
)