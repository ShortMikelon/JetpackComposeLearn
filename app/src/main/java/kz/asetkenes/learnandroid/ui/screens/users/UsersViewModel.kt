package kz.asetkenes.learnandroid.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.asetkenes.learnandroid.Repositories
import kz.asetkenes.learnandroid.domain.users.UsersRepository
import kz.asetkenes.learnandroid.domain.users.entities.User

class UsersViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val users = usersRepository.getAllUsers()
            _uiState.value = UsersUiState(users, users.size)
        }
    }

    data class UsersUiState(
        val users: List<User> = emptyList(),
        val count: Int = 0
    )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository = Repositories.usersRepository
                UsersViewModel(repository)
            }
        }
    }

}