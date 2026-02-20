package com.kal.beum.main.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.home
import beumproject.composeapp.generated.resources.home_selected
import beumproject.composeapp.generated.resources.info
import beumproject.composeapp.generated.resources.info_selected
import beumproject.composeapp.generated.resources.level
import beumproject.composeapp.generated.resources.level_selected
import beumproject.composeapp.generated.resources.sf_pro
import beumproject.composeapp.generated.resources.wing
import beumproject.composeapp.generated.resources.wing_selected
import com.kal.beum.Route
import com.kal.beum.community.presentation.CommunityScreen
import com.kal.beum.community.presentation.CommunityViewModel
import com.kal.beum.community.presentation.DraftDialog
import com.kal.beum.content.presentation.ContentDetailScreen
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.core.presentation.Toast
import com.kal.beum.core.presentation.ToastInfo
import com.kal.beum.home.presentation.HomeScreen
import com.kal.beum.home.presentation.HomeViewModel
import com.kal.beum.level.presentaion.RankingScreen
import com.kal.beum.level.presentaion.RankingViewModel
import com.kal.beum.login.presentation.SignupDialog
import com.kal.beum.myinfo.presentation.LogOutDialog
import com.kal.beum.myinfo.presentation.MyInfoDetailScreen
import com.kal.beum.myinfo.presentation.MyInfoScreen
import com.kal.beum.myinfo.presentation.MyInfoViewModel
import com.kal.beum.myinfo.presentation.PrivacyPolicyScreen
import com.kal.beum.myinfo.presentation.ReportConfirmDialog
import com.kal.beum.myinfo.presentation.ServicePolicyInfoScreen
import com.kal.beum.myinfo.presentation.SettingsScreen
import com.kal.beum.myinfo.presentation.TermScreen
import com.kal.beum.notice.presentaion.NoticeScreen
import com.kal.beum.write.presentation.WritingScreen
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(), viewMode: MainViewModel
) {
    val viewModel = koinViewModel<MainViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.isSplashDone) {
        if (state.isSplashDone) {
            // 스플래시(로그인)가 끝나고 메인 화면이 뜰 때 홈으로 이동 & 백스택 초기화
            navController.navigate(Route.Home.toRoute())
        }
    }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    if (!state.isSplashDone) {
        SplashScreen(viewModel)
    } else {
        Scaffold(
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
            topBar = {}, bottomBar = {
                BottomNavigationBar(navController = navController, currentRoute, state.isDevil)
            }) { innerPadding ->

            // NavHost 내에서 시작 화면을 정의합니다.
            Box(modifier = Modifier.fillMaxSize()) {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = state.surfaceColor
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.Home.toRoute(), // Home이 NavGraph의 시작점으로 정의됩니다.
                        modifier = Modifier.fillMaxSize()
                            .padding(top = innerPadding.calculateTopPadding()),
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = { ExitTransition.None }
                    ) {

                        composable(Route.Home.toRoute()) {
                            val homeViewModel = koinViewModel<HomeViewModel>()
                            HomeScreen(state.isDevil, homeViewModel, viewModel::onAction)
                        }
                        composable(Route.Community.toRoute()) {
                            val communityViewModel = koinViewModel<CommunityViewModel>()
                            CommunityScreen(state.isDevil, communityViewModel, viewModel::onAction)
                        }
                        composable(Route.Level("1").toRoute()) { backStackEntry ->
                            val rankingViewModel = koinViewModel<RankingViewModel>()
                            RankingScreen(state.isDevil, rankingViewModel, viewModel::onAction)
                        }
                        composable(Route.MyInfo("userId").toRoute()) { backStackEntry ->
                            val myInfoViewModel = koinViewModel<MyInfoViewModel>()
                            MyInfoScreen(state.isDevil, myInfoViewModel, viewModel::onAction)
                        }
                    }
                }
            }
        }
        if (state.isDraftDialog) {
            if (state.writingTemp != null) {
                viewModel.onAction(
                    MainAction.PushFullScreen(
                        FullScreenType.DraftDialog(onNewClick = {
                            viewModel.onAction(MainAction.PopFullScreen)
                            viewModel.onAction(
                                MainAction.PushFullScreen(
                                    FullScreenType.WritingScreen(
                                        callBack = state.onWritingComplete
                                    )
                                )
                            )
                        }, onContinueClick = {
                            viewModel.onAction(MainAction.PopFullScreen)
                            viewModel.onAction(
                                MainAction.PushFullScreen(
                                    FullScreenType.WritingScreen(
                                        state.writingTemp, state.onWritingComplete
                                    )
                                )
                            )
                        }, onDismiss = {
                            viewModel.onAction(MainAction.PopFullScreen)
                        })
                    )
                )
            } else {
                viewModel.onAction(MainAction.PushFullScreen(FullScreenType.WritingScreen(callBack = state.onWritingComplete)))
            }
            viewModel.onAction(MainAction.OnDraftDialog)
        }
    }

    // ✅ 여기에 전역 fullScreen 처리!

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isProgress) {
            ProgressDialog()
        } else {
            state.fullScreenStack.forEach { content ->
                Box(
                    Modifier.fillMaxSize()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            focusManager.clearFocus()
                        }) {

                    when (content) {
                        is FullScreenType.MyInfoDetailScreen -> {
                            println("FullScreenType.MyInfoDetailScreen")
                            MyInfoDetailScreen(content.info, viewModel::onAction)
                        }

                        is FullScreenType.SettingsScreen -> {
                            println("FullScreenType.SettingsScreen")
                            SettingsScreen(content.info, viewModel::onAction)
                        }

                        is FullScreenType.ContentDetailScreen -> {
                            ContentDetailScreen(content.id, viewModel::onAction, {
                                viewModel.onAction(MainAction.PopFullScreen)
                            }, content.onDelete)
                        }

                        is FullScreenType.DraftDialog -> {
                            DraftDialog(
                                content.onNewClick, content.onContinueClick, content.onDismiss
                            )
                        }

                        is FullScreenType.WritingScreen -> {
                            WritingScreen(
                                content.tempWriting, viewModel::onAction, content.callBack
                            )
                        }

                        is FullScreenType.NoticeScreen -> {
                            println("NoticeScreen")
                            NoticeScreen(viewModel::onAction)
                        }

                        is FullScreenType.LogOutDialog -> {
                            LogOutDialog(content.onDismiss, content.logoutClick)
                        }

                        is FullScreenType.PrivacyPolicyScreen -> {
                            PrivacyPolicyScreen(viewModel::onAction)
                        }

                        is FullScreenType.ReportConfirmDialog -> {
                            ReportConfirmDialog(
                                content.title, content.onDismiss, content.onContinueClick
                            )
                        }

                        is FullScreenType.ServicePolicyInfoScreen -> {
                            ServicePolicyInfoScreen(viewModel::onAction)
                        }


                        is FullScreenType.TermScreen -> {
                            TermScreen(viewModel::onAction)
                        }

                        is FullScreenType.SignUpDialog -> {
                            SignupDialog(content.onDismiss) {
                                viewModel.onAction(MainAction.PopFullScreen)
                                content.signUpClick()
                            }
                        }

                        is FullScreenType.ProgressDialog -> {
                            ProgressDialog()
                        }
                    }
                }
            }

            // FullScreen Overlay
