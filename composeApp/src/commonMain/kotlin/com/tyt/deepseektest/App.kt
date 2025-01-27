package com.tyt.deepseektest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.tyt.deepseektest.data.*
import com.tyt.deepseektest.data.db.ChatDBModel
import com.tyt.deepseektest.ui.home.homeScreen
import com.tyt.deepseektest.ui.setting.settingScreen
import com.tyt.deepseektest.utils.SimpleHolder
import deepseektest.composeapp.generated.resources.Res
import deepseektest.composeapp.generated.resources.compose_multiplatform
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
@Preview
fun App(onBack: State<SimpleHolder<() -> Boolean>>) {
    Init
    MaterialTheme {
//        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
//        val windowSizeClass = calculateWindowSizeClass()
        val adaptiveInfo = currentWindowAdaptiveInfo()
//        val customNavSuiteType = with(adaptiveInfo) {
//            if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
//                NavigationSuiteType.NavigationDrawer
//            } else {
//                NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
//            }
//        }
        var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                AppDestinations.entries.forEach {
                    item(icon = {
                        Icon(
                            it.icon, contentDescription = stringResource(it.contentDescription)
                        )
                    },
                        label = { Text(stringResource(it.label)) },
                        selected = it == currentDestination,
                        onClick = { currentDestination = it })
                }
            },
//            containerColor = MaterialTheme.colorScheme.primary,
//            contentColor = MaterialTheme.colorScheme.primary,
//            layoutType = customNavSuiteType
        ) {
            val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator()
            val showAddChat = remember { mutableStateOf(false) }
            val navController = rememberNavController()
            val currentChatDBModel = remember { mutableStateOf<ChatDBModel?>(null) }
            when (currentDestination) {
                AppDestinations.HOME -> homeScreen(scaffoldNavigator, showAddChat, currentChatDBModel, onBack)
                AppDestinations.SETTINGS -> settingScreen(navController)
            }
        }

    }
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun testHomeScreen(scaffoldNavigator: ThreePaneScaffoldNavigator<Nothing>) {
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf("nothing") }
    var insertText by remember { mutableStateOf("insert") }
    ListDetailPaneScaffold(directive = scaffoldNavigator.scaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        listPane = {
            AnimatedPane(
                modifier = Modifier.preferredWidth(200.dp),
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.secondary,
//                    onClick = {
//                        coroutineScope.launch {
//                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
//                        }
//                    }
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = {
                            insertText = run {
                                val model = ChatDBModel(
                                    requestModel = ChatRequestModel(
                                        messages = mutableStateListOf(), model = ""
                                    ), apiAddress = "TODO()", token = "TODO()"
                                )
                                model.id = Database.insertChat(model)
                                "inserted" + model.id
                            }
                        }, content = {
                            Text(insertText)
                        })
                        Button(onClick = {
                            text = Database.getChats().toString()
                        }, content = {
                            Text("getAll")
                        })
                        Text(text)
                    }
                }
            }
        },
        detailPane = {
            AnimatedPane(modifier = Modifier) {
                Surface(color = MaterialTheme.colorScheme.primary,
                    onClick = { coroutineScope.launch { scaffoldNavigator.navigateBack() } }) {
                    Text("Details")
                }
            }
        })
}


@Composable
fun oldApp() {
    var showContent by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("Click me!") }
    val coroutineScope = rememberCoroutineScope()
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            showContent = !showContent
            coroutineScope.launch {
            }
        }) {
            Text(text)
        }
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
    }
}

