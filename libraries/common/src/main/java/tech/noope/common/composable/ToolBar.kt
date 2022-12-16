package tech.noope.common.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import tech.noope.common.composable.ui.theme.RAWGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(
    title: String,
    showBackButton: Boolean = false,
    onBackButtonClicked: () -> Unit = {}
) {
    if (showBackButton) {
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = { onBackButtonClicked() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
    } else {
        TopAppBar(
            title = { Text(text = title) },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ToolBarPriview() {
    RAWGTheme {
        ToolBar(title = "Preview Toolbar")
    }
}

@Preview(showBackground = true)
@Composable
fun ToolBarWithBackIconPriview() {
    RAWGTheme {
        ToolBar(title = "Preview Toolbar", showBackButton = true) { }
    }
}