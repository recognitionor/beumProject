package com.kal.beum.home.presentation

import FlowRowTest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kal.beum.home.presentation.components.AlarmButton
import com.kal.beum.home.presentation.components.ToggleButton
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    println("~~~HomeScreen ${state.homeCommentList}")
    Column(
        modifier = Modifier.wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.weight(1f))
            ToggleButton { isDevil ->
                println("isDevil : $isDevil")
            }
            Spacer(modifier = Modifier.weight(1f))

            AlarmButton()

        }




        Text(text = "~!!~!~!")

        FlowRowTest(viewModel)


    }
}