package com.tyt.deepseektest

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.tyt.deepseektest.resources.icons.MyIcons
import com.tyt.deepseektest.utils.SimpleHolder
import deepseektest.composeapp.generated.resources.Res
import deepseektest.composeapp.generated.resources.title
import org.jetbrains.compose.resources.stringResource
import java.awt.Toolkit

fun main() = application {
    val state = rememberWindowState()
    state.position = WindowPosition.Absolute(0.dp, 0.dp)
//    state.placement = WindowPlacement.Maximized
    val screenSize = Toolkit.getDefaultToolkit().screenSize
//    println(LocalDensity.current.density)
    state.size = DpSize(
        (screenSize.width-100).dp, (screenSize.height-100).dp
    )
    val icon = rememberVectorPainter(MyIcons.Ad_group)
    Window(
        onCloseRequest = { exitApplication() },
        title = stringResource(Res.string.title),
        icon = icon,
        state = state
    ) {
//        println("$width $height")
//        println(state.size.toString())
        val callback = rememberUpdatedState(SimpleHolder { false })
        App(callback)
    }
}