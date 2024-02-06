package com.rhmn.cakeorfake.ui.activity.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rhmn.cakeorfake.R
import com.rhmn.cakeorfake.tools.BackgroundSoundService
import com.rhmn.cakeorfake.tools.SharedPrefHandler
import com.rhmn.cakeorfake.tools.SoundsEffect.Companion.playClick
import com.rhmn.cakeorfake.tools.Utils
import com.rhmn.cakeorfake.ui.activity.game.StartGameActivity
import com.rhmn.cakeorfake.ui.composible.BottomSheet
import com.rhmn.cakeorfake.ui.composible.MyComposables.Companion.MyImageBtn
import com.rhmn.cakeorfake.ui.theme.CakeOrFakeTheme
import com.rhmn.cakeorfake.ui.theme.primaryColor
import com.rhmn.cakeorfake.ui.theme.progressBarBackColor


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val intent = Intent(this, BackgroundSoundService::class.java)
//        startService(intent)
        setContent {

            CakeOrFakeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val intent = Intent(this, BackgroundSoundService::class.java)
        stopService(intent)
    }

    override fun onResume() {
        super.onResume()

        val sharedPrefHandler = SharedPrefHandler(this)
        if (sharedPrefHandler.getSoundBackground()) {
            val intent = Intent(this, BackgroundSoundService::class.java)
            startService(intent)
        }

    }
}

