package com.tyt.deepseektest.resources.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


object MyIcons {
    private var _Loading: ImageVector? = null
    private var _Error: ImageVector? = null
    private var _Save: ImageVector? = null
    private var _Ad_group: ImageVector? = null

    public val Error: ImageVector
        get() {
            if (_Error != null) {
                return _Error!!
            }
            _Error = ImageVector.Builder(
                name = "Error",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(480f, 680f)
                    quadToRelative(17f, 0f, 28.5f, -11.5f)
                    reflectiveQuadTo(520f, 640f)
                    reflectiveQuadToRelative(-11.5f, -28.5f)
                    reflectiveQuadTo(480f, 600f)
                    reflectiveQuadToRelative(-28.5f, 11.5f)
                    reflectiveQuadTo(440f, 640f)
                    reflectiveQuadToRelative(11.5f, 28.5f)
                    reflectiveQuadTo(480f, 680f)
                    moveToRelative(-40f, -160f)
                    horizontalLineToRelative(80f)
                    verticalLineToRelative(-240f)
                    horizontalLineToRelative(-80f)
                    close()
                    moveToRelative(40f, 360f)
                    quadToRelative(-83f, 0f, -156f, -31.5f)
                    reflectiveQuadTo(197f, 763f)
                    reflectiveQuadToRelative(-85.5f, -127f)
                    reflectiveQuadTo(80f, 480f)
                    reflectiveQuadToRelative(31.5f, -156f)
                    reflectiveQuadTo(197f, 197f)
                    reflectiveQuadToRelative(127f, -85.5f)
                    reflectiveQuadTo(480f, 80f)
                    reflectiveQuadToRelative(156f, 31.5f)
                    reflectiveQuadTo(763f, 197f)
                    reflectiveQuadToRelative(85.5f, 127f)
                    reflectiveQuadTo(880f, 480f)
                    reflectiveQuadToRelative(-31.5f, 156f)
                    reflectiveQuadTo(763f, 763f)
                    reflectiveQuadToRelative(-127f, 85.5f)
                    reflectiveQuadTo(480f, 880f)
                    moveToRelative(0f, -80f)
                    quadToRelative(134f, 0f, 227f, -93f)
                    reflectiveQuadToRelative(93f, -227f)
                    reflectiveQuadToRelative(-93f, -227f)
                    reflectiveQuadToRelative(-227f, -93f)
                    reflectiveQuadToRelative(-227f, 93f)
                    reflectiveQuadToRelative(-93f, 227f)
                    reflectiveQuadToRelative(93f, 227f)
                    reflectiveQuadToRelative(227f, 93f)
                    moveToRelative(0f, -320f)
                }
            }.build()
            return _Error!!
        }

    public val Loading: ImageVector
        get() {
            if (_Loading != null) {
                return _Loading!!
            }
            _Loading = ImageVector.Builder(
                name = "Loading",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.EvenOdd
                ) {
                    moveTo(13.917f, 7f)
                    arcTo(6.002f, 6.002f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.083f, 7f)
                    horizontalLineTo(1.071f)
                    arcToRelative(7.002f, 7.002f, 0f, isMoreThanHalf = false, isPositiveArc = true, 13.858f, 0f)
                    horizontalLineToRelative(-1.012f)
                    close()
                }
            }.build()
            return _Loading!!
        }

    public val Save: ImageVector
        get() {
            if (_Save != null) {
                return _Save!!
            }
            _Save = ImageVector.Builder(
                name = "Save",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(840f, 280f)
                    verticalLineToRelative(480f)
                    quadToRelative(0f, 33f, -23.5f, 56.5f)
                    reflectiveQuadTo(760f, 840f)
                    horizontalLineTo(200f)
                    quadToRelative(-33f, 0f, -56.5f, -23.5f)
                    reflectiveQuadTo(120f, 760f)
                    verticalLineToRelative(-560f)
                    quadToRelative(0f, -33f, 23.5f, -56.5f)
                    reflectiveQuadTo(200f, 120f)
                    horizontalLineToRelative(480f)
                    close()
                    moveToRelative(-80f, 34f)
                    lineTo(646f, 200f)
                    horizontalLineTo(200f)
                    verticalLineToRelative(560f)
                    horizontalLineToRelative(560f)
                    close()
                    moveTo(480f, 720f)
                    quadToRelative(50f, 0f, 85f, -35f)
                    reflectiveQuadToRelative(35f, -85f)
                    reflectiveQuadToRelative(-35f, -85f)
                    reflectiveQuadToRelative(-85f, -35f)
                    reflectiveQuadToRelative(-85f, 35f)
                    reflectiveQuadToRelative(-35f, 85f)
                    reflectiveQuadToRelative(35f, 85f)
                    reflectiveQuadToRelative(85f, 35f)
                    moveTo(240f, 400f)
                    horizontalLineToRelative(360f)
                    verticalLineToRelative(-160f)
                    horizontalLineTo(240f)
                    close()
                    moveToRelative(-40f, -86f)
                    verticalLineToRelative(446f)
                    verticalLineToRelative(-560f)
                    close()
                }
            }.build()
            return _Save!!
        }

    public val Ad_group: ImageVector
        get() {
            if (_Ad_group != null) {
                return _Ad_group!!
            }
            _Ad_group = ImageVector.Builder(
                name = "Ad_group",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(320f, 640f)
                    horizontalLineToRelative(480f)
                    verticalLineToRelative(-400f)
                    horizontalLineTo(320f)
                    close()
                    moveToRelative(0f, 80f)
                    quadToRelative(-33f, 0f, -56.5f, -23.5f)
                    reflectiveQuadTo(240f, 640f)
                    verticalLineToRelative(-480f)
                    quadToRelative(0f, -33f, 23.5f, -56.5f)
                    reflectiveQuadTo(320f, 80f)
                    horizontalLineToRelative(480f)
                    quadToRelative(33f, 0f, 56.5f, 23.5f)
                    reflectiveQuadTo(880f, 160f)
                    verticalLineToRelative(480f)
                    quadToRelative(0f, 33f, -23.5f, 56.5f)
                    reflectiveQuadTo(800f, 720f)
                    close()
                    moveTo(160f, 880f)
                    quadToRelative(-33f, 0f, -56.5f, -23.5f)
                    reflectiveQuadTo(80f, 800f)
                    verticalLineToRelative(-560f)
                    horizontalLineToRelative(80f)
                    verticalLineToRelative(560f)
                    horizontalLineToRelative(560f)
                    verticalLineToRelative(80f)
                    close()
                    moveToRelative(160f, -720f)
                    verticalLineToRelative(480f)
                    close()
                }
            }.build()
            return _Ad_group!!
        }

}