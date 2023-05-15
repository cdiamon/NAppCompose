package com.example.nappcompose.ui.users.views.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nappcompose.domain.models.User

@Composable
fun UserDetails(user: User.UserModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.avatarUrl,
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
                text = user.login,
                style = MaterialTheme.typography.titleMedium
            )
            user.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Since ${user.createdAt.toString()}",
        style = MaterialTheme.typography.bodyLarge
    )
    user.bio?.let {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = it,
            style = MaterialTheme.typography.bodyLarge
        )
    }
    user.location?.let {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "from $it",
            style = MaterialTheme.typography.bodyLarge
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "followers: ${user.followers ?: 0}",
        style = MaterialTheme.typography.bodyLarge
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "repos: ${user.publicRepos.toString()}",
        style = MaterialTheme.typography.bodyLarge
    )
}
