package com.example.healthysteps.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthysteps.data.local.HabitEntity
import com.example.healthysteps.data.local.HealthyStepsDatabase
import com.example.healthysteps.data.repository.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val database =
        HealthyStepsDatabase.getDatabase(application)

    private val repository =
        HabitRepository(database.habitDao())

    val habits =
        repository.getAllHabits()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun addHabit(
        name: String,
        description: String,
        category: String,
        reminderEnabled: Boolean
    ) {
        val cleanName = name.trim()

        if (cleanName.isBlank()) {
            return
        }

        viewModelScope.launch {

            val habit = HabitEntity(
                name = cleanName,
                description = description.trim(),
                category = if (category.isBlank()) {
                    "General"
                } else {
                    category.trim()
                },
                reminderEnabled = reminderEnabled
            )

            repository.insertHabit(habit)
        }
    }

    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }

    fun completeHabit(habit: HabitEntity) {

        viewModelScope.launch {

            val today = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).format(Calendar.getInstance().time)

            if (habit.lastCompletedDate == today) {
                return@launch
            }

            val yesterdayCalendar = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, -1)
            }

            val yesterday = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).format(yesterdayCalendar.time)

            val newStreak =
                if (habit.lastCompletedDate == yesterday) {
                    habit.currentStreak + 1
                } else {
                    1
                }

            val updatedHabit = habit.copy(
                completedDays = habit.completedDays + 1,
                currentStreak = newStreak,
                longestStreak = maxOf(
                    habit.longestStreak,
                    newStreak
                ),
                xp = habit.xp + 10,
                lastCompletedDate = today
            )

            repository.updateHabit(updatedHabit)
        }
    }
}