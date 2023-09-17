@file:OptIn(ExperimentalMaterial3Api::class)

package kz.asetkenes.learnandroid.ui.screens.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import kz.asetkenes.learnandroid.R
import kz.asetkenes.learnandroid.common.androidCore.viewModelCreator
import kz.asetkenes.learnandroid.common.core.Logger
import kz.asetkenes.learnandroid.domain.signup.entities.DATE_BIRTHDAY_NOT_SELECTED
import kz.asetkenes.learnandroid.domain.signup.entities.SignUpField
import kz.asetkenes.learnandroid.utils.convertTimestampToString

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = viewModelCreator(),
    logger: Logger
) {
    val navigationTrigger = viewModel.navigationSideEffect.observeAsState(null)

    LaunchedEffect(key1 = navigationTrigger, block = {
        val route = navigationTrigger.value?.get()
        logger.debug(route ?: "null")

        if (route != null)
            navController.navigate(
                route,
                navOptions {
                    popUpTo(route) { inclusive = true }
                }
            )
    })

    val state by viewModel.uiState.collectAsState()
    val errorFields by viewModel.errorFields.collectAsState()

    SignUpDisplay(
        state = state,
        errorFields = errorFields,
        onTextChanged = { field, newValue ->
            viewModel.obtainIntent(
                SignUpIntent.OnValueChanged(
                    field,
                    newValue
                )
            )
        },
        onDateSelected = { newDate -> viewModel.obtainIntent(SignUpIntent.OnDateChanged(newDate)) },
        onSignUpClick = { viewModel.obtainIntent(SignUpIntent.SignUpClicked) },
    )
}

@Composable
fun SignUpDisplay(
    state: SignUpUiState,
    errorFields: Map<SignUpField, String>,
    onTextChanged: (SignUpField, String) -> Unit,
    onDateSelected: (Long) -> Unit,
    onDismissDialog: () -> Unit = { },
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var openDatePickerDialog by remember { mutableStateOf(false) }

    val smallPadding = dimensionResource(R.dimen.small_padding)

    if (openDatePickerDialog) {
        val onDismiss = {
            openDatePickerDialog = false
            onDismissDialog()
        }

        DateOfBirthdayPickerDialog(
            onDismiss = onDismiss,
            onDateSelected = onDateSelected
        )
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                fontSize = dimensionResource(R.dimen.large_font_size).value.sp
            )

            SignUpFields(
                state = state,
                errorFields = errorFields,
                onTextChanged = onTextChanged,
                bottomPadding = smallPadding,
                onSelectDateOfBirthday = { openDatePickerDialog = true },
            )

            Button(
                onClick = { onSignUpClick() },
                enabled = state.enabled,
                modifier = Modifier
                    .width(280.dp)
                    .padding(top = dimensionResource(R.dimen.large_padding))
            ) {
                if (state.isInProgress) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(stringResource(R.string.sign_up))
                }
            }
        }
    }
}

