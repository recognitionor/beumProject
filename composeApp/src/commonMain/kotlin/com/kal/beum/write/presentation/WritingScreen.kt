package com.kal.beum.write.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.ic_close
import beumproject.composeapp.generated.resources.icon_arrow_right
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.ToastInfo
import com.kal.beum.main.presentation.MainAction
import com.kal.beum.write.domain.WritingData
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritingScreen(
    writingData: WritingData? = null,
    onAction: (MainAction) -> Unit,
    callBack: (() -> Unit)?
) {
    val viewModel = koinViewModel<WritingViewModel>()
    // 진입할 때 상태 초기화
    LaunchedEffect(Unit) {
        if (writingData == null) {
            viewModel.onAction(WritingAction.Reset)
        } else {
            viewModel.onAction(WritingAction.InitTempWriting(writingData))
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false // <- 이게 포인트!
    )
    var currentInfoBottomSheetType by remember {
        mutableStateOf<InfoBottomSheetType>(
            InfoBottomSheetType.None
        )
    }

    var showCategorySheet by remember { mutableStateOf(writingData?.category == null) }
    var showPointSettingSheet by remember { mutableStateOf(false) }

    if (state.isClose) {
        if (state.closeMessage?.isNotEmpty() == true) {
            onAction(MainAction.ToastMessage(ToastInfo("게시글이 등록되었습니다.")))
        }
        callBack?.invoke()
        onAction(MainAction.PopFullScreen)
        viewModel.onAction(WritingAction.Reset)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.White)
                .padding(top = 40.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(
                    rememberScrollState()
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.height(48.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_close),
                    contentDescription = "",
                    Modifier.clickable {
                        viewModel.onAction(WritingAction.SaveTempWriting)
                        viewModel.onAction(WritingAction.Close)
                    }.width(24.dp).height(24.dp).padding(1.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "글쓰기", style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(700),
                        color = BeumColors.Black,
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.clip(shape = RoundedCornerShape(size = 12.dp)).border(
                    width = 1.dp,
                    color = BeumColors.baseGrayLightGray200,
                    shape = RoundedCornerShape(size = 12.dp)
                ).height(43.dp).background(
                    color = BeumColors.baseGrayLightGray75, shape = RoundedCornerShape(size = 12.dp)
                ).clickable {
                    currentInfoBottomSheetType = InfoBottomSheetType.GuideInfo()
                }.padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "가이드라인에 맞지 않는 글은 통보 없이 숨겨질 수 있습니다.", style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 18.84.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(500),
                        color = BeumColors.baseGrayLightGray600,
                    )
                )

                Spacer(modifier = Modifier.weight(1f)) // 이게 텍스트와 아이콘을 최대한 벌려줌

                Image(
                    modifier = Modifier.align(alignment = Alignment.CenterVertically),
                    painter = painterResource(Res.drawable.icon_arrow_right),
                    colorFilter = ColorFilter.tint(BeumColors.baseGrayLightGray400),
                    contentDescription = ""
                )

            }
            Spacer(modifier = Modifier.height(12.dp))

            WriteEditText(96.dp, state.title, "제목", "제목을 입력해주세요.") {
                viewModel.onAction(WritingAction.OnTitleChanged(it))
            }

            Spacer(modifier = Modifier.height(12.dp))

            WriteEditText(
                300.dp,
                state.content,
                "내 고민",
                "커뮤니티 가이드라인에 맞지 않는 콘텐츠는 통보 없이 숨겨질 수 있습니다."
            ) {
                viewModel.onAction(WritingAction.OnContentChanged(it))
            }
            Spacer(modifier = Modifier.height(12.dp))
            WriteEditText(
                96.dp, state.tags, "태그", "# 입력후 키워드를 입력해주세요.",
                infoClick = {
                    currentInfoBottomSheetType = InfoBottomSheetType.TagInfo()
                },
            ) {
                println("viewModel.onAction : $it")
                viewModel.onAction(WritingAction.OnTagChanged(it))
            }
            Spacer(modifier = Modifier.height(12.dp))
            WriteEditText(
                115.dp, state.isDevil.toString(), "커뮤니티", "",
                infoClick = {
                    currentInfoBottomSheetType = InfoBottomSheetType.CommunityInfo()
                },
            ) {
                viewModel.onAction(WritingAction.OnCommunityChanged(it.toBoolean()))
            }
            Spacer(modifier = Modifier.height(12.dp))
            WriteEditText(
                115.dp, state.selectedCategory?.category ?: "", "카테고리", "카테고리를 선택해주세요", click = {
                    showCategorySheet = true
                }) {
                viewModel.onAction(WritingAction.OnTitleChanged(it))
            }
            Spacer(modifier = Modifier.height(12.dp))
            WriteEditText(
                115.dp, state.rewardPoint.toString(), "포인트", "포인트 걸기",
                click = {
                    showPointSettingSheet = true
                },
                infoClick = {
                    currentInfoBottomSheetType = InfoBottomSheetType.PointInfo()
                },
            ) {
                var point = 0
                try {
                    point = it.toInt()
                } catch (_: Exception) {
                }
                viewModel.onAction(WritingAction.OnPointChanged(point))
            }
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    viewModel.onAction(WritingAction.Submit)
                },
                enabled = checkValidContent(state),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BeumColors.angelSkyblue, contentColor = Color.White
                )
            ) {
                if (state.submitProgress) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "등록", fontSize = 18.sp, fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))


            if (currentInfoBottomSheetType != InfoBottomSheetType.None) {
                ModalBottomSheet(
                    onDismissRequest = {
                        currentInfoBottomSheetType = InfoBottomSheetType.None
                    }, // 닫힐 때 None으로 초기화
                    sheetState = sheetState,
                    dragHandle = null,
                    containerColor = Color.White,
                    modifier = Modifier.wrapContentHeight().fillMaxWidth()
                ) {
                    // currentInfoBottomSheetType에 따라 다른 InfoBottomSheetUI 컴포넌트 호출
                    // 예를 들어, 하나의 com.kal.beum.write.presentation.InfoBottomSheet 컴포넌트가 InfoBottomSheetType을 파라미터로 받도록 설계
                    InfoBottomSheet(type = currentInfoBottomSheetType, onConfirm = {
                        currentInfoBottomSheetType = InfoBottomSheetType.None
                    })
                }
            }
            if (showPointSettingSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showPointSettingSheet = false },
                    sheetState = sheetState,
                    dragHandle = null,
                    modifier = Modifier.wrapContentHeight().fillMaxWidth()
                ) {
                    // 아래에 들어가는 UI가 이미지랑 비슷하게 작성
                    PointSettingBottomSheet(state.rewardPoint, 1000) {
                        viewModel.onAction(WritingAction.OnPointChanged(it))
                        showPointSettingSheet = false
                    }
                }
            }

            if (showCategorySheet) {
                ModalBottomSheet(
                    onDismissRequest = { showCategorySheet = false },
                    sheetState = sheetState,
                    dragHandle = null,
                    modifier = Modifier.wrapContentHeight().fillMaxWidth()
                ) {
                    // 아래에 들어가는 UI가 이미지랑 비슷하게 작성

                    CategorySelectBottomSheet(state.writeCategoryMap, state.selectedCategory) {
                        viewModel.onAction(WritingAction.OnCategoryChanged(it))
                        showCategorySheet = false

                    }
                }
            }
        }
    }

}

fun checkValidContent(state: WritingState): Boolean {
    val result =
        state.content.isNotEmpty() && state.title.isNotEmpty() && state.selectedCategory != null && state.tags.isNotEmpty()
    println("state.content : ${state.content}")
    println("state.title : ${state.title}")
    println("state.selectedCategory : ${state.selectedCategory}")
    println("state.tags : ${state.tags}")
    return result
}
