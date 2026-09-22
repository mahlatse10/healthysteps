package com.example.healthysteps.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.healthysteps.data.local.HabitEntity

class HabitViewModel : ViewModel() {

    private val _habits =
        MutableStateFlow<List<HabitEntity>>(emptyList())

    val habits: StateFlow<List<HabitEntity>> =
        _habits.asStateFlow()

    private var nextId = 1L

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

        val habit = HabitEntity(
            id = nextId++,
            name = cleanName,
            description = description.trim(),
            category = if (category.isBlank()) {
                "General"
            } else {
                category.trim()
            },
            reminderEnabled = reminderEnabled
        )

        _habits.value = listOf(
            habit
        ) + _habits.value
    }

    fun deleteHabit(habit: HabitEntity) {
        _habits.value =
            _habits.value.filter {
                it.id != habit.id
            }
    }

    fun completeHabit(habit: HabitEntity) {

        val today = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(Calendar.getInstance().time)

        if (habit.lastCompletedDate == today) {
            return
        }

        val yesterdayCalendar =
            Calendar.getInstance().apply {
                add(
                    Calendar.DAY_OF_YEAR,
                    -1
                )
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
            completedDays =
                habit.completedDays + 1,

            currentStreak =
                newStreak,

            longestStreak =
                maxOf(
                    habit.longestStreak,
                    newStreak
                ),

            xp =
                habit.xp + 10,

            lastCompletedDate =
                today
        )

        _habits.value =
            _habits.value.map {
                if (it.id == habit.id) {
                    updatedHabit
                } else {
                    it
                }
            }
    }
}