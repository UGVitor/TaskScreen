package com.example.taskscreen
// TasksScreen.kt

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskscreen.UiState
import com.example.taskscreen.TasksViewModel

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel = viewModel()) {
    val uiState by tasksViewModel.uiState.collectAsState()

    when (val state = uiState) {
        is UiState.Loading -> LoadingComponent()
        is UiState.Success -> TasksListComponent(tasks = state.data) {
            tasksViewModel.refreshTasks()
        }
        is UiState.Error -> ErrorComponent(message = state.message) {
            tasksViewModel.refreshTasks()
        }
    }
}

@Composable
fun LoadingComponent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun TasksListComponent(tasks: List<String>, onRefreshClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Minhas Tarefas", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        tasks.forEach { task ->
            Text(task, style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onRefreshClick) {
            Text("Recarregar")
        }
    }
}

@Composable
fun ErrorComponent(message: String, onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ocorreu um erro:", color = MaterialTheme.colorScheme.error)
        Text(message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetryClick) {
            Text("Tentar Novamente")
        }
    }
}
