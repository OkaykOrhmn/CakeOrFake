package com.rhmn.cakeorfake.ui.activity.game

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.rhmn.cakeorfake.R
import com.rhmn.cakeorfake.tools.SharedPrefHandler
import com.rhmn.cakeorfake.tools.SharedPrefHandler.Constants.Companion.GAME_LEVEL_NAME
import com.rhmn.cakeorfake.tools.SoundsEffect.Companion.play
import com.rhmn.cakeorfake.tools.Utils.Companion.finishActivityGoHome
import com.rhmn.cakeorfake.ui.composible.MyComposables.Companion.MyImageBtn
import com.rhmn.cakeorfake.ui.theme.CakeOrFakeTheme
import com.rhmn.cakeorfake.tools.Utils.Companion.finishAllActivity
import com.rhmn.cakeorfake.tools.Utils.Companion.getImageDrawable
import com.rhmn.cakeorfake.ui.activity.home.HomeActivity
import com.rhmn.cakeorfake.ui.composible.MyComposables.Companion.swipeableSample
import com.rhmn.cakeorfake.ui.theme.progressBarBackColor
import com.skydoves.cloudy.Cloudy
import kotlinx.coroutines.delay

class StartGameActivity : ComponentActivity(), Knife {
    var w = mutableStateOf(false)
    var d = mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefHandler = SharedPrefHandler(this)
        if (sharedPrefHandler.getSoundVoice()) {
            play(this, R.raw.realorfake)
        }

        setContent {
            setContent {


                CakeOrFakeTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Game(this, win = w.value, done = d.value)
                    }
                }
            }
        }
    }

    override fun knifeSlideDown(win: Boolean, done: Boolean) {
        w.value = win
        d.value = done
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishActivityGoHome(this)
    }


}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Game(knife: Knife, win: Boolean, done: Boolean) {
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    val sharedPrefHandler = SharedPrefHandler(context)
    val intent = activity!!.intent
    var lvl = intent.getIntExtra(GAME_LEVEL_NAME, 1)
    var lvlP = lvl - 1

    val levelData = sharedPrefHandler.getGameList().data.levels[lvlP]
    val drwble = getImageDrawable(context, levelData.image)

    val drwbleCake = if (levelData.cake) {
        getImageDrawable(context, levelData.image + "_cake")
    } else {
        drwble
    }

    var visible by remember {
        mutableStateOf(false)
    }

    var dialog by remember {
        mutableStateOf(false)
    }

    var popupControl by remember { mutableStateOf(false) }


    LaunchedEffect(done) {
        delay(2000)
        dialog = done
        if (sharedPrefHandler.getSoundVoice()) {
            if (dialog) {
                var what: String

                what = if (levelData.cake) {
                    play(context, R.raw.cake)
                    "کیکه"
                } else {
                    play(context, R.raw.real)
                    "واگعیه"


                }
                Toast.makeText(context, what, Toast.LENGTH_SHORT).show()


            }
        }


    }

    val radius by animateIntAsState(
        targetValue = if (dialog) 18 else 0,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 100,
            easing = LinearOutSlowInEasing
        ), label = ""
    )


    Box(modifier = Modifier.fillMaxSize()) {
        if (popupControl) {
            Dialog(
                onDismissRequest = {
                    popupControl = false
                },
                properties = DialogProperties(usePlatformDefaultWidth = true),
                content = {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            popupControl = false
                        }) {
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.TopEnd)
                                .padding(top = 120.dp, start = 54.dp)
//                        shape = MaterialTheme.shapes.large
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.hint),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 1.5f)
                            )

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(

                                    ),

                                ) {
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.BottomCenter
                                ) {
                                    MyImageBtn(
                                        context = context,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(120.dp)
                                            .padding(horizontal = 12.dp),
                                        drawable = painterResource(id = R.drawable.dont),
                                        click = {
                                            popupControl = false
                                        }
                                    )
                                }
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.BottomCenter
                                ) {
                                    MyImageBtn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(120.dp)
                                            .padding(horizontal = 12.dp),

                                        drawable = painterResource(id = R.drawable.want),
                                        context = context,
                                        click = {
                                            if (sharedPrefHandler.getCoin() >= 500) {
                                                popupControl = false
                                                sharedPrefHandler.setCoin(sharedPrefHandler.getCoin() - 500)
                                                knife.knifeSlideDown(win = true, done = true)
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "سکه کم است ☹️",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                        }
                                    )
                                }


                            }
                        }
                    }

                }
            )
        }

        if (dialog) {


            Dialog(
                onDismissRequest = {
                },
                properties = DialogProperties(usePlatformDefaultWidth = false),
                content = {

                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.dialog_back),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.Center)
//                        shape = MaterialTheme.shapes.large
                        ) {
                            Column(
                                modifier = Modifier.padding(
                                    vertical = 32.dp,
                                    horizontal = 46.dp
                                )
                            ) {
                                if (win) {

                                    Image(
                                        painter = painterResource(id = R.drawable.dialog_win_back),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.dialog_lose_back),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                }


                            }

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(
                                        horizontal = 46.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,

                                ) {
                                Box(modifier = Modifier.weight(1f)) {
                                    MyImageBtn(
                                        context = context,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(86.dp)
                                            .padding(horizontal = 12.dp),
                                        drawable = painterResource(id = R.drawable.menu_btn),
                                        click = {
                                            if (win) {
                                                var coin = sharedPrefHandler.getCoin()
                                                coin += 300
                                                sharedPrefHandler.setCoin(coin)

                                                if (lvl != sharedPrefHandler.getGameList().data.count)
                                                    if (lvl >= sharedPrefHandler.getLevel())
                                                        sharedPrefHandler.setLevel(levelData.level + 1)
                                            }
                                            finishActivityGoHome(context)
                                        }
                                    )
                                }
                                Box(modifier = Modifier.weight(1f)) {
                                    MyImageBtn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(86.dp)
                                            .height(86.dp)
                                            .padding(horizontal = 12.dp),

                                        drawable = painterResource(id = R.drawable.next_btn),
                                        context = context,
                                        click = {
                                            if (win) {
                                                var coin = sharedPrefHandler.getCoin()
                                                coin += 300
                                                sharedPrefHandler.setCoin(coin)


                                                if (lvl != sharedPrefHandler.getGameList().data.count)
                                                    if (lvl >= sharedPrefHandler.getLevel())
                                                        sharedPrefHandler.setLevel(levelData.level + 1)
                                            }

                                            if (levelData.level == sharedPrefHandler.getGameList().data.count) {
                                                val intent =
                                                    Intent(context, HomeActivity::class.java)
                                                finishAllActivity(context)
                                                context.startActivity(intent)
                                            } else {
                                                val intent =
                                                    Intent(context, StartGameActivity::class.java)
                                                var lev = levelData.level
                                                if (win) {
                                                    lev += 1
                                                }
                                                intent.putExtra(GAME_LEVEL_NAME, lev)
                                                finishActivityGoHome(context)
                                                context.startActivity(intent)
                                            }
                                        }
                                    )
                                }


                            }
                        }
                    }


                },

                )
        }


