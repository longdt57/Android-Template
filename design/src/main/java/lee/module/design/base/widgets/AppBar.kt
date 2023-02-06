package lee.module.design.base.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    navigationIconBackEnable: Boolean = true,
    navigationIconClick: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
    backgroundColor: Color = Color.Transparent
) {
    val navigationIcon: @Composable (() -> Unit) = navigationIconClick?.let {
        {
            IconButton(onClick = { it.invoke() }) {
                Icon(Icons.Rounded.ArrowBack, "")
            }
        }
    } ?: {}
    CenterAlignedTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = if (navigationIconBackEnable && navigationIconClick != null) navigationIcon else {
            {}
        },
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = backgroundColor)
    )
}
