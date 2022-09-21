package lee.module.design

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun ComposeVideoPlayer(videoUrl: String, listener: Player.Listener? = null) {
    val context = LocalContext.current

    /**
     * Variable Area
     */
    // rememberSaveable keeps exoPlayer alive when composable redraw, onConfiguration changes...
    val exoPlayer = rememberSaveable { ExoPlayer.Builder(context).build() }

    // remember block keeps the mediaItem alive when redraw. mediaItem is recreated whenever videoUrl changes
    val mediaItem = remember(key1 = videoUrl) { MediaItem.fromUri(videoUrl.toUri()) }

    // Keep this instance alive when compose redraws
    val playerView = remember { createDefaultVideoView(context, exoPlayer) }

    /**
     * Example for viewModel Flow. See [LoginViewModel and LoginActivity]
     */
    // val screen = remember { viewModel.navigator.collectAsState(DefaultScreen).value}
    // navController.navigate(screen.route)

    /**
     * UI Area
     */
    // Show the Main UI
    AndroidView(modifier = Modifier.fillMaxSize(), factory = { playerView })

    // Run when init, and whenever videoUrl is changed
    DisposableEffect(key1 = videoUrl) {
        /**
         * Short time, light init Area.
         * Use for: init light and important resources
         */
        // 1. This code will be executed only on initially, and when videoUrl is changed
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

        onDispose {
            /**
             * Destroy/release resource Area
             */
            // 4. this code will be executed when this composable is disposed.
            exoPlayer.release()
        }
    }

    /**
     * Resume Area.
     */
    // run whenever this composable redraw. IE: call API when redraw this composable.
    SideEffect {
        // 2. This code will be executed whenever this composable redraw
    }

    /**
     * Long time, heavy init Area
     * Use for: Call API, Add Listener, ...
     */
    // Run only 1 time when init this composable
    LaunchedEffect(key1 = Unit) {
        // 3. This code will be executed only on initially, and when videoUrl is changed. It runs after 1
        listener?.let {
            exoPlayer.addListener(it)
        }
    }

    /**
     * Old Android Lifecycle Area
     * Use for: onResume/onPause/onStop behavior of old android lifecycle
     */
    // exoPlayer pause/resume
    OnLifecycleEvent { _, event ->
        when (event) {
            // Run when user comeback (ie: Press home button, then press recent app button => reopen app
            Lifecycle.Event.ON_RESUME -> exoPlayer.playWhenReady = true

            // Run when user go to other screen
            Lifecycle.Event.ON_PAUSE -> exoPlayer.playWhenReady = false

            // Run when user go to other screen and destroy current screen.
            Lifecycle.Event.ON_STOP -> exoPlayer.stop()
            else -> Unit
        }
    }
}

private fun createDefaultVideoView(context: Context, exoPlayer: ExoPlayer): StyledPlayerView {
    return StyledPlayerView(context).apply {
        player = exoPlayer
        useController = true
    }
}