//            state.fullScreen.forEach { content ->
//                if (content != null) {
//                    Box(
//                        Modifier.fillMaxSize().clickable(
//                        indication = null,
//                        interactionSource = remember { MutableInteractionSource() }) { /* 이벤트 소모만! */ }) {
//                        content()
//                    }
//                }
//            }
        }
        state.showToast?.let {
            Toast(it) {
                viewModel.onAction(MainAction.ToastMessage())
            }
        }
    }

//    PermissionScreen()


}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?, devil: Boolean) {
    val shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    // 💡 시스템 내비게이션 바(제스처/버튼) 높이를 동적으로 가져옵니다.
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    // 기본 높이 80.dp에서 7.dp를 줄인 높이
    val reducedHeight = 80.dp - 7.dp

    // 전체 높이 = (줄인 높이) + (시스템 내비게이션 영역 높이)
    NavigationBar(
        modifier = Modifier.height(reducedHeight + bottomPadding).clip(shape).drawWithContent {
            val topStartRadius = 32.dp.toPx()
            val topEndRadius = 32.dp.toPx()
            val borderWidth = 2.dp.toPx()
            val borderColor = BeumColors.BlackAlpha10

            drawContent() // NavigationBar의 내용 먼저 그림

            val width = size.width

            // 1. 그라디언트(Brush) 정의
            // 보더의 양 끝(width * 0.1 지점)에서 투명하게 시작/끝나고, 중앙에서 불투명한 파란색이 되도록 설정
            val gradientBrush = Brush.linearGradient(
                colors = listOf(
                    borderColor.copy(alpha = 0.1f),    // 0% 지점 (왼쪽 끝, 투명)
                    borderColor,                     // 80% 지점 (완전히 불투명)
                    borderColor.copy(alpha = 0.1f)     // 100% 지점 (오른쪽 끝, 투명)
                ), start = Offset(0f, 0f), end = Offset(width, 0f), tileMode = TileMode.Clamp
            )

            // 2. Path 정의 (이전 코드와 동일, 전체 상단 라인을 정의)
            val path = Path().apply {
                // 시작점을 (0, 0)으로 가정하고 Top-Left Radius의 끝점에서 시작하도록 조정
                moveTo(0f, topStartRadius)

                // Arc for top-left corner
                arcTo(
                    rect = Rect(0f, 0f, topStartRadius * 2, topStartRadius * 2),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                // Line across the top
                lineTo(width - topEndRadius, 0f)

                // Arc for top-right corner
                arcTo(
                    rect = Rect(width - topEndRadius * 2, 0f, width, topEndRadius * 2),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            }

            // 3. Path를 Brush로 그리기
            drawPath(
                path = path, brush = gradientBrush, // Color 대신 Brush 사용
                style = Stroke(width = borderWidth, cap = StrokeCap.Round)
            )
        }, containerColor = if (devil) BeumColors.baseGrayLightGray800 else Color.White // 여기 추가
    ) {
        val selectedTintColor = if (devil) Color.White else BeumColors.baseGrayLightGray800
        val unSelectedTintColor = if (devil) BeumColors.transparentWhite else Color.Unspecified
        val isHomeSelected = currentRoute == Route.Home.toRoute()
        NavigationBarItem(
            selected = isHomeSelected, // 상태 업데이트 필요
            onClick = {
                navController.navigate(Route.Home.toRoute()) {
                    launchSingleTop = true
                    restoreState = true
                }
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            ), label = {
                Text(
                    modifier = Modifier.offset(y = (-2).dp),
                    text = "홈", style = TextStyle(
                        fontSize = if (isHomeSelected) 11.sp else 10.sp,
                        lineHeight = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = if (isHomeSelected) FontWeight.Bold else FontWeight(500),
                        color = if (isHomeSelected) if (devil) BeumColors.White else BeumColors.baseGrayLightGray800 else BeumColors.baseGrayLightGray500,
                        textAlign = TextAlign.Center,
                    )
                )
            }, icon = {
                Icon(
                    painter = if (isHomeSelected) painterResource(Res.drawable.home_selected) else painterResource(
                        Res.drawable.home
                    ),
                    tint = if (isHomeSelected) selectedTintColor else unSelectedTintColor,
                    contentDescription = "Home"
                )
            })
        val isCommunitySelected = currentRoute == Route.Community.toRoute()
        NavigationBarItem(
            selected = isCommunitySelected, // 상태 업데이트 필요
            onClick = {
                navController.navigate(Route.Community.toRoute()) {
                    launchSingleTop = true
                    restoreState = true
                }
            }, label = {
                Text(
                    modifier = Modifier.offset(y = (-2).dp),
                    text = "커뮤니티", style = TextStyle(
                        fontSize = if (isCommunitySelected) 11.sp else 10.sp,
                        lineHeight = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = if (isCommunitySelected) FontWeight.Bold else FontWeight(500),
                        color = if (isCommunitySelected) if (devil) BeumColors.White else BeumColors.baseGrayLightGray800 else BeumColors.baseGrayLightGray500,
                        textAlign = TextAlign.Center,
                    )
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            ), icon = {
                Icon(
                    painter = if (isCommunitySelected) painterResource(Res.drawable.wing_selected) else painterResource(
                        Res.drawable.wing
                    ),
                    tint = if (isCommunitySelected) selectedTintColor else unSelectedTintColor,
                    contentDescription = "Community"
                )
            })
        val isRankingSelected = currentRoute == Route.Level("1").toRoute()
        NavigationBarItem(
            selected = isRankingSelected, // 상태 업데이트 필요
            onClick = {
                navController.navigate(Route.Level("1").toRoute()) {
                    launchSingleTop = true
                    restoreState = true
                }
            }, label = {
                Text(
                    modifier = Modifier.offset(y = (-2).dp),
                    text = "랭킹", style = TextStyle(
                        fontSize = if (isRankingSelected) 11.sp else 10.sp,
                        lineHeight = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = if (isRankingSelected) FontWeight.Bold else FontWeight(500),
                        color = if (isRankingSelected) if (devil) BeumColors.White else BeumColors.baseGrayLightGray800 else BeumColors.baseGrayLightGray500,
                        textAlign = TextAlign.Center,
                    )
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            ), icon = {
                Icon(
                    painter = if (isRankingSelected) painterResource(Res.drawable.level_selected) else painterResource(
                        Res.drawable.level
                    ),
                    tint = if (isRankingSelected) selectedTintColor else unSelectedTintColor,
                    contentDescription = "Level"
                )
            })
        val isMyInfoSelected = currentRoute == Route.MyInfo("userId").toRoute()
        NavigationBarItem(
            selected = isMyInfoSelected, // 상태 업데이트 필요
            onClick = {
                navController.navigate(Route.MyInfo("userId").toRoute()) {
                    launchSingleTop = true
                    restoreState = true
                }
            }, label = {
                Text(
                    modifier = Modifier.offset(y = (-2).dp),
                    text = "마이", style = TextStyle(
                        fontSize = if (isMyInfoSelected) 11.sp else 10.sp,
                        lineHeight = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = if (isMyInfoSelected) FontWeight.Bold else FontWeight(500),
                        color = if (isMyInfoSelected) if (devil) BeumColors.White else BeumColors.baseGrayLightGray800 else BeumColors.baseGrayLightGray500,
                        textAlign = TextAlign.Center,
                    )
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            ), icon = {
                Icon(
                    painter = if (isMyInfoSelected) painterResource(
                        Res.drawable.info_selected
                    ) else painterResource(Res.drawable.info),
                    tint = if (isMyInfoSelected) selectedTintColor else unSelectedTintColor,
                    contentDescription = "My Info"
                )
            })
    }
}

// Route를 String으로 변환하는 확장 함수
fun Route.toRoute(): String {
    return when (this) {
        is Route.Home -> "home"
        is Route.Community -> "community"
        is Route.Level -> "level/${id}"
        is Route.MyInfo -> "myinfo/${id}"
    }
}