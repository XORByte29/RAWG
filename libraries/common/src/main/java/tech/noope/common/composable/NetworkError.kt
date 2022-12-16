package tech.noope.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.noope.common.composable.ui.theme.RAWGTheme

@Composable
fun NetworkError(
    title: String = "Oh no!",
    message: String = "A network error occurred. Check your connection or try again",
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(32.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )

        Button(onClick = { onRetryClick() }) {
            Text(
                text = "Try Again",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NetworkErrorPreview() {
    RAWGTheme {
        NetworkError(modifier = Modifier, onRetryClick = {})
    }
}