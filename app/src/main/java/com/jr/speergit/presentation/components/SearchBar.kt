package com.jr.speergit.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.jr.speergit.presentation.home.HomeViewModel
import com.jr.speergit.presentation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchText: String, viewModel: HomeViewModel) {
    val focusManager = LocalFocusManager.current
    val textFieldFocusRequester = remember { FocusRequester() }

    TextField(
        value = searchText,
        onValueChange = viewModel::onSearchTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(textFieldFocusRequester),
        trailingIcon = {
            SearchIcon(iconTint = AppTheme.colorScheme.iconTint)
        },
        placeholder = { Text(text = "Search") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
            }
        )
    )
}

@Composable
fun SearchIcon(iconTint: Color) {
    Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "search icon",
        tint = iconTint
    )
}