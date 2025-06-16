package com.kal.beum.write.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.write.domain.WritingCategory
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategorySelectBottomSheet(
    writeCategoryMap: Map<String, List<WritingCategory>>,
    onDismissRequest: (writeCategoryItem: WritingCategory) -> Unit
) {
    Column(
        modifier = Modifier.wrapContentHeight().padding(24.dp).verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "카테고리를 선택해주세요.", style = TextStyle(
                fontSize = BeumTypo.TypoScaleText500,
                lineHeight = 32.sp,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(700),
                color = BeumColors.baseGrayLightGray900,
            )
        )

        Spacer(Modifier.height(24.dp))
        writeCategoryMap.map {
            Text(
                text = it.key, style = TextStyle(
                    fontSize = BeumTypo.TypoScaleText300,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(700),
                    color = BeumColors.surfaceGray100_2
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            Column {
                FlowRow(
                    modifier = Modifier.wrapContentWidth(),
                ) {
                    it.value.forEach { item ->
                        Box(
                            modifier = Modifier.padding(
                                end = 10.dp, bottom = 10.dp
                            ).clip(RoundedCornerShape(50)).border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(50)
                            ).clickable {
                                onDismissRequest(item)
                            }.padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {

                            Text(
                                text = item.category
                            )
                        }
                    }
                }
            }
        }
    }
}