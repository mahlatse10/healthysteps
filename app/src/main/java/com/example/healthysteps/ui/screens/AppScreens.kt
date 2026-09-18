package com.example.healthysteps.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.example.healthysteps.data.local.HabitEntity
import com.example.healthysteps.data.remote.ApiClient
import com.example.healthysteps.data.repository.AuthRepository
import com.example.healthysteps.ui.viewmodel.HabitViewModel
import com.example.healthysteps.ReminderScheduler
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val authRepository = remember { AuthRepository() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = ""
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = ""
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = {
                when {
                    email.isBlank() -> {
                        errorMessage = "Please enter your email."
                    }

                    password.isBlank() -> {
                        errorMessage = "Please enter your password."
                    }

                    else -> {
                        isLoading = true
                        errorMessage = ""

                        scope.launch {
                            val result = authRepository.login(
                                email = email.trim(),
                                password = password
                            )

                            isLoading = false

                            if (result.isSuccess) {
                                onLoginSuccess()
                            } else {
                                errorMessage =
                                    result.exceptionOrNull()?.message
                                        ?: "Login failed. Please check your details."
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(
                text = if (isLoading) {
                    "Logging in..."
                } else {
                    "Login"
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
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
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val authRepository = remember { AuthRepository() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                errorMessage = ""
            },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = ""
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = ""
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                errorMessage = ""
            },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(
            onClick = {
                when {
                    name.isBlank() -> {
                        errorMessage = "Please enter your full name."
                    }

                    email.isBlank() -> {
                        errorMessage = "Please enter your email."
                    }

                    password.isBlank() -> {
                        errorMessage = "Please enter a password."
                    }

                    password.length < 6 -> {
                        errorMessage = "Password must be at least 6 characters."
                    }

                    password != confirmPassword -> {
                        errorMessage = "Passwords do not match."
                    }

                    else -> {
                        isLoading = true
                        errorMessage = ""

                        scope.launch {
                            val result = authRepository.register(
                                name = name,
                                email = email,
                                password = password
                            )

                            isLoading = false

                            if (result.isSuccess) {
                                onRegistrationSuccess()
                            } else {
                                errorMessage =
                                    result.exceptionOrNull()?.message
                                        ?: "Registration failed. Please try again."
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(
                text = if (isLoading) {
                    "Creating Account..."
                } else {
                    "Register"
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "HealthySteps",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Welcome back!")

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Your Progress",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Level 1",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text("0 XP earned")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Keep going and build healthy habits!")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onHabitsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("My Habits")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onFoodScannerClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Food Scanner")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onProgressClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Progress")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onAchievementsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Achievements")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onProfileClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Profile")
        }

        Spacer(modifier = Modifier.height(8.dp))

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
    val viewModel: HabitViewModel = viewModel()
    val habits by viewModel.habits.collectAsState()

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
                text = "My Habits",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onAddHabitClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add New Habit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (habits.isEmpty()) {

            Text(
                text = "You have no habits yet.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tap \"Add New Habit\" to create your first habit."
            )

        } else {

            LazyColumn {
                items(
                    items = habits,
                    key = { it.id }
                ) { habit ->

                    HabitCard(
                        habit = habit,
                        onDelete = {
                            viewModel.deleteHabit(habit)
                        },
                        onComplete = {
                            viewModel.completeHabit(habit)
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}


@Composable
private fun HabitCard(
    habit: HabitEntity,
    onDelete: () -> Unit,
    onComplete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = habit.name,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = habit.category,
                style = MaterialTheme.typography.bodyMedium
            )

            if (habit.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))

                Text(habit.description)
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Progress: ${habit.completedDays}/${habit.targetDays} days"
            )

            Text(
                "Current streak: ${habit.currentStreak} days"
            )

            Text(
                "Longest streak: ${habit.longestStreak} days"
            )

            Text("XP: ${habit.xp}")

            Spacer(modifier = Modifier.height(12.dp))

            val today = remember {
                java.text.SimpleDateFormat(
                    "yyyy-MM-dd",
                    java.util.Locale.getDefault()
                ).format(java.util.Calendar.getInstance().time)
            }

            Button(
                onClick = onComplete,
                modifier = Modifier.fillMaxWidth(),
                enabled = habit.lastCompletedDate != today
            ) {
                Text(
                    if (habit.lastCompletedDate == today) {
                        "Completed Today ✓"
                    } else {
                        "Complete Habit (+10 XP)"
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onDelete,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Delete Habit")
            }
        }
    }
}


@Composable
fun AddHabitScreen(
    onBackClick: () -> Unit
) {
    val viewModel: HabitViewModel = viewModel()
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var reminderEnabled by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text("Back")
        }

        Text(
            text = "Add Habit",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                errorMessage = ""
            },
            label = { Text("Habit Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
            },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = category,
            onValueChange = {
                category = it
            },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Daily reminder",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = reminderEnabled,
                onCheckedChange = {
                    reminderEnabled = it
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = {
                if (name.isBlank()) {
                    errorMessage = "Please enter a habit name."
                } else {

                    viewModel.addHabit(
                        name = name.trim(),
                        description = description.trim(),
                        category = category.trim(),
                        reminderEnabled = reminderEnabled
                    )

                    if (reminderEnabled) {
                        ReminderScheduler.scheduleDailyReminder(
                            context = context,
                            habitId = System.currentTimeMillis(),
                            habitName = name.trim()
                        )
                    }

                    onBackClick()
                }
            },
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
    val viewModel: HabitViewModel = viewModel()
    val habits by viewModel.habits.collectAsState()

    val totalCompleted = habits.sumOf { it.completedDays }
    val totalXp = habits.sumOf { it.xp }
    val currentStreak = habits.maxOfOrNull { it.currentStreak } ?: 0
    val longestStreak = habits.maxOfOrNull { it.longestStreak } ?: 0

    val weeklyCompletion = if (habits.isEmpty()) {
        0
    } else {
        val totalTargetDays = habits.sumOf { it.targetDays }

        if (totalTargetDays == 0) {
            0
        } else {
            ((totalCompleted.toDouble() / totalTargetDays) * 100)
                .toInt()
                .coerceAtMost(100)
        }
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
            text = "Progress",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Weekly Overview",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Habits completed: $totalCompleted")
                Text("Current streak: $currentStreak days")
                Text("Longest streak: $longestStreak days")
                Text("Total XP: $totalXp")
                Text("Weekly completion: $weeklyCompletion%")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Keep building consistency. Every completed habit counts."
        )
    }
}


@Composable
fun AchievementsScreen(
    onBackClick: () -> Unit
) {
    val viewModel: HabitViewModel = viewModel()
    val habits by viewModel.habits.collectAsState()

    val totalXp = habits.sumOf { it.xp }
    val totalCompleted = habits.sumOf { it.completedDays }
    val longestStreak = habits.maxOfOrNull { it.longestStreak } ?: 0

    val achievements = listOf(
        Triple(
            "First Step",
            "Complete your first habit",
            totalCompleted >= 1
        ),
        Triple(
            "Getting Started",
            "Earn 50 XP",
            totalXp >= 50
        ),
        Triple(
            "One Week",
            "Maintain a 7-day streak",
            longestStreak >= 7
        ),
        Triple(
            "Healthy Routine",
            "Complete 25 habits",
            totalCompleted >= 25
        ),
        Triple(
            "Consistency",
            "Maintain a 30-day streak",
            longestStreak >= 30
        )
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
            text = "Achievements",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(achievements) { achievement ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = achievement.first,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(achievement.second)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (achievement.third) {
                                "Unlocked ✓"
                            } else {
                                "Locked"
                            }
                        )
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
    val viewModel: HabitViewModel = viewModel()
    val habits by viewModel.habits.collectAsState()

    val totalXp = habits.sumOf { it.xp }
    val level = (totalXp / 100) + 1
    val longestStreak = habits.maxOfOrNull { it.longestStreak } ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text("Back")
        }

        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = "HealthySteps User",
            onValueChange = {},
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = "user@example.com",
            onValueChange = {},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Level: $level")
        Text("Total XP: $totalXp")
        Text("Longest streak: $longestStreak days")
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
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Notifications",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = notifications,
                onCheckedChange = {
                    notifications = it
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dark Mode",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = darkMode,
                onCheckedChange = {
                    darkMode = it
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

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
            text = "Language",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

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
                    text = if (selectedLanguage == language) {
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
    onFoodFound: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var barcode by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        TextButton(
            onClick = onBackClick
        ) {
            Text("Back")
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Food Scanner",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Enter a product barcode to look up nutritional information."
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = barcode,
            onValueChange = { newValue ->
                barcode = newValue.filter {
                    it.isDigit()
                }
            },
            label = {
                Text("Barcode")
            },
            placeholder = {
                Text("e.g. 6001000000000")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {
                val cleanBarcode = barcode.trim()

                if (cleanBarcode.isNotEmpty()) {
                    onFoodFound(cleanBarcode)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = barcode.trim().isNotEmpty()
        ) {
            Text("Look Up Food")
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "How it works",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Enter the barcode printed on a food product. HealthySteps will use Open Food Facts to retrieve available nutritional information."
                )
            }
        }
    }
}

@Composable
fun FoodDetailsScreen(
    barcode: String,
    onBackClick: () -> Unit
) {
    var productName by remember { mutableStateOf("Loading...") }
    var brand by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("Loading...") }
    var protein by remember { mutableStateOf("Loading...") }
    var carbohydrates by remember { mutableStateOf("Loading...") }
    var fat by remember { mutableStateOf("Loading...") }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(barcode) {

        try {

            val response = ApiClient.api.getProduct(barcode)

            if (response.status == 1 && response.product != null) {

                val product = response.product
                val nutrition = product.nutriments

                productName =
                    product.product_name ?: "Unknown product"

                brand =
                    product.brands ?: "Unknown brand"

                calories =
                    nutrition?.energy_kcal_100g
                        ?.let { "$it kcal / 100g" }
                        ?: "N/A"

                protein =
                    nutrition?.proteins_100g
                        ?.let { "$it g / 100g" }
                        ?: "N/A"

                carbohydrates =
                    nutrition?.carbohydrates_100g
                        ?.let { "$it g / 100g" }
                        ?: "N/A"

                fat =
                    nutrition?.fat_100g
                        ?.let { "$it g / 100g" }
                        ?: "N/A"

            } else {

                errorMessage =
                    "Product not found for barcode $barcode."

                productName = "Product not found"
                brand = ""
                calories = "N/A"
                protein = "N/A"
                carbohydrates = "N/A"
                fat = "N/A"
            }

        } catch (e: Exception) {

            errorMessage =
                "Unable to retrieve product information. Please check your internet connection."

            productName = "Food lookup failed"
            brand = ""
            calories = "N/A"
            protein = "N/A"
            carbohydrates = "N/A"
            fat = "N/A"
        }
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
            text = "Food Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = productName,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (brand.isNotBlank()) {
                    Text("Brand: $brand")
                }

                Text("Barcode: $barcode")

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(16.dp))

                Text("Calories: $calories")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Protein: $protein")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Carbohydrates: $carbohydrates")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Fat: $fat")

                if (errorMessage.isNotBlank()) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}