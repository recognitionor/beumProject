package com.kal.beum.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kal.beum.Route
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.home.presentation.HomeScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen() {
    val viewModel = koinViewModel<MainViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    println("MainScreen state.isOnboardingDone : " + state.isOnboardingDone)
    println("MainScreen state.isDevil : " + state.isDevil)
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    if (!state.isSplashDone) {
        SplashScreen()
    } else {
        Scaffold(topBar = {}, bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute)
        }) { innerPadding ->

            // NavHost 내에서 시작 화면을 정의합니다.

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
                        HomeScreen(state.isDevil) { isDevil ->
                            viewModel.devilToggle(isDevil)
                        }
                    }
                    composable(Route.Community.toRoute()) {
                        CommunityScreen(navController)
                    }
                    composable(Route.Level("1").toRoute()) { backStackEntry ->
                        val levelId = backStackEntry.arguments?.getString("id")
                        LevelScreen(levelId)
                    }
                    composable(Route.MyInfo("userId").toRoute()) { backStackEntry ->
                        val myInfoId = backStackEntry.arguments?.getString("id")
                        MyInfoScreen(myInfoId)
                    }
                }
            }
        }
    }


//    PermissionScreen()


}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    NavigationBar(
        modifier = Modifier.clip(
            RoundedCornerShape(
                topStart = 32.dp, topEnd = 32.dp
            )
        )
    ) {
        NavigationBarItem(selected = currentRoute == Route.Home.toRoute(), // 상태 업데이트 필요
            onClick = { navController.navigate(Route.Home.toRoute()) },
            label = { Text("Home") },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") })
        NavigationBarItem(selected = currentRoute == Route.Community.toRoute(), // 상태 업데이트 필요
            onClick = { navController.navigate(Route.Community.toRoute()) },
            label = { Text("Community") },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Community") })
        NavigationBarItem(selected = currentRoute == Route.Level("1").toRoute(), // 상태 업데이트 필요
            onClick = { navController.navigate(Route.Level("1").toRoute()) },
            label = { Text("Level") },
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Level") })
        NavigationBarItem(selected = currentRoute == Route.MyInfo("userId").toRoute(), // 상태 업데이트 필요
            onClick = { navController.navigate(Route.MyInfo("userId").toRoute()) },
            label = { Text("My Info") },
            icon = { Icon(Icons.Filled.Person, contentDescription = "My Info") })
    }
}


@Composable
fun CommunityScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Community Screen")
    }
}

@Composable
fun LevelScreen(levelId: String?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Level Screen: $levelId")
    }
}

@Composable
fun MyInfoScreen(myInfoId: String?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("My Info Screen: $myInfoId")
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