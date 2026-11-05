package com.eraorder.arena.core.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp

private val Ink = SolidColor(Color.Black)

private fun builder(name: String) = ImageVector.Builder(
    name = name,
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
)

private fun filled(name: String, path: String): ImageVector =
    builder(name).addPath(pathData = addPathNodes(path), fill = Ink).build()

private fun outlined(name: String, path: String, width: Float): ImageVector =
    builder(name).addPath(
        pathData = addPathNodes(path),
        stroke = Ink,
        strokeLineWidth = width,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ).build()

private const val HEART =
    "M12,21.35 L10.55,20.03 C5.4,15.36 2,12.28 2,8.5 C2,5.42 4.42,3 7.5,3 " +
        "C9.24,3 10.91,3.81 12,5.09 C13.09,3.81 14.76,3 16.5,3 " +
        "C19.58,3 22,5.42 22,8.5 C22,12.28 18.6,15.36 13.45,20.04 Z"

object AppIcons {
    val ArrowBack: ImageVector by lazy {
        filled(
            "ArrowBack",
            "M20,11 H7.83 L13.41,5.41 L12,4 L4,12 L12,20 L13.41,18.59 L7.83,13 H20 Z"
        )
    }

    val PlayArrow: ImageVector by lazy {
        filled("PlayArrow", "M8,5 L19,12 L8,19 Z")
    }

    val Check: ImageVector by lazy {
        filled("Check", "M9,16.17 L4.83,12 L3.41,13.41 L9,19 L21,7 L19.59,5.59 Z")
    }

    val Clear: ImageVector by lazy {
        filled(
            "Clear",
            "M19,6.41 L17.59,5 L12,10.59 L6.41,5 L5,6.41 L10.59,12 L5,17.59 " +
                "L6.41,19 L12,13.41 L17.59,19 L19,17.59 L13.41,12 Z"
        )
    }

    val Favorite: ImageVector by lazy { filled("Favorite", HEART) }

    val FavoriteBorder: ImageVector by lazy { outlined("FavoriteBorder", HEART, 1.9f) }

    val Star: ImageVector by lazy {
        filled(
            "Star",
            "M12,2 L15.09,8.26 L22,9.27 L17,14.14 L18.18,21.02 L12,17.77 " +
                "L5.82,21.02 L7,14.14 L2,9.27 L8.91,8.26 Z"
        )
    }

    val Refresh: ImageVector by lazy {
        filled(
            "Refresh",
            "M17.65,6.35 C16.2,4.9 14.21,4 12,4 C7.58,4 4,7.58 4,12 C4,16.42 7.58,20 12,20 " +
                "C15.73,20 18.84,17.45 19.73,14 L17.65,14 C16.83,16.33 14.61,18 12,18 " +
                "C8.69,18 6,15.31 6,12 C6,8.69 8.69,6 12,6 C13.66,6 15.14,6.69 16.22,7.78 " +
                "L13,11 L20,11 L20,4 Z"
        )
    }

    val Settings: ImageVector by lazy {
        filled(
            "Settings",
            "M3,7 H21 V9 H3 Z M6.5,5 H9.5 V11 H6.5 Z M3,15 H21 V17 H3 Z M14.5,13 H17.5 V19 H14.5 Z"
        )
    }

    val CheckCircle: ImageVector by lazy {
        builder("CheckCircle")
            .addPath(
                pathData = addPathNodes(
                    "M12,3 C16.97,3 21,7.03 21,12 C21,16.97 16.97,21 12,21 " +
                        "C7.03,21 3,16.97 3,12 C3,7.03 7.03,3 12,3 Z"
                ),
                stroke = Ink,
                strokeLineWidth = 1.9f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            )
            .addPath(
                pathData = addPathNodes(
                    "M7.2,12.3 L10.4,15.5 L16.8,9.1 L18.2,10.5 L10.4,18.3 L5.8,13.7 Z"
                ),
                fill = Ink
            )
            .build()
    }

    val DateRange: ImageVector by lazy {
        builder("DateRange")
            .addPath(
                pathData = addPathNodes("M4,5 H20 V21 H4 Z"),
                stroke = Ink,
                strokeLineWidth = 1.7f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            )
            .addPath(
                pathData = addPathNodes(
                    "M7,2 H9 V5 H7 Z M15,2 H17 V5 H15 Z M4,5 H20 V9 H4 Z " +
                        "M6.5,11.5 H9 V14 H6.5 Z M10.75,11.5 H13.25 V14 H10.75 Z " +
                        "M15,11.5 H17.5 V14 H15 Z M6.5,15.5 H9 V18 H6.5 Z " +
                        "M10.75,15.5 H13.25 V18 H10.75 Z"
                ),
                fill = Ink
            )
            .build()
    }
}
