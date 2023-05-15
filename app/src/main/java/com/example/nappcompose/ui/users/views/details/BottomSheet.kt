package com.example.nappcompose.ui.users.views.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nappcompose.domain.models.User
import com.example.nappcompose.ui.users.UsersViewModel
import com.example.nappcompose.ui.users.UsersViewModelImpl

@Composable
fun BottomSheet() {
    val viewModel: UsersViewModel = hiltViewModel<UsersViewModelImpl>()
    val details = viewModel.userDetails.collectAsState().value

    Column(
        modifier = Modifier.padding(32.dp)
    ) {
        details.onSuccess { user ->
            when (user) {
                is User.UserModel -> {
                    UserDetails(user)
                }
                is User.Loading -> {
                    CircularProgressIndicator()
                }
                is User.Error -> {
                    DetailsError(Throwable(user.message))
                }
            }
        }.onFailure {
            DetailsError(it)
        }
    }
}
