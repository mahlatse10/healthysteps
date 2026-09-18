package com.example.healthysteps.data.repository

import com.example.healthysteps.data.local.HabitDao
import com.example.healthysteps.data.local.HabitEntity
import kotlinx.coroutines.flow.Flow

class HabitRepository(
    private val habitDao: HabitDao
) {

    fun getAllHabits(): Flow<List<HabitEntity>> {
        return habitDao.getAllHabits()
    }

    suspend fun insertHabit(habit: HabitEntity) {
        habitDao.insertHabit(habit)
    }

    suspend fun updateHabit(habit: HabitEntity) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: HabitEntity) {
        habitDao.deleteHabit(habit)
    }
}