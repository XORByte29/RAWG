package tech.noope.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import tech.noope.common.domain.data.GameDataModel
import tech.noope.common.domain.data.createGameDataPreview
import tech.noope.common.composable.ui.theme.RAWGTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GameCard(
    game: GameDataModel,
    onClick: (data: GameDataModel) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                onClick(game)
            }
    ) {
        ConstraintLayout {
            val (
                image,
                layoutText,
            ) = createRefs()

            GlideImage(
                model = game.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                it
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
            }

            Column(
                Modifier
                    .background(Color(0xCC292424))
                    .padding(8.dp)
                    .fillMaxWidth()
                    .constrainAs(layoutText) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
            ) {
                Text(
                    text = game.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Black,
                )

                Text(
                    text = "Release date ${game.released}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "",
                        tint = Color(0xFFF57C00),
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = game.rating.toString(),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameCardPreview() {
    RAWGTheme {
        GameCard(createGameDataPreview()) {

        }
    }
}