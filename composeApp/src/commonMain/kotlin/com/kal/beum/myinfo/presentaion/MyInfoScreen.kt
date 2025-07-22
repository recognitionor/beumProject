package com.kal.beum.myinfo.presentaion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.angel_abled
import beumproject.composeapp.generated.resources.devil_abled
import beumproject.composeapp.generated.resources.heart
import beumproject.composeapp.generated.resources.ic_angel_emoji
import beumproject.composeapp.generated.resources.ic_devil_emoji
import beumproject.composeapp.generated.resources.ic_down_arrow
import beumproject.composeapp.generated.resources.ic_info
import beumproject.composeapp.generated.resources.ic_setting
import beumproject.composeapp.generated.resources.sf_pro
import coil3.compose.AsyncImage
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.main.presentation.MainAction
import com.kal.beum.myinfo.domain.MyContent
import com.kal.beum.utils.formatWithComma
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyInfoScreen(devil: Boolean, action: (MainAction) -> Unit) {
    val viewModel = koinViewModel<MyInfoViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    var myInfoSelectedTabIndex by remember { mutableStateOf(0) }

    var selectModeBottomSheet by remember { mutableStateOf(false) }
    var reportContent by remember { mutableStateOf<MyContent?>(null) }
    var reportPage by remember { mutableStateOf(0) }
    var reportReasonIndex by remember { mutableStateOf(-1) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(BeumColors.White),
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
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_setting),
                    colorFilter = ColorFilter.tint(Color.Black),
                    contentDescription = ""
                )

            }
            Spacer(modifier = Modifier.fillMaxWidth().height(32.dp))

            if (state.myInfo?.profileImage?.isNotEmpty() == true) {
                AsyncImage(model = state.myInfo?.profileImage!!, contentDescription = "")
            } else {
                Image(
                    modifier = Modifier.size(64.dp).border(
                        width = 1.dp,
                        color = BeumColors.baseAlphaWhiteLightWhite500A,
                        shape = RoundedCornerShape(size = 32.dp)
                    ).background(
                        color = if (devil) BeumColors.DevilPrimary else BeumColors.angelSkyblue,
                        shape = RoundedCornerShape(size = 32.dp)
                    ),
                    painter = painterResource(if (devil) Res.drawable.ic_devil_emoji else Res.drawable.ic_angel_emoji),
                    contentDescription = "profile image",
                )
            }

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
            Box(
                modifier = Modifier.padding(20.dp).border(
                    width = 1.dp,
                    color = BeumColors.baseCoolGrayLightGray200,
                    shape = RoundedCornerShape(size = BeumDimen.radius100)
                ).fillMaxWidth().height(80.dp).background(
                    color = BeumColors.baseCoolGrayLightGray75,
                    shape = RoundedCornerShape(size = BeumDimen.radius100)
                ), contentAlignment = Alignment.Center
            ) {
                Column {
                    Row(
                        modifier = Modifier.width(110.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (devil) "악마 활동 점수" else "천사 활동 점수", style = TextStyle(
                                fontSize = BeumTypo.TypoScaleText100,
                                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                fontWeight = FontWeight(500),
                                color = BeumColors.baseGrayLightGray600,
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Image(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(Res.drawable.ic_info),
                            contentDescription = ""
                        )
                    }
                    Row(
                        modifier = Modifier.width(110.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = formatWithComma(
                                state.myInfo?.angelPoint ?: 0
                            ), // Use formatWithComma function to format the number with commas , style = TextStyle(
                            fontSize = BeumTypo.TypoScaleText600,
                            lineHeight = BeumTypo.lineHeightCaption1,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(700),
                            color = BeumColors.baseGrayLightGray800,
                            textAlign = TextAlign.Right,
                        )
                        Spacer(modifier = Modifier.weight(1f))
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

            Spacer(modifier = Modifier.height(20.dp))


            MyInfoFeedTab(
                devil,
                list = if (myInfoSelectedTabIndex == 0) state.myContent else state.myReply,
                selectedTabIndex = myInfoSelectedTabIndex,
                onItemClick = {
                    reportPage = 1
                    reportContent = it
                },
                onTabSelected = {
                    myInfoSelectedTabIndex = it
                })
            if (selectModeBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        selectModeBottomSheet = false
                    }, // 닫힐 때 None으로 초기화
                    sheetState = sheetState,
                    containerColor = Color.White,
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
                            modifier = Modifier.wrapContentHeight().fillMaxWidth()
                        ) {
                            ReportBottomSheetPage {
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
                            containerColor = BeumColors.baseGrayLightGray75,
                            modifier = Modifier.fillMaxWidth().wrapContentHeight().wrapContentHeight()
                        ) {
                            ReportDetailBottomSheet {
                                reportPage = 3
                                reportReasonIndex = it
                            }
                        }
                    }
                    3 -> {
                        action(MainAction.SetFullScreen {
                            ReportConfirmDialog(onContinueClick = {
                                action(MainAction.SetFullScreen(null))
                                reportContent?.let {
                                    viewModel.reportUser(it)
                                }
                            }, onDismiss = {
                                action(MainAction.SetFullScreen(null))
                                reportPage = 0
                                reportReasonIndex = -1
                                reportContent = null
                            })
                        })
                    }
                }
            }
        }

        Box(
            modifier = Modifier.padding(
                20.dp
            ).align(Alignment.BottomEnd),
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