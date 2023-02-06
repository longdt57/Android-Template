package lee.module.design.base.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import lee.module.design.R

@Composable
fun SliderBubble(xOffset: Dp, label: String) {
    Box(
        modifier = Modifier
            .offset(x = xOffset),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.im_slider_bubble),
            contentDescription = ""
        )
        Text(
            text = label, style = MaterialTheme.typography.caption, color = Color.White,
            fontWeight = FontWeight.Medium, textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SliderBubbleView(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    /*@IntRange(from = 0)*/
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SliderColors = SliderDefaults.colors()
) {
    val numPoint = (valueRange.endInclusive - valueRange.start).toInt() + 1
    var widthIs by remember { mutableStateOf(0f) }
    Column(
        modifier = modifier.onSizeChanged { widthIs = it.width.toFloat() }
    ) {
        val xOffset = LocalDensity.current.run { (widthIs * value / numPoint).toInt().toDp() }
        val label = value.toInt().toString()
        SliderBubble(xOffset, label)

        Slider(
            value = value,
            valueRange = valueRange,
            steps = numPoint - 2,
            onValueChange = { onValueChange(it) },
            colors = colors,
            modifier = Modifier.padding(horizontal = 6.dp),
            enabled = enabled,
            onValueChangeFinished = onValueChangeFinished,
            interactionSource = interactionSource,
        )
    }
}
