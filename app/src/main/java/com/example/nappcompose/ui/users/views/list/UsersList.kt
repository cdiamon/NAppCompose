package com.example.nappcompose.ui.users.views.list

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.nappcompose.ui.theme.Purple500
import com.example.nappcompose.ui.users.UsersViewModel
import com.example.nappcompose.ui.users.UsersViewModelImpl

/**
 * List of users with pagination.
 * @param onItemClicked callback to be invoked when an item is clicked.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UsersList(
    onItemClicked: (String?) -> Unit,
) {
    val viewModel: UsersViewModel = hiltViewModel<UsersViewModelImpl>()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val users = viewModel.userList.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.pointerInteropFilter {
        keyboardController?.hide()
        false
    }) {
        items(
            count = users.itemCount,
            key = users.itemKey(),
            contentType = users.itemContentType()
        ) { index ->
            val user = users[index]
            UserItem(user = user, onItemClicked = onItemClicked)
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
