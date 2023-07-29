package kz.asetkenes.learnandroid.ui.screens.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.asetkenes.learnandroid.domain.signin.entities.SignInField
import kz.asetkenes.learnandroid.domain.signin.exceptions.EmptyFieldException
import kz.asetkenes.learnandroid.domain.users.UsersRepository
import kz.asetkenes.learnandroid.domain.signin.entities.UserSignInData
import kz.asetkenes.learnandroid.domain.users.exceptions.AuthException
import kz.asetkenes.learnandroid.ui.viewutils.SideEffect
import kz.asetkenes.learnandroid.ui.viewutils.publishSideEffect

class SignInViewModel(
    private val usersRepository: UsersRepository,
) : ViewModel() {

    private val _uiStateStateFlow = MutableStateFlow(SignInUiState())
    val uiStateStateFlow = _uiStateStateFlow.asStateFlow()

    private val _emptyFieldStateFlow = MutableStateFlow<SideEffect<SignInField?>>(SideEffect(null))
    val emptyFieldStateFlow = _emptyFieldStateFlow.asStateFlow()

    private val _routeStateFlow = MutableStateFlow<SideEffect<String?>>(SideEffect(null))
    val routeStateFlow = _routeStateFlow.asStateFlow()

    init {
        if (usersRepository.isSignIn()) {
            _routeStateFlow.publishSideEffect(null)
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                updateState { copy(inProgress = true) }
                usersRepository.signIn(
                    UserSignInData(email, password)
                )
                delay(2000)
                _routeStateFlow.publishSideEffect(null)
            } catch (ex: AuthException) {
                TODO()
            } catch (ex: EmptyFieldException) {
                val field = ex.field
                _emptyFieldStateFlow.publishSideEffect(field)
            } finally {
                updateState { copy(inProgress = false) }
            }
        }
    }

    private fun updateState(block: SignInUiState.() -> SignInUiState) {
        _uiStateStateFlow.value = _uiStateStateFlow.value.block()
    }

}

