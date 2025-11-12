package com.kal.beum.level.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.home.presentation.components.ToggleButton
import com.kal.beum.main.presentation.MainAction
import org.jetbrains.compose.resources.Font
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    isDevil: Boolean,
    viewModel: RankingViewModel,
    onAction: (MainAction) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDetailInfoIndex by remember { mutableStateOf(-1) }
    LaunchedEffect(Unit) {
        viewModel.getRankerList(isDevil)
    }
    onAction(MainAction.SurfaceColor(if (isDevil) BeumColors.DarkGray50 else BeumColors.White))
    Column(modifier = Modifier.background(if(isDevil) BeumColors.DarkGray50 else BeumColors.White)) {
        Row(
            modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "랭킹", style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 36.08.sp,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(700),
                    color = if (isDevil) BeumColors.White else BeumColors.baseGrayLightGray900,
                )
            )
            Spacer(Modifier.weight(1f))
            ToggleButton(isDevil, if (isDevil) Color.Black else BeumColors.baseCoolGrayLightGray100) {
                onAction(MainAction.ToggleDevil(it))
            }
        }

        LazyColumn {
            items(state.rankerUserList.size) { index ->
                val ranker = state.rankerUserList[index]
                RankDetailInfo(isDevil, ranker) {
                    showDetailInfoIndex = index
                }
            }
        }

        val selected = state.rankerUserList.getOrNull(showDetailInfoIndex)
        if (selected != null) {
            ModalBottomSheet(
                onDismissRequest = { showDetailInfoIndex = -1 },
                containerColor = if (isDevil) Color(0xCC000000) else Color(0xCCFFFFFF),
                dragHandle = null,
            ) {
                RankDetailInfo(isDevil, selected) {}
            }
        }
    }
}