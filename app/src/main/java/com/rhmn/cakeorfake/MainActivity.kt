package com.rhmn.cakeorfake

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.rhmn.cakeorfake.tools.SharedPrefHandler
import com.rhmn.cakeorfake.tools.SoundsEffect.Companion.playIntro
import com.rhmn.cakeorfake.tools.Utils.Companion.createData
import com.rhmn.cakeorfake.ui.activity.home.HomeActivity
import com.rhmn.cakeorfake.ui.composible.MyComposables.Companion.TypewriterText
import com.rhmn.cakeorfake.ui.theme.CakeOrFakeTheme
import com.rhmn.cakeorfake.ui.theme.progressBarBackColor
import com.rhmn.cakeorfake.ui.theme.progressBarColor
import com.rhmn.cakeorfake.tools.Utils.Companion.finishActivity
import com.rhmn.cakeorfake.tools.Utils.Companion.goToActivity
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        playIntro(this)
        setContent {
            CakeOrFakeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView("Android")
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(Unit) {
        while (true) {
            val sharedPrefHandler = SharedPrefHandler(context)
//            if(sharedPrefHandler.isFirstTime()){
            sharedPrefHandler.setGameList(createData(context))
//                sharedPrefHandler.itsFirstTime()
//            }
            delay(4500)
            goToActivity(context, HomeActivity::class.java)
            finishActivity(context)


        }
    }


    val transition = rememberInfiniteTransition(label = "")
//    val transition  = updateTransition(targetState = true, label = "")
    var componentWidth by remember { mutableStateOf(0.dp) }

    val offsetAnimation by transition.animateValue(
        initialValue = 0.dp,
        targetValue = componentWidth,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 5000
                componentWidth / 10 at 1000 with LinearOutSlowInEasing
                componentWidth / 8 at 2000 with LinearOutSlowInEasing
                componentWidth / 5 at 3000 with LinearOutSlowInEasing
                componentWidth / 4 at 4000 with LinearOutSlowInEasing
                componentWidth at 5000 with LinearOutSlowInEasing

            },
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    Column(
        Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.splash),
                contentScale = ContentScale.Crop
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.padding(horizontal = 58.dp),
            painter = painterResource(id = R.drawable.cake_or_fake_logo),
            contentDescription = "main-logo"
        )
        TypewriterText(
            texts = listOf(
                " ",
                "...",
            ),
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .fillMaxWidth()
                .onGloballyPositioned {
                    componentWidth = it.size.width.dp

                }
                .border(width = 4.dp, color = Color.White, shape = RoundedCornerShape(40))
                .background(progressBarBackColor, shape = RoundedCornerShape(40))


        ) {
            Box(
                modifier = Modifier
                    .width(offsetAnimation)
                    .height(34.dp)
                    .padding(4.dp)
                    .background(progressBarColor, shape = RoundedCornerShape(60))
                    .border(width = 0.dp, color = Color.Transparent, shape = RoundedCornerShape(60))


            )
        }

    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CakeOrFakeTheme {
        GreetingView("Android")
    }
}