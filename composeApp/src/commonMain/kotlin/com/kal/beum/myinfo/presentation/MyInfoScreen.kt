package com.kal.beum.myinfo.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import beumproject.composeapp.generated.resources.*
import coil3.compose.AsyncImage
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.main.presentation.FullScreenType
import com.kal.beum.main.presentation.MainAction
import com.kal.beum.myinfo.domain.MyContent
import com.kal.beum.utils.formatWithComma
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyInfoScreen(devil: Boolean, viewModel: MyInfoViewModel, action: (MainAction) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var myInfoSelectedTabIndex by remember { mutableStateOf(0) }

    var selectModeBottomSheet by remember { mutableStateOf(false) }
    var reportContent by remember { mutableStateOf<MyContent?>(null) }
    var reportPage by remember { mutableStateOf(0) }
    var reportReasonIndex by remember { mutableStateOf(-1) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val scope = rememberCoroutineScope()
    action(MainAction.SurfaceColor(BeumColors.baseCoolGrayLightGray100))

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().background(BeumColors.baseCoolGrayLightGray100),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "마이페이지", style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = BeumDimen.baseLineheightTextLineheight300,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(BeumTypo.baseTextWeightTextWeight700),
                        color = BeumColors.Black,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))

            }
            Spacer(modifier = Modifier.fillMaxWidth().height(32.dp))

            Image(
                painter = painterResource(if (!devil) Res.drawable.img_angel_user else Res.drawable.img_devil_user),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = state.myInfo?.nickName ?: "", style = TextStyle(
                    fontSize = BeumTypo.TypoScaleText500,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(700),
                    color = BeumColors.baseGrayLightGray800,
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.padding(20.dp).border(
                    width = 1.dp,
                    color = BeumColors.baseCoolGrayLightGray200,
                    shape = RoundedCornerShape(size = BeumDimen.radius100)
                ).fillMaxWidth().height(80.dp).background(
                    color = BeumColors.baseCoolGrayLightGray75,
                    shape = RoundedCornerShape(size = BeumDimen.radius100)
                )
            ) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (!devil) "천사 활동 점수" else "악마 활동 점수", style = TextStyle(
                                    fontSize = BeumTypo.TypoScaleText100,
                                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                    fontWeight = FontWeight(500),
                                    color = BeumColors.baseGrayLightGray600,
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))

                            Image(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(Res.drawable.ic_info),
                                contentDescription = ""
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = formatWithComma(
                                    if (!devil) state.myInfo?.angelPoint ?: 0 else state.myInfo?.devilPoint ?: 0
                                ), // Use formatWithComma function to format the number with commas , style = TextStyle(
                                fontSize = BeumTypo.TypoScaleText600,
                                lineHeight = BeumTypo.lineHeightCaption1,
                                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                fontWeight = FontWeight(700),
                                color = BeumColors.baseGrayLightGray800,
                                textAlign = TextAlign.Right,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier.width(18.dp).height(18.dp).background(
                                    color = BeumColors.baseCoolGrayLightGray700,
                                    shape = RoundedCornerShape(size = 75.00002.dp)
                                ).padding(start = 3.dp, top = 3.dp, end = 3.dp, bottom = 3.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.heart),
                                    contentDescription = "image description",
                                    contentScale = ContentScale.None
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.weight(1f)) {
                MyInfoFeedTab(
                    devil,
                    list = if (myInfoSelectedTabIndex == 0) state.myContent else state.myReply,
                    selectedTabIndex = myInfoSelectedTabIndex,
                    writeClick = {
                        state.myInfo?.userId?.let { userId ->
                            action(MainAction.NewWriting {
                                viewModel.getMyContent(userId.toInt())
                            })
                        }
                    },
                    onItemClick = {
                        action(MainAction.PushFullScreen(FullScreenType.ContentDetailScreen(it.id)))
                    },
                    onTabSelected = {
                        myInfoSelectedTabIndex = it
                    })
            }
            if (selectModeBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        selectModeBottomSheet = false
                    }, // 닫힐 때 None으로 초기화
                    sheetState = sheetState,
                    containerColor = Color.White,
                    dragHandle = null,
                    modifier = Modifier.wrapContentHeight().fillMaxWidth()
                ) {
                    ModeToggleBottomSheet(
                        isDevilMode = devil, onToggleMode = {
                            action.invoke(MainAction.ToggleDevil(it))
                            selectModeBottomSheet = false
                        })
                }
            }
            if (reportContent != null) {
                when (reportPage) {
                    1 -> {
                        ModalBottomSheet(
                            onDismissRequest = {
                                reportPage = 0
                                reportReasonIndex = -1
                                reportContent = null
                            },
                            sheetState = sheetState,
                            containerColor = BeumColors.baseGrayLightGray75,
                            dragHandle = null,
                            modifier = Modifier.wrapContentHeight().fillMaxWidth()
                        ) {
                            ReportBottomSheetPage({
                                scope.launch {
                                    reportPage = 0
                                    reportReasonIndex = -1
                                    reportContent = null
                                    sheetState.hide()
                                }
                            }) {
                                reportPage = 2
                            }
                        }
                    }

                    2 -> {
                        ModalBottomSheet(
                            onDismissRequest = {
                                reportPage = 0
                                reportReasonIndex = -1
                                reportContent = null
                            }, // 닫힐 때 None으로 초기화
                            sheetState = sheetState,
                            dragHandle = null,
                            containerColor = BeumColors.baseGrayLightGray75,
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                .wrapContentHeight()
                        ) {
                            ReportDetailBottomSheet {
                                reportPage = 3
                                reportReasonIndex = it
                            }
                        }
                    }

                    3 -> {
                        action(
                            MainAction.PushFullScreen(
                                FullScreenType.ReportConfirmDialog(title = "", onContinueClick = {
                                    action(MainAction.PopFullScreen)
                                    reportContent?.let {
                                        viewModel.reportUser(it)
                                    }
                                }, onDismiss = {
                                    action(MainAction.PopFullScreen)
                                    reportPage = 0
                                    reportReasonIndex = -1
                                    reportContent = null
                                })
                            )
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier.padding(20.dp)
                .padding(bottom = BeumDimen.BottomNavigationHeight + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                .align(Alignment.BottomEnd),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier.background(
                    color = BeumColors.GrayGray900, shape = RoundedCornerShape(size = 88.dp)
                ).padding(
                    12.dp
                ).clickable {
                    selectModeBottomSheet = true
                }, verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(if (devil) Res.drawable.devil_abled else Res.drawable.angel_abled),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (devil) "악마" else "천사", style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText300,
                        lineHeight = 14.17.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(600),
                        color = BeumColors.baseAlphaWhiteDarkWhite,
                        textAlign = TextAlign.Right,
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(Res.drawable.ic_down_arrow), contentDescription = ""
                )
            }
        }
    }
}