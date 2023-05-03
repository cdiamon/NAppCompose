package com.example.nappcompose.ui.users.views.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.nappcompose.data.networkmodels.ResultStatus
import com.example.nappcompose.ui.users.UsersViewModel

@Composable
fun BottomSheet() {
    val viewModel = hiltViewModel<UsersViewModel>()
    val details = viewModel.userDetails.collectAsState().value

    Column(
        modifier = Modifier.padding(32.dp)
    ) {
        when (details.status) {
            ResultStatus.Status.SUCCESS -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = details.data?.avatarUrl,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(20))
                            .size(92.dp),
                        contentDescription = "avatar"
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = details.data?.login.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                        details.data?.name?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Since ${details.data?.createdAt.toString()}",
                    style = MaterialTheme.typography.bodyLarge
                )
                details.data?.bio?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                details.data?.location?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "from $it",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "has repos: ${details.data?.publicRepos.toString()}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            ResultStatus.Status.ERROR -> {
                Text(
                    text = details.error?.localizedMessage ?: "Details fetching error",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            ResultStatus.Status.LOADING -> {
                CircularProgressIndicator()
            }
        }
    }
}
