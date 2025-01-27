package com.tyt.deepseektest.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tyt.deepseektest.DataHolder
import com.tyt.deepseektest.data.*
import com.tyt.deepseektest.data.db.ChatDBModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun createChatPane(onBack: (ChatDBModel?) -> Unit) {
//    val adaptiveInfo = currentWindowAdaptiveInfo()
//    val showBack = (adaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT)
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var modelName by remember { mutableStateOf("") }
    var modelAlias by remember { mutableStateOf("") }
    var modelSelectExpanded by remember { mutableStateOf(false) }
    val showModelSelect by remember { derivedStateOf { DataHolder.ModelList.size > 0 } }

    var addressName by remember { mutableStateOf("") }
    var addressAlias by remember { mutableStateOf("") }
    var addressSelectExpanded by remember { mutableStateOf(false) }
    val showAddressSelect by remember { derivedStateOf { DataHolder.ApiAddressList.size > 0 } }

    var tokenName by remember { mutableStateOf("") }
    var tokenAlias by remember { mutableStateOf("") }
    var tokenSelectExpanded by remember { mutableStateOf(false) }
    val showTokenSelect by remember { derivedStateOf { DataHolder.TokenList.size > 0 } }

    var systemHint by remember { mutableStateOf("你是一个有用的机器人,请回答我的问题") }

    val confirmEnabled by
    remember { derivedStateOf { modelName.isNotBlank() && addressName.isNotBlank() && tokenName.isNotBlank() } }
    Column {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("新建对话")
                }
            },
            navigationIcon = {
                Box(modifier = Modifier.padding(12.dp).clickable { onBack(null) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                }
            }
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .weight(1f)
                .draggable(
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(-delta)
                        }
                    },
                    orientation = Orientation.Vertical
                )
        ) {
            FlowRow {
                OutlinedTextField(
                    value = modelName,
                    onValueChange = { modelName = it },
                    label = { Text("model名") },
                    modifier = Modifier.weight(3f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = modelAlias,
                    onValueChange = { modelAlias = it },
                    label = { Text("model别名") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                if (showModelSelect) {
                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        IconButton(onClick = { modelSelectExpanded = !modelSelectExpanded }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More options")
                        }
                        DropdownMenu(
                            expanded = modelSelectExpanded,
                            onDismissRequest = { modelSelectExpanded = false },
                        ) {
                            DataHolder.ModelList.forEach {
                                DropdownMenuItem(
                                    text = { Text("${it.data}(${it.alias})") },
                                    onClick = {
                                        modelName = it.data
                                        modelAlias = it.alias
                                        modelSelectExpanded = !modelSelectExpanded
                                    }
                                )
                            }
                        }
                    }

                }
            }

            FlowRow {
                OutlinedTextField(
                    value = addressName,
                    onValueChange = { addressName = it },
                    label = { Text("api地址") },
                    modifier = Modifier.weight(3f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = addressAlias,
                    onValueChange = { addressAlias = it },
                    label = { Text("api地址别名") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                if (showAddressSelect) {
                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        IconButton(onClick = { addressSelectExpanded = !addressSelectExpanded }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More options")
                        }
                        DropdownMenu(
                            expanded = addressSelectExpanded,
                            onDismissRequest = { addressSelectExpanded = false },
                        ) {
                            DataHolder.ApiAddressList.forEach {
                                DropdownMenuItem(
                                    text = { Text("${it.data}(${it.alias})") },
                                    onClick = {
                                        addressName = it.data
                                        addressAlias = it.alias
                                        addressSelectExpanded = !addressSelectExpanded
                                    }
                                )
                            }
                        }
                    }

                }
            }

            FlowRow {
                OutlinedTextField(
                    value = tokenName,
                    onValueChange = { tokenName = it },
                    label = { Text("token") },
                    modifier = Modifier.weight(3f),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = tokenAlias,
                    onValueChange = { tokenAlias = it },
                    label = { Text("token别名") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                if (showTokenSelect) {
                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        IconButton(onClick = { tokenSelectExpanded = !tokenSelectExpanded }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More options")
                        }
                        DropdownMenu(
                            expanded = tokenSelectExpanded,
                            onDismissRequest = { tokenSelectExpanded = false },
                        ) {
                            DataHolder.TokenList.forEach {
                                DropdownMenuItem(
                                    text = { Text("${it.data}(${it.alias})") },
                                    onClick = {
                                        tokenName = it.data
                                        tokenAlias = it.alias
                                        tokenSelectExpanded = !tokenSelectExpanded
                                    }
                                )
                            }
                        }
                    }

                }
            }

            OutlinedTextField(
                value = systemHint,
                onValueChange = { systemHint = it },
                label = { Text("系统提示词") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
        }
        Row(horizontalArrangement = Arrangement.End) {
            IconButton(
                onClick = {
                    val time = System.currentTimeMillis()
                    if (!Database.checkModelExists(modelName)) {
                        Database.insertModel(ModelItem(modelName, modelAlias, time))
                        DataHolder.updateModelList()
                    }
                    if (!Database.checkApiAddressExists(addressName)) {
                        Database.insertApiAddress(ApiAddressItem(addressName, addressAlias, time))
                        DataHolder.updateApiAddressList()
                    }
                    if (!Database.checkTokenExists(tokenName)) {
                        Database.insertToken(TokenItem(tokenName, tokenAlias, time))
                        DataHolder.updateTokenList()
                    }
                    onBack(
                        ChatDBModel(
                            ChatRequestModel(
                                if (systemHint.isNotBlank()) {
                                    mutableStateListOf(
                                        ChatMessageModel(
                                            role = ChatRole.SYSTEM,
                                            content = systemHint
                                        )
                                    )
                                } else {
                                    mutableStateListOf()
                                },
                                modelName
                            ),
                            addressName,
                            tokenName,
                            lastModified = System.currentTimeMillis()
                        )
                    )
                },
                enabled = confirmEnabled
            ) {
                Icon(Icons.Default.Check, "确认")
            }
        }
    }
}