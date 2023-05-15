package com.example.nappcompose.ui.users.views.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nappcompose.domain.models.User
import com.example.nappcompose.ui.theme.GHTypography


@Composable
fun UserItem(user: User.UserModel?, onItemClicked: (String?) -> Unit) {
    val onNameClick = remember(user) { { onItemClicked(user?.login) } }

    Box(
        modifier = Modifier
            .clickable(onClick = onNameClick)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile image
            AsyncImage(
                model = user?.avatarUrl,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(56.dp),
                contentDescription = "avatar"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // Text that shows the login
                    Text(
                        text = user?.login.toString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = GHTypography.bodyLarge
                    )

                    // Text that shows the id
                    Text(
                        text = "id: ${user?.id.toString()}",
                        style = GHTypography.bodyMedium
                    )

                }

                // Text that shows the message
                Text(
                    modifier = Modifier
                        .padding(top = 2.dp),
                    text = user?.htmlUrl.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = GHTypography.bodySmall
                )
            }
        }
    }
}
