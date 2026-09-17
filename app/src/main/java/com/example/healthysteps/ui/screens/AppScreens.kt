package com.example.healthysteps.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.healthysteps.data.model.Habit

private val demoHabits = mutableStateListOf(
    Habit(
        id = 1,
        name = "Drink Water",
        description = "Drink at least 2 litres of water",
        category = "Hydration",
        targetDays = 7,
        completedDays = 4,
        currentStreak = 4,
        longestStreak = 6,
        xp = 40
    ),
    Habit(
        id = 2,
        name = "Morning Walk",
        description = "Walk for at least 20 minutes",
        category = "Exercise",
        targetDays = 7,
        completedDays = 3,
        currentStreak = 3,
        longestStreak = 5,
        xp = 30
    )
)

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Welcome Back",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onLoginSuccess,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account")
        }
    }
}

@Composable
fun RegisterScreen(
    onRegistrationSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Create Account",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onRegistrationSuccess,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        TextButton(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account? Login")
        }
    }
}

@Composable
fun DashboardScreen(
    onHabitsClick: () -> Unit,
    onProgressClick: () -> Unit,
    onAchievementsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onFoodScannerClick: () -> Unit
) {
    val totalXp = demoHabits.sumOf { it.xp }
    val level = (totalXp / 100) + 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            "HealthySteps",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(8.dp))

        Text("Your daily health journey")

        Spacer(Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    "Level $level",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text("$totalXp XP earned")

                Spacer(Modifier.height(8.dp))

                Text("Keep going and build healthy habits!")
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onHabitsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("My Habits")
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = onFoodScannerClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Food Scanner")
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = onProgressClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Progress")
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = onAchievementsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Achievements")
        }

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = onProfileClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Profile")
        }

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = onSettingsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Settings")
        }
    }
}

@Composable
fun HabitsScreen(
    onAddHabitClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBackClick) {
                Text("Back")
            }

            Text(
                "My Habits",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onAddHabitClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add New Habit")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(demoHabits) { habit ->
                HabitCard(habit)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun HabitCard(habit: Habit) {
    var completed by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        habit.name,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(habit.category)

                    Text(habit.description)
                }

                Checkbox(
                    checked = completed,
                    onCheckedChange = {
                        completed = it
                    }
                )
            }

            Divider()

            Spacer(Modifier.height(8.dp))

            Text("Progress: ${habit.completedDays}/${habit.targetDays} days")
            Text("Current streak: ${habit.currentStreak} days")
            Text("XP: ${habit.xp}")
        }
    }
}

@Composable
fun AddHabitScreen(
    onBackClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var reminderEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text("Back")
        }

        Text(
            "Add Habit",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Habit Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Daily reminder",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = reminderEnabled,
                onCheckedChange = {
                    reminderEnabled = it
                }
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Habit")
        }
    }
}

@Composable
fun ProgressScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text("Back")
        }

        Text(
            "Progress",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    "Weekly Overview",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(Modifier.height(12.dp))

                Text("Habits completed: 7")
                Text("Current streak: 4 days")
                Text("Longest streak: 6 days")
                Text("Total XP: 70")
                Text("Weekly completion: 70%")
            }
        }

        Spacer(Modifier.height(20.dp))

        Text("Keep building consistency. Every completed habit counts.")
    }
}

@Composable
fun AchievementsScreen(
    onBackClick: () -> Unit
) {
    val achievements = listOf(
        "First Step" to "Complete your first habit",
        "Getting Started" to "Earn 50 XP",
        "One Week" to "Maintain a 7-day streak",
        "Healthy Routine" to "Complete 25 habits",
        "Consistency" to "Maintain a 30-day streak"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text("Back")
        }

        Text(
            "Achievements",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(achievements) { achievement ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            achievement.first,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(achievement.second)

                        Spacer(Modifier.height(8.dp))

                        Text("Locked")
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text("Back")
        }

        Text(
            "Profile",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = "HealthySteps User",
            onValueChange = {},
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = "user@example.com",
            onValueChange = {},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        Spacer(Modifier.height(20.dp))

        Text("Level: 1")
        Text("Total XP: 70")
        Text("Longest streak: 6 days")
    }
}

@Composable
fun SettingsScreen(
    onLanguageClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var notifications by remember { mutableStateOf(true) }
    var darkMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text("Back")
        }

        Text(
            "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Notifications",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = notifications,
                onCheckedChange = {
                    notifications = it
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Dark Mode",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = darkMode,
                onCheckedChange = {
                    darkMode = it
                }
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onLanguageClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Language")
        }
    }
}

@Composable
fun LanguageScreen(
    onBackClick: () -> Unit
) {
    var selectedLanguage by remember {
        mutableStateOf("English")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text("Back")
        }

        Text(
            "Language",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(20.dp))

        listOf(
            "English",
            "Sepedi",
            "isiZulu"
        ).forEach { language ->

            Button(
                onClick = {
                    selectedLanguage = language
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    if (selectedLanguage == language) {
                        "$language ✓"
                    } else {
                        language
                    }
                )
            }
        }
    }
}

@Composable
fun FoodScannerScreen(
    onFoodFound: () -> Unit,
    onBackClick: () -> Unit
) {
    var barcode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text("Back")
        }

        Text(
            "Food Scanner",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(20.dp))

        Text(
            "Enter a barcode to look up nutritional information."
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = barcode,
            onValueChange = { barcode = it },
            label = { Text("Barcode") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onFoodFound,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Look Up Food")
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "Camera barcode scanning will be connected during the final scanner integration."
        )
    }
}

@Composable
fun FoodDetailsScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text("Back")
        }

        Text(
            "Food Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    "Product",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(Modifier.height(12.dp))

                Text("Calories: N/A")
                Text("Protein: N/A")
                Text("Carbohydrates: N/A")
                Text("Fat: N/A")
                Text("Brand: N/A")
            }
        }
    }
}