package com.kal.beum.main.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
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
import com.kal.beum.Platform
import com.kal.beum.Route
import com.kal.beum.community.presentation.CommunityScreen
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.core.presentation.Toast
import com.kal.beum.home.presentation.HomeScreen
import com.kal.beum.level.presentaion.RankingScreen
import com.kal.beum.myinfo.presentation.MyInfoScreen
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen() {
    val viewModel = koinViewModel<MainViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    if (!state.isSplashDone) {
        SplashScreen()
    } else {
        Scaffold(topBar = {}, bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute, state.isDevil)
        }) { innerPadding ->

            // NavHost 내에서 시작 화면을 정의합니다.
            Box(modifier = Modifier.fillMaxSize()) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = if (state.isDevil) BeumColors.baseGrayLightGray800 else BeumColors.baseCoolGrayLightGray100
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.Home.toRoute(), // Home이 NavGraph의 시작점으로 정의됩니다.
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {

                        composable(Route.Home.toRoute()) {
                            HomeScreen(state.isDevil, viewModel::onAction)
                        }
                        composable(Route.Community.toRoute()) {
                            CommunityScreen(state.isDevil, viewModel::onAction)
                        }
                        composable(Route.Level("1").toRoute()) { backStackEntry ->
                            RankingScreen(state.isDevil, viewModel::onAction)
                        }
                        composable(Route.MyInfo("userId").toRoute()) { backStackEntry ->
                            MyInfoScreen(state.isDevil, viewModel::onAction)
                        }
                    }
                }
            }
        }
        state.showToast?.let {
            Toast(it) {
                viewModel.onAction(MainAction.ToastMessage())
            }
        }
    }

    println("state.isFullScreen ${state.isFullScreen}")
    // ✅ 여기에 전역 fullScreen 처리!
    println("state.fullScreen ${state.fullScreen}")

    Box(modifier = Modifier.fillMaxSize()) {

        if (state.isProgress) {
            ProgressDialog()
        } else {
            // FullScreen Overlay
            state.fullScreen.forEach { content ->
                if (content != null) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { /* 이벤트 소모만! */ }
                    ) {
                        content()
                    }
                }
            }
        }
    }

//    PermissionScreen()


}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?, devil: Boolean) {
    NavigationBar(

        modifier = Modifier.clip(
            RoundedCornerShape(
                topStart = 32.dp, topEnd = 32.dp
            )
        ), containerColor = if (devil) BeumColors.baseGrayLightGray800 else Color.White // 여기 추가
    ) {
        val selectedTintColor = if (devil) Color.White else BeumColors.baseGrayLightGray800
        val unSelectedTintColor = if (devil) BeumColors.transparentWhite else Color.Unspecified
        NavigationBarItem(selected = currentRoute == Route.Home.toRoute(), // 상태 업데이트 필요
            onClick = { navController.navigate(Route.Home.toRoute()) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            ),
            label = {
                Text(
                    "홈", style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText25,
                        lineHeight = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(500),
                        color = BeumColors.baseGrayLightGray500,
                        textAlign = TextAlign.Center,
                    )
                )
            },
            icon = {
                val isSelected = currentRoute == Route.Home.toRoute()
                Icon(
                    painter = if (isSelected) painterResource(Res.drawable.home_selected) else painterResource(
                        Res.drawable.home
                    ),
                    tint = if (isSelected) selectedTintColor else unSelectedTintColor,
                    contentDescription = "Home"
                )
            })
        NavigationBarItem(selected = currentRoute == Route.Community.toRoute(), // 상태 업데이트 필요
            onClick = { navController.navigate(Route.Community.toRoute()) }, label = {
                Text(
                    "커뮤니티", style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText25,
                        lineHeight = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(500),
                        color = BeumColors.baseGrayLightGray500,
                        textAlign = TextAlign.Center,
                    )
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            ), icon = {
                val isSelected = currentRoute == Route.Community.toRoute()
                Icon(
                    painter = if (isSelected) painterResource(Res.drawable.wing_selected) else painterResource(
                        Res.drawable.wing
                    ),
                    tint = if (isSelected) selectedTintColor else unSelectedTintColor,
                    contentDescription = "Community"
                )
            })
        NavigationBarItem(selected = currentRoute == Route.Level("1").toRoute(), // 상태 업데이트 필요
            onClick = { navController.navigate(Route.Level("1").toRoute()) }, label = {
                Text(
                    "레벨", style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText25,
                        lineHeight = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(500),
                        color = BeumColors.baseGrayLightGray500,
                        textAlign = TextAlign.Center,
                    )
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            ), icon = {
                val isSelected = currentRoute == Route.Level("1").toRoute()
                Icon(
                    painter = if (isSelected) painterResource(Res.drawable.level_selected) else painterResource(
                        Res.drawable.level
                    ),
                    tint = if (isSelected) selectedTintColor else unSelectedTintColor,
                    contentDescription = "Level"
                )
            })
        NavigationBarItem(selected = currentRoute == Route.MyInfo("userId").toRoute(), // 상태 업데이트 필요
            onClick = { navController.navigate(Route.MyInfo("userId").toRoute()) }, label = {
                Text(
                    "마이", style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText25,
                        lineHeight = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(500),
                        color = BeumColors.baseGrayLightGray500,
                        textAlign = TextAlign.Center,
                    )
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            ), icon = {
                val isSelected = currentRoute == Route.MyInfo("userId").toRoute()
                Icon(
                    painter = if (isSelected) painterResource(
                        Res.drawable.info_selected
                    ) else painterResource(Res.drawable.info),
                    tint = if (isSelected) selectedTintColor else unSelectedTintColor,
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