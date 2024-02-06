package com.rhmn.cakeorfake.ui.composible

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rhmn.cakeorfake.R
import com.rhmn.cakeorfake.tools.SharedPrefHandler
import com.rhmn.cakeorfake.tools.SharedPrefHandler.Constants.Companion.GAME_LEVEL_NAME
import com.rhmn.cakeorfake.tools.SoundsEffect
import com.rhmn.cakeorfake.tools.Utils.Companion.getLevels
import com.rhmn.cakeorfake.ui.activity.game.StartGameActivity
import com.rhmn.cakeorfake.ui.activity.game.model.Level
import com.rhmn.cakeorfake.ui.activity.home.HomeActivity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(context: Context,onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        CountryList(context)
    }
}

@Composable
fun CountryList(context: Context) {

    var lvls :List<Level> = getLevels(context)
    val interactionSource = remember { MutableInteractionSource() }
    val sharedPrefHandler = SharedPrefHandler(context)

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        verticalItemSpacing = 24.dp,
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(horizontal = 48.dp, vertical = 32.dp)

    ) {
        items(lvls) { (lev, lock) ->
            val btnValue = if (lock) "🔒" else "$lev"
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .paint(
                        painter = painterResource(id = R.drawable.level_btn),
                        contentScale = ContentScale.Crop
                    )
                    .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {

                        SoundsEffect.playClick(context)

                        if(!lock){
                        val intent = Intent(context, StartGameActivity::class.java)
                        intent.putExtra(GAME_LEVEL_NAME,lev)
                        context.startActivity(intent)
                    }
                }
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "$btnValue", fontFamily = FontFamily(
                        Font(R.font.bkoodakbold)
                    ),
                    fontSize = 42.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}