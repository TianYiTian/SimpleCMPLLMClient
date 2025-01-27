package com.tyt.deepseektest

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import deepseektest.composeapp.generated.resources.Res
import deepseektest.composeapp.generated.resources.home
import deepseektest.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.StringResource

enum class AppDestinations(
    val label: StringResource,
    val icon: ImageVector,
    val contentDescription: StringResource
) {
    HOME(Res.string.home, Icons.Default.Home, Res.string.home),
    SETTINGS(Res.string.settings, Icons.Default.Settings, Res.string.settings),
}