//        Cloudy(
//            radius = radius,
//        ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .blur(radius.dp)
                .paint(
                    painter = painterResource(id = R.drawable.game_stage),
                    contentScale = ContentScale.Crop
                ),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.TopCenter
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 64.dp)
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        MyImageBtn(
                            modifier = Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    spotColor = Color(0x1A000000),
                                    ambientColor = Color(0x1A000000)
                                )
                                .padding(0.dp)
                                .size(72.dp),
                            drawable = painterResource(id = R.drawable.back_btns),
                            context = context,
                            click = {
                                finishActivityGoHome(context)
                            }
                        )
                    }

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text(
                            text = "مرحله $lvl",
                            fontSize = 32.sp,
                            fontFamily = FontFamily(Font(R.font.bkoodakbold)),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(85.dp)
                                .height(85.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sample),
                                contentDescription = "image description",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .border(
                                        width = 3.dp,
                                        color = Color(0xFFFFFFFF),
                                        shape = RoundedCornerShape(size = 53.dp)
                                    )
                                    .size(85.dp)
                                    .clip(shape = RoundedCornerShape(size = 53.dp))
                                    .clickable {
                                        popupControl = !popupControl
                                    }
                            )
                            Image(
                                modifier = Modifier
                                    .padding(0.dp)
                                    .size(30.dp)
                                    .offset(x = 55.dp, y = 55.dp),
                                painter = painterResource(id = R.drawable.hint_btn),
                                contentDescription = "image description",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f)
            ) {

//                Image(
//                    painter = painterResource(id = imageDrwbl),
//                    contentDescription = "",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .size(LocalConfiguration.current.screenWidthDp.dp / 2)
//                        .align(
//                            Alignment.Center
//                        )
//                )
                Box(
                    modifier = Modifier

                        .fillMaxSize()
                        .align(
                            Alignment.Center
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        targetState = levelData.cake && done,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(durationMillis = 1500)) with
                                    fadeOut(animationSpec = tween(durationMillis = 500))
                        }, label = ""
                    ) { targetState ->
                        Image(
                            painter = painterResource(id = if (!targetState) drwble else drwbleCake),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 2)
                        )
                    }
                }

                if (visible) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopEnd)
                    ) {
                        swipeableSample(knife, levelData.cake)

                        Text(
                            text = "چاقو رو بکشش پایین 😉⬇️",
                            fontSize = 32.sp,
                            fontFamily = FontFamily(Font(R.font.bkoodakbold)),
                            color = progressBarBackColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )

                    }
                }


            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(bottom = 72.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        MyImageBtn(
                            modifier = Modifier
                                .padding(bottom = 0.dp, top = 8.dp, start = 0.dp, end = 0.dp)
                                .width(LocalConfiguration.current.screenWidthDp.dp / 2)
                                .height(76.dp),
                            drawable = painterResource(id = R.drawable.real_btn),
                            context = context,
                            click = {

                                knife.knifeSlideDown(!levelData.cake, true)

                            }

                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        MyImageBtn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(86.dp),
                            drawable = painterResource(id = R.drawable.cake_btn),
                            context = context,
                            click = {
                                visible = true
                            }
                        )
                    }
                }
            }

        }


//        }


    }


}