@Composable
private fun SignUpFields(
    state: SignUpUiState,
    errorFields: Map<SignUpField, String>,
    onTextChanged: (SignUpField, String) -> Unit,
    bottomPadding: Dp,
    onSelectDateOfBirthday: () -> Unit,
) {

    Column(
        modifier = Modifier
            .padding(top = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {

        val nameIsError = errorFields[SignUpField.NAME] != null
        OutlinedTextField(
            value = state.name,
            label = { Text(stringResource(R.string.name)) },
            onValueChange = { text -> onTextChanged(SignUpField.NAME, text) },
            enabled = state.enabled,
            trailingIcon = {
                if (nameIsError)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
            },
            supportingText = {
                if (nameIsError)
                    Text(
                        text = errorFields[SignUpField.NAME]!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
            },
            isError = nameIsError,
            singleLine = true,
            modifier = Modifier.padding(bottom = bottomPadding)
        )

        val emailIsError = errorFields[SignUpField.EMAIL] != null
        OutlinedTextField(
            value = state.email,
            label = { Text(stringResource(R.string.email)) },
            onValueChange = { text -> onTextChanged(SignUpField.EMAIL, text) },
            enabled = state.enabled,
            trailingIcon = {
                if (emailIsError)
                    Icon(
                        painter = painterResource(R.drawable.ic_error),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
            },
            supportingText = {
                if (emailIsError)
                    Text(
                        text = errorFields[SignUpField.EMAIL]!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
            },
            isError = emailIsError,
            singleLine = true,
            modifier = Modifier.padding(bottom = bottomPadding)
        )

        PasswordTextField(
            value = state.password,
            field = SignUpField.PASSWORD,
            label = stringResource(R.string.password),
            enabled = state.enabled,
            onTextChanged = onTextChanged,
            errorFields = errorFields,
            bottomPadding = bottomPadding
        )

        PasswordTextField(
            value = state.repeatPassword,
            field = SignUpField.REPEAT_PASSWORD,
            label = stringResource(R.string.repeat_password),
            enabled = state.enabled,
            onTextChanged = onTextChanged,
            errorFields = errorFields,
            bottomPadding = bottomPadding
        )

        val aboutMeIsError = errorFields[SignUpField.ABOUT_ME] != null
        OutlinedTextField(
            value = state.aboutMe,
            label = { Text(stringResource(R.string.about_me)) },
            onValueChange = { text -> onTextChanged(SignUpField.ABOUT_ME, text) },
            enabled = state.enabled,
            trailingIcon = {
                if (aboutMeIsError)
                    Icon(
                        painter = painterResource(R.drawable.ic_error),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
            },
            supportingText = {
                if (aboutMeIsError)
                    Text(
                        text = errorFields[SignUpField.ABOUT_ME]!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
            },
            isError = aboutMeIsError,
            modifier = Modifier.padding(bottom = bottomPadding)
        )

        val dateOfBirthdayFieldText =
            if (state.dateBirthDay == DATE_BIRTHDAY_NOT_SELECTED) stringResource(R.string.not_selected)
            else convertTimestampToString(state.dateBirthDay, onlyDate = true)

        OutlinedTextField(
            value = dateOfBirthdayFieldText,
            label = { Text(stringResource(R.string.date_of_birthday)) },
            onValueChange = { },
            readOnly = true,
            enabled = state.enabled,
            trailingIcon = {
                IconButton(onClick = { onSelectDateOfBirthday() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_select_date_of_birthday),
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier.padding(bottom = bottomPadding)
        )
    }
}

@Composable
fun PasswordTextField(
    value: String,
    field: SignUpField,
    label: String,
    enabled: Boolean,
    errorFields: Map<SignUpField, String>,
    onTextChanged: (SignUpField, String) -> Unit,
    bottomPadding: Dp
) {
    var showPassword by remember { mutableStateOf(false) }
    val isError = errorFields[field] != null

    OutlinedTextField(
        value = value,
        label = { Text(label) },
        onValueChange = { newValue ->
            onTextChanged(field, newValue)
        },
        enabled = enabled,
        trailingIcon = {
            val iconId =
                if (showPassword) R.drawable.ic_visibility_off
                else R.drawable.ic_visibility
            IconButton(
                onClick = { showPassword = !showPassword }
            ) {
                Icon(
                    painter = painterResource(iconId),
                    contentDescription = null
                )
            }
        },
        supportingText = {
            if (isError)
                Text(
                    text = errorFields[field]!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
        },
        isError = isError,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation =
        if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier.padding(bottom = bottomPadding)
    )
}

@Composable
fun DateOfBirthdayPickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState(System.currentTimeMillis())
    val selectedDate = datePickerState.selectedDateMillis ?: System.currentTimeMillis()

    Surface {
        DatePickerDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                Button(onClick = {
                    onDateSelected(selectedDate)
                    onDismiss()
                }) {
                    Text("Ok")
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.medium_rounded)),
            modifier = modifier
                .fillMaxWidth()
                .height(560.dp)
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}

@Preview
@Composable
fun SignUpDisplayPreview() {
    val state = SignUpUiState(
        name = "name",
        email = "email",
        password = "password",
        repeatPassword = "repeatPassword",
        aboutMe = "aboutMe",
        dateBirthDay = 0,
        isInProgress = true
    )
    SignUpDisplay(
        state = state,
        errorFields = emptyMap(),
        onTextChanged = { _, _ -> },
        onDateSelected = { },
        onSignUpClick = { },
    )
}

@Preview
@Composable
fun DateOfBirthdayPickerDialogPreview() {
    DateOfBirthdayPickerDialog(onDismiss = { }, onDateSelected = { })
}