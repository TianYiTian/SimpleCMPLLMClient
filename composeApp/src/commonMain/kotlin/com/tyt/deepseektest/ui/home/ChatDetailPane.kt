package com.tyt.deepseektest.ui.home

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowWidthSizeClass
import com.tyt.deepseektest.DataHolder
import com.tyt.deepseektest.NetworkService
import com.tyt.deepseektest.data.ChatRole
import com.tyt.deepseektest.data.Database
import com.tyt.deepseektest.data.SendStatus
import com.tyt.deepseektest.data.db.ChatDBModel
import com.tyt.deepseektest.getPlatform
import com.tyt.deepseektest.resources.icons.MyIcons
import com.tyt.deepseektest.utils.SimpleHolder
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun chatDetailPane(
    currentChatDBModel: MutableState<ChatDBModel?>,
    onBack: () -> Unit,
    onBackIntercept: State<SimpleHolder<() -> Boolean>>,
    onRefresh: () -> Unit,
) {
    if (currentChatDBModel.value == null) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text("请选中一个对话或新建一个对话")

        }
    } else {
        key(currentChatDBModel.value!!) {
            val coroutineScope = rememberCoroutineScope()
            val messages = remember { currentChatDBModel.value!!.requestModel.messages }
//        val modifyingTitle by remember { mutableStateOf (derivedStateOf{ currentChatDBModel.value!!.title + "" } )}
//        val temp by remember { derivedStateOf {  } }
//        val string = remember { mutableStateOf(derivedStateOf { currentChatDBModel.value!!.title.value+"" }) }
            var titleRefreshKey by mutableStateOf(false)
            val adaptiveInfo = currentWindowAdaptiveInfo()
            var inputText by remember { mutableStateOf("") }
//        Text(currentChatDBModel.value.toString())
            var showSetting by remember { mutableStateOf(false) }
            var menuMoreExpanded by remember { mutableStateOf(false) }

            var iconRefreshKey by remember { mutableStateOf(1L) }

            val refreshFunction = {
                currentChatDBModel.value!!.lastModified = System.currentTimeMillis()
                Database.insertChat(currentChatDBModel.value!!)
                DataHolder.updateChatList()
                iconRefreshKey += 1
            }

            if (showSetting) {
                DisposableEffect(Unit) {
                    val previousBackIntercept = onBackIntercept.value.data
                    if (getPlatform().isAndroid() && adaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                        onBackIntercept.value.data = {
                            showSetting = false
                            true
                        }
                    }
                    onDispose { onBackIntercept.value.data = previousBackIntercept }
                }
                var modifyingApiAddress by remember { mutableStateOf(currentChatDBModel.value!!.apiAddress + "") }
                var modifyingToken by remember { mutableStateOf(currentChatDBModel.value!!.token + "") }
                var modifyingModel by remember { mutableStateOf(currentChatDBModel.value!!.requestModel.model + "") }

                var modelSelectExpanded by remember { mutableStateOf(false) }
                var tokenSelectExpanded by remember { mutableStateOf(false) }
                var apiAddressSelectExpanded by remember { mutableStateOf(false) }

                val canSave by remember {
                    derivedStateOf {
                        modifyingToken != currentChatDBModel.value!!.token || modifyingApiAddress != currentChatDBModel.value!!.apiAddress || modifyingModel != currentChatDBModel.value!!.requestModel.model
                    }
                }

                Column(modifier = Modifier.padding(12.dp)) {
                    Box {
                        TopAppBar(title = {
                            Box(
                                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                            ) {
                                Text("修改设置")
                            }
                        }, navigationIcon = {
                            Box(modifier = Modifier.clickable { showSetting = false }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                            }
                        }, actions = {
                            IconButton(onClick = {
                                currentChatDBModel.value!!.token = modifyingToken
                                currentChatDBModel.value!!.apiAddress = modifyingApiAddress
                                currentChatDBModel.value!!.requestModel.model = modifyingModel
                                showSetting = false
                            }, enabled = canSave) {
                                Icon(MyIcons.Save, "save")
                            }
                        })
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            modifyingModel,
                            onValueChange = { modifyingModel = it },
                            label = { Text("Model") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            IconButton(onClick = { modelSelectExpanded = !modelSelectExpanded }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More options")
                            }
                            DropdownMenu(
                                expanded = modelSelectExpanded,
                                onDismissRequest = { modelSelectExpanded = false },
                            ) {
                                DataHolder.ModelList.forEach {
                                    DropdownMenuItem(text = { Text("${it.data}(${it.alias})") }, onClick = {
                                        modifyingModel = it.data
                                        modelSelectExpanded = !modelSelectExpanded
                                    })
                                }
                            }
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            modifyingApiAddress,
                            onValueChange = { modifyingApiAddress = it },
                            label = { Text("Api地址") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            IconButton(onClick = { apiAddressSelectExpanded = !apiAddressSelectExpanded }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More options")
                            }
                            DropdownMenu(
                                expanded = apiAddressSelectExpanded,
                                onDismissRequest = { apiAddressSelectExpanded = false },
                            ) {
                                DataHolder.ApiAddressList.forEach {
                                    DropdownMenuItem(text = { Text("${it.data}(${it.alias})") }, onClick = {
                                        modifyingApiAddress = it.data
                                        apiAddressSelectExpanded = !apiAddressSelectExpanded
                                    })
                                }
                            }
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            modifyingToken,
                            onValueChange = { modifyingToken = it },
                            label = { Text("Token") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            IconButton(onClick = { tokenSelectExpanded = !tokenSelectExpanded }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More options")
                            }
                            DropdownMenu(
                                expanded = tokenSelectExpanded,
                                onDismissRequest = { tokenSelectExpanded = false },
                            ) {
                                DataHolder.TokenList.forEach {
                                    DropdownMenuItem(text = { Text("${it.data}(${it.alias})") }, onClick = {
                                        modifyingToken = it.data
                                        tokenSelectExpanded = !tokenSelectExpanded
                                    })
                                }
                            }
                        }
                    }

                }
            } else {

                Column {
                    Box(modifier = Modifier.padding(end = 12.dp)) {
                        TopAppBar(title = {
                            Box(
                                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                            ) {
                                key(titleRefreshKey) {
                                    var modifyingTitle by remember { mutableStateOf(currentChatDBModel.value!!.title + "") }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        OutlinedTextField(
                                            modifyingTitle,
                                            onValueChange = { modifyingTitle = it },
                                            label = { Text("标题") },
                                            modifier = Modifier.weight(3f),
                                            singleLine = true
                                        )
                                        if (derivedStateOf { modifyingTitle != currentChatDBModel.value!!.title }.value) {
                                            IconButton(/*enabled = modifyingTitle.value!=currentChatDBModel.value!!.title,*/
                                                onClick = {
                                                    currentChatDBModel.value!!.title = modifyingTitle
                                                    currentChatDBModel.value!!.lastModified = System.currentTimeMillis()
                                                    titleRefreshKey = !titleRefreshKey
                                                    Database.insertChat(currentChatDBModel.value!!)
                                                    onRefresh()
                                                }) {
                                                Icon(Icons.Default.Refresh, "refresh")
                                            }
                                        }
                                    }
                                }

                            }
                        }, navigationIcon = {
                            if (adaptiveInfo.windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED) {
                                Box(modifier = Modifier.padding(12.dp).clickable { onBack() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                                }
                            }
                        }, actions = {
                            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                IconButton(onClick = { menuMoreExpanded = !menuMoreExpanded }) {
                                    Icon(Icons.Default.MoreVert, contentDescription = "More options")
                                }
                                DropdownMenu(
                                    expanded = menuMoreExpanded,
                                    onDismissRequest = { menuMoreExpanded = false },
                                ) {
                                    DropdownMenuItem(text = { Text("修改设置") }, onClick = {
                                        menuMoreExpanded = !menuMoreExpanded
                                        showSetting = true
                                    })
                                }
                            }

                        })
                    }

                    val listState = rememberLazyListState()
                    LaunchedEffect(messages.size) {
                        listState.scrollToItem(messages.size)
                    }
                    LazyColumn(
                        modifier = Modifier.weight(1f).draggable(
                            state = rememberDraggableState { delta ->
                                coroutineScope.launch {
                                    listState.scrollBy(-delta)
                                }
                            }, orientation = Orientation.Vertical
                        ).padding(24.dp), state = listState
                    ) {
                        itemsIndexed(messages, key = { index, item ->
                            item.toString() + index
                        }) { index, item ->
                            CompositionLocalProvider(
                                LocalContentColor provides MaterialTheme.colorScheme.onBackground
                            ) {
//                                val item = messages[index]
                                val isLast by derivedStateOf { index == messages.size - 1 }
                                when (item.role) {
                                    ChatRole.SYSTEM -> {
                                        BoxWithConstraints(
                                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                                        ) {
                                            Text(item.content, modifier = Modifier.widthIn(0.dp, maxWidth * 2 / 3))
                                        }
                                    }

                                    ChatRole.TOOL -> {
                                        //doNothing
                                    }

                                    else -> {

                                        CompositionLocalProvider(
                                            LocalLayoutDirection provides if (item.role == ChatRole.USER) LayoutDirection.Rtl else LayoutDirection.Ltr,
                                        ) {
                                            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                                                val widthCalculatedMax by derivedStateOf { maxWidth * 9 / 10f }
                                                Column(
                                                    horizontalAlignment = Alignment.Start,
                                                    modifier = Modifier.widthIn(0.dp, widthCalculatedMax)
                                                        .background(MaterialTheme.colorScheme.background).border(
                                                            2.dp,
                                                            MaterialTheme.colorScheme.outline,
                                                            shape = RoundedCornerShape(12.dp)
                                                        ).padding(12.dp),
                                                ) {
                                                    Text(
                                                        item.role.value,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 30.sp
                                                    )
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        SelectionContainer {
                                                            Text(
                                                                item.content, modifier = Modifier.widthIn(
                                                                    0.dp, widthCalculatedMax - 48.dp
                                                                )
                                                            )
                                                        }
                                                        key(iconRefreshKey){
                                                            when (item.sendStatus) {
                                                                SendStatus.SENDING -> {
                                                                    val infiniteTransition =
                                                                        rememberInfiniteTransition()
                                                                    val degree by infiniteTransition.animateFloat(
                                                                        0f, 360f, infiniteRepeatable(tween(1000))
                                                                    )
                                                                    Icon(
                                                                        MyIcons.Loading,
                                                                        "loading",
                                                                        modifier = Modifier.rotate(degree)
                                                                    )
                                                                }

                                                                SendStatus.FAILED -> {
                                                                    IconButton(enabled = isLast, onClick = {
                                                                        coroutineScope.launch {
                                                                            NetworkService.resendLast(
                                                                                currentChatDBModel.value!!,
                                                                                refreshFunction
                                                                            )
                                                                        }
                                                                    }) {
                                                                        Icon(MyIcons.Error, "error", tint = Color.Red)
                                                                    }
                                                                }

                                                                SendStatus.COMPLETED -> {
                                                                    if (item.role == ChatRole.USER) {
                                                                        Icon(
                                                                            Icons.Default.Check,
                                                                            "completed",
                                                                            tint = Color.Green
                                                                        )
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }


                                    }
                                }
                                if (!isLast) {
                                    HorizontalDivider(thickness = 12.dp, color = Color.Transparent)
                                }
                            }

                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = inputText,
                                onValueChange = { inputText = it },
                                label = { Text("请输入") },
                                modifier = Modifier.weight(1f),
                                maxLines = 2
                            )
                            IconButton(enabled = inputText.isNotBlank() && messages.last().sendStatus != SendStatus.SENDING,
                                onClick = {
                                    val tempText = inputText
                                    coroutineScope.launch {
                                        NetworkService.sendChatMessage(
                                            currentChatDBModel.value!!, tempText, refreshFunction
                                        )
                                    }
                                    inputText = ""
                                }) {
                                Icon(Icons.AutoMirrored.Filled.Send, "发送")
                            }
                        }
                    }

                }

            }
        }
    }
}

//@Composable
//fun chatDetailTest(){
//    Button(onClick = {
//        coroutineScope.launch {
//            val response = OpenAIData.CLIENT.post(currentChatDBModel.value!!.apiAddress) {
//                contentType(ContentType.Application.Json)
//                header(
//                    "Authorization", "Bearer " + currentChatDBModel.value!!.token
//                )
//                setBody(
//                    ChatRequestModel(
//                        arrayListOf(
//                            ChatMessageModel(
//                                ChatRole.SYSTEM, "你是一个没用的机器人,回答我的问题"
//                            ), ChatMessageModel(
//                                ChatRole.USER, "你是傻子吗"
//                            )
//                        ),
//                        temperature = currentChatDBModel.value!!.requestModel.temperature,
//                        model = currentChatDBModel.value!!.requestModel.model
//                    )
//                )
//            }
//            if (response.status.value == 200) {
//                val model: ChatResponseModel = response.body()
//                messages.add(model.choices[0].message)
//            }
//        }
//    }) {
//        Text("网络测试")
//    }
//    Text(messages.joinToString())
//}