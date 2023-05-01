package com.example.nappcompose.ui.users.views.list

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.example.nappcompose.domain.models.UserModel
import com.example.nappcompose.ui.theme.GHTypography
import com.example.nappcompose.ui.theme.Purple500
import com.example.nappcompose.ui.users.UsersViewModel

/**
 * Entry point for a chart screen.
 * @param onItemClicked callback to be invoked when an item is clicked.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UsersList(
    onItemClicked: (String?) -> Unit,
) {
    val viewModel = hiltViewModel<UsersViewModel>()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val users = viewModel.userList.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.pointerInteropFilter {
        keyboardController?.hide()
        false
    }) {
        items(users) { user: UserModel.Generic? ->
            Box(modifier = Modifier
                .clickable { onItemClicked(user?.login) }
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
                        model = user?.avatar_url,
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

        when (users.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                //TODO Error Item
                //state.error to get error message
            }
            is LoadState.Loading -> { // Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refresh Loading"
                        )

                        CircularProgressIndicator(color = Purple500)
                    }
                }
            }
            else -> {}
        }

        when (users.loadState.append) { // Pagination
            is LoadState.Error -> {
                Toast.makeText(
                    context,
                    "Pagination Error",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "Pagination Loading")

                        CircularProgressIndicator(color = Purple500)
                    }
                }
            }
            else -> {}
        }
    }
}
