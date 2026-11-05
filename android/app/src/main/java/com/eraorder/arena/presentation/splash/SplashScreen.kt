package com.eraorder.arena.presentation.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eraorder.arena.R
import com.eraorder.arena.core.di.AppViewModelFactory
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.WalnutChip
import com.eraorder.arena.core.theme.WalnutDeep
import com.eraorder.arena.core.ui.GalleryBackground

@Composable
fun SplashRoute(onFinished: () -> Unit) {
    val splashViewModel: SplashViewModel = viewModel(factory = AppViewModelFactory())
    val finished by splashViewModel.finished.collectAsStateWithLifecycle()
    LaunchedEffect(finished) {
        if (finished) onFinished()
    }
    SplashScreen()
}

@Composable
private fun SplashScreen() {
    val transition = rememberInfiniteTransition(label = "splash")
    val outerSpin by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(9000, easing = LinearEasing), RepeatMode.Restart),
        label = "outerSpin"
    )
    val innerSpin by transition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(14000, easing = LinearEasing), RepeatMode.Restart),
        label = "innerSpin"
    )
    val pulse by transition.animateFloat(
        initialValue = 0.45f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(tween(1400, easing = LinearEasing), RepeatMode.Reverse),
        label = "pulse"
    )

    GalleryBackground(
        overlay = listOf(
            WalnutDeep.copy(alpha = 0.92f),
            WalnutDeep.copy(alpha = 0.88f),
            WalnutDeep.copy(alpha = 0.97f)
        ),
        lampAlpha = 0.08f,
        cornerSprite = R.drawable.sprite_3,
        cornerAlpha = 0.10f
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(214.dp).rotate(outerSpin)) {
                    drawArc(
                        color = Bronze.copy(alpha = 0.35f),
                        startAngle = 0f,
                        sweepAngle = 252f,
                        useCenter = false,
                        topLeft = Offset.Zero,
                        size = Size(size.width, size.height),
                        style = Stroke(width = 5f)
                    )
                }
                Canvas(modifier = Modifier.size(170.dp).rotate(innerSpin)) {
                    drawArc(
                        color = Bronze.copy(alpha = 0.18f),
                        startAngle = 40f,
                        sweepAngle = 200f,
                        useCenter = false,
                        topLeft = Offset.Zero,
                        size = Size(size.width, size.height),
                        style = Stroke(width = 4f)
                    )
                }
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = stringResource(R.string.app_name),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(148.dp)
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.splash_title),
                    style = MaterialTheme.typography.displaySmall,
                    color = WalnutDeep.copy(alpha = 0.55f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 3.dp)
                )
                Text(
                    text = stringResource(R.string.splash_title),
                    style = MaterialTheme.typography.displaySmall,
                    color = Parchment,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.splash_subtitle),
                style = MaterialTheme.typography.labelLarge,
                color = Bronze,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(36.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .width(168.dp)
                    .height(3.dp),
                color = Bronze,
                trackColor = WalnutChip,
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = stringResource(R.string.splash_loading),
                style = MaterialTheme.typography.labelSmall,
                color = ParchmentDim,
                modifier = Modifier.alpha(pulse)
            )
        }
    }
}
