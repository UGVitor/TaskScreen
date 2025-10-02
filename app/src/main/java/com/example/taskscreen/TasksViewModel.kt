package com.example.taskscreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchTasks()
    }

    fun refreshTasks() {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                delay(2000) // Simula chamada de rede

                // Simula erro aleatório
                if (System.currentTimeMillis() % 2 == 0L) {
                    throw Exception("Falha ao conectar com o servidor.")
                }

                val tasks = listOf("Comprar pão", "Estudar MVVM", "Passear com o cachorro", "Ler 10 paginas", "Não tirar 0 na atividade")
                _uiState.value = UiState.Success(tasks)

            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}