@Composable
fun Home() {
    val interactionSource = remember { MutableInteractionSource() }
    val mContext = LocalContext.current
    val mActivity = (mContext as? Activity)
    val sharedPrefHandler = SharedPrefHandler(mContext)
    var showSheet by remember { mutableStateOf(false) }
    var showSetting by remember { mutableStateOf(false) }

    var voice by remember { mutableStateOf(sharedPrefHandler.getSoundVoice()) }
    var back by remember { mutableStateOf(sharedPrefHandler.getSoundBackground()) }
    var effect by remember { mutableStateOf(sharedPrefHandler.getSoundEffect()) }

    if (showSheet) {
        BottomSheet(mContext) {
            showSheet = false
        }
    }
    Box(Modifier.fillMaxSize()) {
        if (showSetting) {
            Dialog(
                onDismissRequest = {
                    showSetting = false
                },
                properties = DialogProperties(usePlatformDefaultWidth = false),
                content = {


                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center)
//                        shape = MaterialTheme.shapes.large
                    ) {
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(32.dp)
                                .background(primaryColor, shape = RoundedCornerShape(24.dp))
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                            ) {

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier
                                        .weight(1f)) {
                                        Checkbox(
                                            // below line we are setting
                                            // the state of checkbox.
                                            checked = effect,
                                            // below line is use to add padding
                                            // to our checkbox.
                                            modifier = Modifier.padding(16.dp),
                                            // below line is use to add on check
                                            // change to our checkbox.
                                            onCheckedChange = {
                                                effect = it
                                                sharedPrefHandler.setSoundEffect(effect)
                                            },
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = progressBarBackColor
                                            ),
                                        )
                                    }

                                    Box(modifier = Modifier
                                        .weight(2f), contentAlignment = Alignment.CenterEnd) {
                                        Text(
                                            text = "صدای دکمه ها",
                                            fontSize = 32.sp,
                                            fontFamily = FontFamily(Font(R.font.bkoodakbold)),
                                            color = Color.White,
                                            textAlign = TextAlign.Center
                                        )
                                    }


                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier
                                        .weight(1f)) {
                                        Checkbox(
                                            // below line we are setting
                                            // the state of checkbox.
                                            checked = back,
                                            // below line is use to add padding
                                            // to our checkbox.
                                            modifier = Modifier.padding(16.dp),
                                            // below line is use to add on check
                                            // change to our checkbox.
                                            onCheckedChange = {
                                                back = it
                                                sharedPrefHandler.setSoundBackground(back)
                                                val intent = Intent(
                                                    mContext,
                                                    BackgroundSoundService::class.java
                                                )
                                                if (it) {
                                                    mContext.startService(intent)
                                                } else {
                                                    mContext.stopService(intent)
                                                }
                                            },
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = progressBarBackColor
                                            ),
                                        )
                                    }
                                    Box(modifier = Modifier
                                        .weight(2f), contentAlignment = Alignment.CenterEnd) {
                                        Text(
                                            text = "صدای پس زمینه",
                                            fontSize = 32.sp,
                                            fontFamily = FontFamily(Font(R.font.bkoodakbold)),
                                            color = Color.White,
                                            textAlign = TextAlign.Center
                                        )
                                    }


                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier
                                        .weight(1f)) {
                                        Checkbox(
                                            // below line we are setting
                                            // the state of checkbox.
                                            checked = voice,
                                            // below line is use to add padding
                                            // to our checkbox.
                                            modifier = Modifier.padding(16.dp),
                                            // below line is use to add on check
                                            // change to our checkbox.
                                            onCheckedChange = {
                                                voice = it
                                                sharedPrefHandler.setSoundVoice(voice)

                                            },
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = progressBarBackColor
                                            ),
                                        )
                                    }
                                    Box(modifier = Modifier
                                        .weight(2f), contentAlignment = Alignment.CenterEnd) {
                                        Text(
                                            text = "صدای صحبت",
                                            fontSize = 32.sp,
                                            fontFamily = FontFamily(Font(R.font.bkoodakbold)),
                                            color = Color.White,
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                }

                            }
                        }
                    }


                },

                )
        }

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .paint(painterResource(id = R.drawable.splash), contentScale = ContentScale.Crop),

            ) {

            Column(modifier = Modifier.fillMaxWidth()) {
                Row {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.coin_back),
                            contentDescription = ""
                        )
                        Text(
                            text = sharedPrefHandler.getCoin().toString(),
                            fontFamily = FontFamily(Font(R.font.bkoodakbold)),
                            fontWeight = FontWeight.Bold,
                            color = Color.White, fontSize = 28.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .height(IntrinsicSize.Min)
                                .padding(top = 8.dp, start = 8.dp)
                        )
                    }


                    MyImageBtn(
                        modifier = Modifier.weight(1f),
                        drawable = painterResource(id = R.drawable.setting_back),
                        context = mContext,
                        click = {
                            showSetting = true

                        }
                    )
                }

            }

            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MyImageBtn(
                        drawable = painterResource(id = R.drawable.start_game_btn),
                        modifier = Modifier
                            .width(LocalConfiguration.current.screenWidthDp.dp / 2)
                            .height(LocalConfiguration.current.screenWidthDp.dp / 3),
                        context = mContext,
                        click = {

                            val intent = Intent(mContext, StartGameActivity::class.java)
                            intent
                                .putExtra(
                                    SharedPrefHandler.Constants.GAME_LEVEL_NAME,
                                    sharedPrefHandler.getLevel()
                                )
                            mContext.startActivity(intent)

                        }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.levels_btn),
                        contentDescription = "",
                        modifier = Modifier
                            .width(LocalConfiguration.current.screenWidthDp.dp / 2)
                            .height(LocalConfiguration.current.screenWidthDp.dp / 3)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                showSheet = true

                            }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.support_btn),
                        contentDescription = "",
                        modifier = Modifier
                            .width(LocalConfiguration.current.screenWidthDp.dp / 2)
                            .height(LocalConfiguration.current.screenWidthDp.dp / 3)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {

                                var url = "https://rahmanzaei.ir/"
                                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                                    url = "http://$url"
                                }

                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                mContext.startActivity(browserIntent)
                            }
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "حمایت از ما",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .background(
                                Color.Black.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(vertical = 12.dp, horizontal = 24.dp)


                    ) {

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.wrapContentSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.insta),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Amirbaqeri.Ui",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.wrapContentSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.insta),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "_rahmanzaei_",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                    }
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    CakeOrFakeTheme {
        Greeting("Android")
    }
}