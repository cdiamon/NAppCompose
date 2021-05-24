package com.example.nappcompose.ui.chart

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nappcompose.R
import com.example.nappcompose.ui.theme.Purple700

@Composable
fun SwitchFilterBottom(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
    filterText: LiveData<String>
) {
    val bottomOffset = 64.dp
    val text = filterText.observeAsState()

    ExtendedFloatingActionButton(
        icon = {
            Icon(
                imageVector = Icons.Filled.Refresh,
                modifier = Modifier.height(18.dp),
                contentDescription = null
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.switch_filter) + text.value,
            )
        },
        onClick = onClicked,
        backgroundColor = Purple700,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = modifier
            .offset(x = 0.dp, y = -bottomOffset)
            .height(36.dp)
    )
}

@Preview
@Composable
fun SwitchFilterPreview() {
    SwitchFilterBottom(onClicked = {}, filterText = MutableLiveData(" (Filter name)"))
}
