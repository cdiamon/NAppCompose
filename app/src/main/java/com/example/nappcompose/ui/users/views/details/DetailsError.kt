package com.example.nappcompose.ui.users.views.details

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.nappcompose.R

@Composable
fun DetailsError(message: Throwable?) {
    Text(
        text = message?.localizedMessage ?: stringResource(R.string.detailsError),
        style = MaterialTheme.typography.titleMedium
    )
}
