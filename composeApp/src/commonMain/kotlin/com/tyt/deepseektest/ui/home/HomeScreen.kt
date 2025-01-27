package com.tyt.deepseektest.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.tyt.deepseektest.DataHolder
import com.tyt.deepseektest.DataHolder.ChatList
import com.tyt.deepseektest.data.ChatRole
import com.tyt.deepseektest.data.Database
import com.tyt.deepseektest.data.SendStatus
import com.tyt.deepseektest.data.db.ChatDBModel
import com.tyt.deepseektest.getPlatform
import com.tyt.deepseektest.utils.SimpleHolder
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun homeScreen(
    scaffoldNavigator: ThreePaneScaffoldNavigator<Nothing>,
    showAdd: MutableState<Boolean>,
    currentChatDBModel: MutableState<ChatDBModel?>,
    onBackIntercept: State<SimpleHolder<() -> Boolean>>,
) {
    val coroutineScope = rememberCoroutineScope()
//    val chatListItems = remember { chatListItems }
//    val chatListItems = remember { mutableStateListOf<ChatListItem>() }
//    chatListItems.add(ChatListItem(ChatListItemType.ADD))
//    chatListItems.addAll(Database.getChats().reversed().map {
//            it->ChatListItem(ChatListItemType.MODEL,it)
//    })
    var refreshListKey by mutableStateOf(false)

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
                    val chatListItems = remember { ChatList }
                    val scrollState = rememberLazyListState()
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.draggable(
                            orientation = Orientation.Vertical,
                            state = rememberDraggableState { delta ->
                                coroutineScope.launch {
                                    scrollState.scrollBy(-delta)
                                }
                            })
                    ) {
                        items(chatListItems.size + 1) { index ->
                            //第一个固定为添加
                            when (index) {
                                0 -> {
                                    Box(contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxWidth().heightIn(120.dp, Dp.Infinity).clickable {
                                            coroutineScope.launch {
                                                if (scaffoldNavigator.currentDestination?.pane != ListDetailPaneScaffoldRole.Detail || !showAdd.value) {
                                                    scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                                                    showAdd.value = true
                                                }
                                            }
                                        }) {
                                        Icon(Icons.Default.Add, contentDescription = "add")
                                    }
                                }

                                else -> {
                                    key(refreshListKey) {
                                        val item = chatListItems[index - 1]
                                        val title = item.title.ifBlank {
                                            val firstUserMessage =
                                                item.requestModel.messages.firstOrNull { it.role == ChatRole.USER }
                                            firstUserMessage?.content ?: "无标题"
                                        }
                                        Box(
                                            modifier = Modifier.padding(horizontal = 12.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Box(
                                                modifier = Modifier.fillMaxWidth().clickable {
                                                    currentChatDBModel.value = item
                                                    showAdd.value = false
                                                    val lastMessage = currentChatDBModel.value!!.requestModel.messages.lastOrNull()
                                                    if (lastMessage!=null && lastMessage.sendStatus==SendStatus.SENDING){
                                                        lastMessage.sendStatus = SendStatus.FAILED
                                                    }
                                                    scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                                                }.border(
                                                    2.dp,
                                                    color = MaterialTheme.colorScheme.outlineVariant,
                                                    RoundedCornerShape(12.dp)
                                                ).padding(12.dp), contentAlignment = Alignment.Center
                                            ) {
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Text(title, maxLines = 3, overflow = TextOverflow.Ellipsis)
                                                    IconButton(onClick = {
                                                        if (currentChatDBModel.value == item) {
                                                            currentChatDBModel.value = null
                                                        }
                                                        Database.deleteChat(item)
                                                        DataHolder.updateChatList()
                                                    }) {
                                                        Icon(Icons.Default.Delete, "delete", tint = Color.Red)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            HorizontalDivider(thickness = 12.dp, color = Color.Transparent)
                        }
                    }
                }
            }
        },
        detailPane = {
            val adaptiveInfo = currentWindowAdaptiveInfo()
            AnimatedPane(modifier = Modifier) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
//                    onClick = { coroutineScope.launch { scaffoldNavigator.navigateBack() } }
                ) {
                    if (showAdd.value) {
//                        Text("adding!!!!!!!!!!!!", modifier = Modifier.clickable {
//                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.List)
//                            showAdd.value = false
//                        })

                        val onBack: (ChatDBModel?) -> Unit = {
                            if (it == null) {
                                scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.List)
                            } else {
                                val item = Database.insertChat(it)
                                it.id = item
                                DataHolder.updateChatList()
                                currentChatDBModel.value = it
                                scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                            }
                            showAdd.value = false
                        }
                        DisposableEffect(Unit) {
                            val previousBackIntercept = onBackIntercept.value.data
                            if (getPlatform().isAndroid() && adaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                                onBackIntercept.value.data = {
                                    val result =
                                        scaffoldNavigator.currentDestination?.pane == ListDetailPaneScaffoldRole.Detail
                                    onBack(null)
                                    result
                                }
                            }
                            onDispose { onBackIntercept.value.data = previousBackIntercept }
                        }
                        createChatPane(onBack)
                    } else {
                        val onBack: () -> Unit = { scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.List) }
                        DisposableEffect(Unit) {
                            val previousBackIntercept = onBackIntercept.value.data
                            if (getPlatform().isAndroid() && adaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                                onBackIntercept.value.data = {
                                    val result =
                                        scaffoldNavigator.currentDestination?.pane == ListDetailPaneScaffoldRole.Detail
                                    onBack()
                                    result
                                }
                            }
                            onDispose { onBackIntercept.value.data = previousBackIntercept }
                        }
                        chatDetailPane(currentChatDBModel, onBack, onBackIntercept) {
                            DataHolder.updateChatList()
                            refreshListKey = !refreshListKey
                        }
                    }
                }
            }
        })
}

