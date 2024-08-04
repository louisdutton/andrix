package sh.ld2.example.ui.styleguide

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ProductTitleText(copy: String, color: Color, modifier: Modifier) {
  Text(
      text = copy,
      modifier = modifier,
      color = color,
      style = ProductTitleTextStyle,
  )
}

val ProductTitleTextStyle =
    TextStyle(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    )
