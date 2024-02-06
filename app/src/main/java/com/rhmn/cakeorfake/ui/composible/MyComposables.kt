package com.rhmn.cakeorfake.ui.composible

import android.content.Context
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import com.rhmn.cakeorfake.R
import com.rhmn.cakeorfake.tools.SoundsEffect
import com.rhmn.cakeorfake.ui.activity.game.Knife
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

class MyComposables {
    companion object {

        @Composable
        fun MyImageBtn(modifier: Modifier, drawable: Painter, click: () -> Unit, context: Context) {
            val interactionSource = remember { MutableInteractionSource() }
            Image(
                painter = drawable,
                contentDescription = "",
                modifier = modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        SoundsEffect.playClick(context = context)
                        click()
                    }
            )
        }


        @Composable
        fun TypewriterText(
            texts: List<String>,
        ) {
            var textIndex by remember {
                mutableStateOf(0)
            }
            var textToDisplay by remember {
                mutableStateOf("")
            }

            LaunchedEffect(
                key1 = texts,
            ) {
                while (textIndex < texts.size) {
                    texts[textIndex].forEachIndexed { charIndex, _ ->
                        textToDisplay = texts[textIndex]
                            .substring(
                                startIndex = 0,
                                endIndex = charIndex + 1,
                            )
                        delay(160)
                    }
                    textIndex = (textIndex + 1) % texts.size
                    delay(100)
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = textToDisplay,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Right,
                    fontFamily = FontFamily(
                        Font(
                            R.font.bkoodakbold
                        )
                    )

                )
                Text(
                    text = "درحال بارگذاری",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(2f),
                    textAlign = TextAlign.Left,
                    fontFamily = FontFamily(
                        Font(
                            R.font.bkoodakbold
                        )
                    )

                )

            }


        }

        @OptIn(ExperimentalComposeUiApi::class)
        @Composable
        fun swipeableSample(knife: Knife, cake: Boolean) {
            val context = LocalContext.current

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.TopEnd
            ) {
                var offsetX by remember { mutableStateOf(0f) }
                var offsetY by remember { mutableStateOf(0f) }
                var disble by remember { mutableStateOf(true) }

                fun Modifier.gesturesDisabled(disabled: Boolean = disble) =
                    if (disabled) {
                        pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()

                                val (x, y) = dragAmount
                                when {
                                    x > 0 -> { /* right */
                                    }

                                    x < 0 -> { /* left */
                                    }

                                }
                                when {
                                    y > 0 -> { /* down */
                                    }

                                    y < 0 -> { /* up */
                                    }

                                }

//                                offsetX += dragAmount.x
                                if (dragAmount.y > 0) {
                                    offsetY += dragAmount.y

                                }

                                if (offsetY >= 800) {
                                    knife.knifeSlideDown(cake, true)
                                    disble = false

                                }


                            }
                        }
                    } else {
                        this
                    }

                Box(
                    Modifier
                        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                        .paint(
                            painterResource(id = R.drawable.knife),
                            contentScale = ContentScale.None,
                        )
                        .gesturesDisabled(disble)
                )
            }
        }

        @Composable
        fun Shaker() {
            val transition = rememberInfiniteTransition(label = "")

        }


    }
}