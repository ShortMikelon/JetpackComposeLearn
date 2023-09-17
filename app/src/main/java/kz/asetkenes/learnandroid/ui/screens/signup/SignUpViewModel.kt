package kz.asetkenes.learnandroid.ui.screens.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.asetkenes.learnandroid.R
import kz.asetkenes.learnandroid.common.core.IntentHandler
import kz.asetkenes.learnandroid.common.core.Logger
import kz.asetkenes.learnandroid.common.core.Resources
import kz.asetkenes.learnandroid.data.room.StorageException
import kz.asetkenes.learnandroid.domain.signup.entities.SignUpEntity
import kz.asetkenes.learnandroid.domain.signup.entities.SignUpField
import kz.asetkenes.learnandroid.domain.signup.exceptions.DateOfBirthdayNotSelectedException
import kz.asetkenes.learnandroid.domain.signup.exceptions.EmptyFieldsException
import kz.asetkenes.learnandroid.domain.signup.exceptions.PasswordMismatchException
import kz.asetkenes.learnandroid.domain.users.UsersRepository
import kz.asetkenes.learnandroid.ui.navigation.MainDestination
import kz.asetkenes.learnandroid.ui.viewutils.SideEffect

class SignUpViewModel(
    private val usersRepository: UsersRepository,
    private val logger: Logger,
    private val resources: Resources
) : ViewModel(), IntentHandler<SignUpIntent> {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _errorFields = MutableStateFlow<Map<SignUpField, String>>(emptyMap())
    val errorFields = _errorFields.asStateFlow()

    private val _navigationSideEffect = MutableLiveData<SideEffect<String>>()
    val navigationSideEffect: LiveData<SideEffect<String>> = _navigationSideEffect

    override fun obtainIntent(intent: SignUpIntent) {
        when (intent) {
            SignUpIntent.SignUpClicked -> signUp()
            is SignUpIntent.OnValueChanged -> onValueChanged(intent.field, intent.newValue)
            is SignUpIntent.OnDateChanged -> onDateChanged(intent.date)
            is SignUpIntent.OnFieldClicked -> clearErrorField(intent.field)
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            try {
                updateUiState {
                    copy(
                        name = name.trim(),
                        email = email.trim(),
                        aboutMe = aboutMe.trim()
                    )
                }

                val signUpData = SignUpEntity(
                    _uiState.value.name,
                    _uiState.value.email,
                    _uiState.value.password,
                    _uiState.value.repeatPassword,
                    _uiState.value.aboutMe,
                    _uiState.value.dateBirthDay
                )

                usersRepository.signUp(signUpData)
                updateUiState { copy(isInProgress = true) }

                _navigationSideEffect.value = SideEffect(MainDestination.HOME_DESTINATION)
            } catch (ex: PasswordMismatchException) {
                _errorFields.value =
                    mapOf(SignUpField.REPEAT_PASSWORD to resources.getString(R.string.mismatch_password))
            } catch (ex: EmptyFieldsException) {
                _errorFields.value =
                    ex.fields.associateBy({ it }, { resources.getString(R.string.empty_field) })
            } catch (ex: DateOfBirthdayNotSelectedException) {
                _errorFields.value =
                    mapOf(SignUpField.DATE_OF_BIRTHDAY to resources.getString(R.string.date_of_birthday_not_selected))
            } catch (ex: StorageException) {
                logger.error(ex)
                throw ex
            } catch (ex: Exception) {
                logger.error(ex)
                throw ex
            } finally {
                updateUiState { copy(isInProgress = false) }
            }
        }
    }

    private fun onValueChanged(field: SignUpField, newValue: String) {
        clearErrorField(field)
        when (field) {
            SignUpField.NAME -> updateUiState { copy(name = newValue) }
            SignUpField.EMAIL -> updateUiState { copy(email = newValue) }
            SignUpField.PASSWORD -> updateUiState { copy(password = newValue) }
            SignUpField.REPEAT_PASSWORD -> updateUiState { copy(repeatPassword = newValue) }
            SignUpField.ABOUT_ME -> updateUiState { copy(aboutMe = newValue) }
            SignUpField.DATE_OF_BIRTHDAY -> throw IllegalStateException("DATE_OF_BIRTHDAY field changed incorrectly.")
        }
    }

    private fun onDateChanged(newDate: Long) {
        updateUiState { copy(dateBirthDay = newDate) }
    }

    private fun clearErrorField(field: SignUpField) {
        _errorFields.update { it.filter { (key, _) -> key != field } }
    }

    private fun updateUiState(block: SignUpUiState.() -> SignUpUiState) {
        _uiState.value = _uiState.value.block()
    }
}

