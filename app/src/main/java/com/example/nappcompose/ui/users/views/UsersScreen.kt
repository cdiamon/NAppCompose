package com.example.nappcompose.ui.users.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nappcompose.ui.users.UsersViewModel
import com.example.nappcompose.ui.users.views.details.BottomSheet
import com.example.nappcompose.ui.users.views.list.UsersList
import com.example.nappcompose.ui.users.views.search.SearchView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UsersScreen() {
    val viewModel = hiltViewModel<UsersViewModel>()

    val searchText = remember {
        viewModel.searchText
    }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()
    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { BottomSheet() },
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            SearchView(searchText)

            UsersList(
                onItemClicked = {
                    coroutineScope.launch {
                        if (!it.isNullOrBlank()) {
                            viewModel.fetchUserDetails(it)
                        }
                        sheetState.show()
                    }
                }
            )
        }
    }
}
