package kz.asetkenes.learnandroid.ui.screens.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.asetkenes.learnandroid.R
import kz.asetkenes.learnandroid.common.androidCore.viewModelCreator
import kz.asetkenes.learnandroid.domain.users.entities.User

@Composable
fun UsersScreen(
    viewModel: UsersViewModel = viewModelCreator()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        UsersCount(count = uiState.count)

        UsersList(users = uiState.users)
    }
}

@Composable
fun UsersCount(count: Int, modifier: Modifier = Modifier) {
    val mediumPadding = dimensionResource(R.dimen.medium_padding)
    val largeFontSize = dimensionResource(R.dimen.large_font_size).value.sp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = mediumPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.users_count, count),
            fontSize = largeFontSize
        )
    }
}

@Composable
fun UsersList(users: List<User>, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(all = 10.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(users) { user ->
            UserCard(user)
        }

    }
}

@Composable
fun UserCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = user.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )
            Text(
                text = user.aboutMe,
                modifier = Modifier.padding(
                    start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp
                )
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun UsersScreenPreview() {
    val fakeUsers = (1..100).map {
        User(it.toLong(), "name", "email", "aboutMe", 0, 0)
    }

    Column {
        UsersCount(count = fakeUsers.size)

        UsersList(users = fakeUsers)
    }
}