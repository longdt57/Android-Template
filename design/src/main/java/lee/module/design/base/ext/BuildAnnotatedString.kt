package lee.module.design.base.ext

import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

fun underlineAnnotatedString(content: String, presses: List<Pair<String, String>>): AnnotatedString {
    return buildAnnotatedString {
        append(content)
        presses.forEach { pairPress ->
            val press = pairPress.first
            val url = pairPress.second

            val start = content.indexOf(press)
            val end = start + press.length
            addStyle(style = SpanStyle(textDecoration = TextDecoration.Underline), start = start, end = end)
            addStringAnnotation(
                tag = press,
                annotation = url,
                start = start,
                end = end
            )
        }
    }
}

fun handleUnderlineClick(text: AnnotatedString, listUnderLine: List<Pair<String, String>>, offset: Int, uriHandler: UriHandler) {
    listUnderLine.forEach { annotatedStringData ->
        text.getStringAnnotations(tag = annotatedStringData.first, start = offset, end = offset).firstOrNull()?.let {
            uriHandler.openUri(annotatedStringData.second)
        }
    }
}

fun applyFontWeightAnnotatedString(content: String, fontWeight: FontWeight, vararg presses: String): AnnotatedString {
    return buildAnnotatedString {
        append(content)
        presses.forEach { press ->
            val start = content.indexOf(press)
            addStyle(style = SpanStyle(fontWeight = fontWeight), start = start, end = start + press.length)
        }
    }
}
