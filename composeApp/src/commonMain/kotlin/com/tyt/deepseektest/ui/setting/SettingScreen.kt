package com.tyt.deepseektest.ui.setting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.bundle.Bundle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tyt.deepseektest.DataHolder
import com.tyt.deepseektest.SimplePreferences
import com.tyt.deepseektest.data.ApiAddressItem
import com.tyt.deepseektest.data.Database
import com.tyt.deepseektest.data.ModelItem
import com.tyt.deepseektest.data.TokenItem
import deepseektest.composeapp.generated.resources.Res
import deepseektest.composeapp.generated.resources.about
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import kotlin.random.Random
import kotlin.reflect.typeOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun settingScreen(navController: NavHostController) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    NavHost(navController, startDestination = "home") {
        composable("home") {
            LazyColumn(
                state = scrollState,
                modifier = Modifier.draggable(orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(-delta)
                        }
                    })
            ) {
                items(SettingType.entries) { item ->
                    ListItem(modifier = Modifier.clickable {
                        navController.navigate(SettingItem(item))
                    }, headlineContent = { Text(item.displayName) })
                }
            }
        }
        composable<SettingItem>(typeMap = mapOf(/*typeOf<SettingItem>() to SettingParametersType, */typeOf<SettingType>() to SettingNavType)) { backStackEntry ->
            val settingItem: SettingItem = backStackEntry.toRoute()
            when (settingItem.type) {
                SettingType.ABOUT -> {
                    aboutScreen {
                        navController.navigate("home")
                    }
                }

                SettingType.TEST -> {
                    testScreen {
                        navController.navigate("home")
                    }
                }

                else -> {
                    settingDetailScreen(settingItem.type) {
                        navController.navigate("home")
                    }
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun aboutScreen(onBack: () -> Unit) {
    Column {
        TopAppBar(title = {
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(stringResource(Res.string.about))
            }
        }, navigationIcon = {
            Box(modifier = Modifier.padding(12.dp).clickable { onBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
            }
        })
        Text("暂时啥也没有")
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun settingDetailScreen(type: SettingType, onBack: () -> Unit) {
    val data by remember {
        derivedStateOf {
            when (type) {
                SettingType.MODEL_SETTING -> DataHolder.ModelList
                SettingType.API_ADDRESS_SETTING -> DataHolder.ApiAddressList
                SettingType.TOKEN_SETTING -> DataHolder.TokenList
                else -> throw Exception()
            }
        }
    }
    val dataNameString: String = when (type) {
        SettingType.MODEL_SETTING -> "model名"
        SettingType.API_ADDRESS_SETTING -> "api地址"
        SettingType.TOKEN_SETTING -> "token"
        else -> throw Exception()
    }
    val dataAliasString: String = when (type) {
        SettingType.MODEL_SETTING -> "model别名"
        SettingType.API_ADDRESS_SETTING -> "api地址别名"
        SettingType.TOKEN_SETTING -> "token别名"
        else -> throw Exception()
    }
    var dataName by remember { mutableStateOf("") }
    var dataAlias by remember { mutableStateOf("") }
    val canAdd by remember { derivedStateOf { dataName.isNotBlank() } }
    Column {
        TopAppBar(title = {
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(type.displayName)
            }
        }, navigationIcon = {
            Box(modifier = Modifier.padding(12.dp).clickable { onBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
            }
        })
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(data.size + 1, key = {
                if (it == 0) {
                    Unit
                } else {
                    data[it - 1]
                }
            }) { index ->
                //第一个固定为添加
                when (index) {
                    0 -> {
                        FlowRow {
                            OutlinedTextField(
                                value = dataName,
                                onValueChange = { dataName = it },
                                label = { Text(dataNameString) },
                                modifier = Modifier.weight(3f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = dataAlias,
                                onValueChange = { dataAlias = it },
                                label = { Text(dataAliasString) },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                IconButton(enabled = canAdd, onClick = {
                                    val time = System.currentTimeMillis()
                                    when (type) {
                                        SettingType.MODEL_SETTING -> {
                                            Database.insertModel(ModelItem(dataName, dataAlias, time))
                                            DataHolder.updateModelList()
                                        }

                                        SettingType.API_ADDRESS_SETTING -> {
                                            Database.insertApiAddress(ApiAddressItem(dataName, dataAlias, time))
                                            DataHolder.updateApiAddressList()
                                        }

                                        SettingType.TOKEN_SETTING -> {
                                            Database.insertToken(TokenItem(dataName, dataAlias, time))
                                            DataHolder.updateTokenList()
                                        }

                                        else -> throw Exception()
                                    }
                                    dataName = ""
                                    dataAlias = ""
                                }) {
                                    Icon(Icons.Default.Add, contentDescription = "add")
                                }
                            }
                        }
                    }

                    else -> {
//                            val indexTemp by derivedStateOf { index }
                        val item = data[index - 1]
                        var alias by remember { mutableStateOf(data[index - 1].alias + "") }
                        FlowRow {
                            OutlinedTextField(
                                value = item.data,
                                enabled = false,
                                onValueChange = { },
                                label = { Text(dataNameString) },
                                modifier = Modifier.weight(3f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = alias,
                                onValueChange = { alias = it },
                                label = { Text(dataAliasString) },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            Row(modifier = Modifier.align(Alignment.CenterVertically)) {
                                IconButton(enabled = item.alias != alias, onClick = {
                                    val time = System.currentTimeMillis()
                                    when (type) {
                                        SettingType.MODEL_SETTING -> {
                                            Database.insertModel(ModelItem(item.data, alias, time))
                                            DataHolder.updateModelList()
                                        }

                                        SettingType.API_ADDRESS_SETTING -> {
                                            Database.insertApiAddress(ApiAddressItem(item.data, alias, time))
                                            DataHolder.updateApiAddressList()
                                        }

                                        SettingType.TOKEN_SETTING -> {
                                            Database.insertToken(TokenItem(item.data, alias, time))
                                            DataHolder.updateTokenList()
                                        }

                                        else -> throw Exception()
                                    }
                                }) {
                                    Icon(Icons.Default.Refresh, contentDescription = "refresh")
                                }
                                IconButton(onClick = {
                                    when (type) {
                                        SettingType.MODEL_SETTING -> {
                                            Database.deleteModel(item.data)
                                            DataHolder.updateModelList()
                                        }

                                        SettingType.API_ADDRESS_SETTING -> {
                                            Database.deleteApiAddress(item.data)
                                            DataHolder.updateApiAddressList()
                                        }

                                        SettingType.TOKEN_SETTING -> {
                                            Database.deleteToken(item.data)
                                            DataHolder.updateTokenList()
                                        }

                                        else -> throw Exception()
                                    }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "delete")
                                }


                            }
                        }

//                        val enabled by remember {
//                            derivedStateOf {
//                                item.getAlias() != alias.value
//                            }
//                        }


                    }
                }
                HorizontalDivider(thickness = 12.dp, color = Color.Transparent)
            }
        }
    }
}

@Serializable
data class SettingItem(val type: SettingType)

//@Serializable
enum class SettingType(val displayName: String) {
    MODEL_SETTING("Model管理"), API_ADDRESS_SETTING("Api地址管理"), TOKEN_SETTING("Token管理"), ABOUT("关于"), TEST("测试")
}

//val SettingParametersType = object : NavType<SettingItem>(isNullableAllowed = false) {
//    override fun get(bundle: Bundle, key: String): SettingItem? {
//        val settingType = SettingType.entries.find { it.displayName == bundle.getString(key) }
//        return if (settingType != null) {
//            SettingItem(settingType)
//        } else {
//            null
//        }
//    }
//
//    override fun parseValue(value: String): SettingItem {
//        return SettingItem(SettingType.entries.find { it.displayName == value }!!)
//    }
//
//    override fun put(bundle: Bundle, key: String, value: SettingItem) {
//        bundle.putString(key, value.type.displayName)
//    }
//
//}

val SettingNavType = object : NavType<SettingType>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): SettingType? {
        return SettingType.entries.find { it.displayName == bundle.getString(key) }

    }

    override fun parseValue(value: String): SettingType {
        return SettingType.entries.find { it.name == value }!!
    }

    override fun put(bundle: Bundle, key: String, value: SettingType) {
        bundle.putString(key, value.displayName)
    }

}

@Composable
fun testSetting() {
    var text by remember { mutableStateOf("展示") }
    var result by remember { mutableStateOf("测试结果") }
    var int by remember { mutableStateOf(1) }
    val random by remember { mutableStateOf(Random(1)) }
    Box(modifier = Modifier.fillMaxHeight()) {
        Column {
            Button(onClick = {
                SimplePreferences.writeBoolean(
                    "test" + when (int) {
                        10 -> {
                            int = 0
                            "10"
                        }

                        else -> {
                            int += 1
                            "$int"
                        }
                    }, random.nextBoolean()
                )
            }) {
                Text("写个")
            }
            Button(onClick = {
                val index = random.nextInt(0, 11)
                text = "test$index:${SimplePreferences.readBoolean("test$index")}"
            }) {
                Text("读个")
            }
            Text(text)
            Button(onClick = {
                //此处应有请求,但是没有选择界面所以暂时没写
            }) {
                Text("测试")
            }
            Text(result)
        }
    }
}