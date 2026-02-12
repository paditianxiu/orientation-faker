package me.padi.orientationfaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import me.padi.orientationfaker.control.Functions
import me.padi.orientationfaker.service.OrientationService
import me.padi.orientationfaker.ui.theme.Theme
import top.yukonga.miuix.kmp.basic.Button
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.FabPosition
import top.yukonga.miuix.kmp.basic.FloatingActionButton
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.IconButton
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.extra.LocalWindowDialogState
import top.yukonga.miuix.kmp.extra.WindowDialog
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.extended.Pause
import top.yukonga.miuix.kmp.icon.extended.Settings
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.PressFeedbackType
import top.yukonga.miuix.kmp.utils.overScrollVertical

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val scrollBehavior = MiuixScrollBehavior()
            val context = LocalContext.current
            val showDialog = remember { mutableStateOf(false) }
            Theme {
                Scaffold(
                    topBar = {
                    TopAppBar(
                        scrollBehavior = scrollBehavior,
                        title = stringResource(R.string.app_name),
                        navigationIcon = {
                            AboutDialog()
                        },
                        actions = {
                            IconButton(onClick = {

                            }) {
                                Icon(MiuixIcons.Settings, contentDescription = "更多")
                            }
                            Spacer(Modifier.width(8.dp))
                        })

                },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                OrientationService.stop(context)
                            },
                            minWidth = 120.dp,
                        ) {
                            Row {
                                Icon(
                                    imageVector = MiuixIcons.Pause,
                                    contentDescription = "关闭服务",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                                Spacer(Modifier.width(8.dp))
                                Text("停止", color = MaterialTheme.colorScheme.onPrimary)

                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier
                            .fillMaxSize()
                            .overScrollVertical()
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                        contentPadding = PaddingValues(
                            top = paddingValues.calculateTopPadding() + 20.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        ),
                        horizontalArrangement = Arrangement.spacedBy(8.dp), // 添加间距
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(Functions.orientations) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                pressFeedbackType = PressFeedbackType.Sink,
                                showIndication = true,
                                onClick = {
                                    if (checkPermission(context)) {
                                        OrientationService.start(context, item.function.orientation)
                                    } else {
                                        showDialog.value = true
                                    }
                                },
                                cornerRadius = 12.dp,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        painter = painterResource(item.icon),
                                        contentDescription = stringResource(item.label),
                                        modifier = Modifier.size(24.dp),
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = stringResource(item.label),
                                        style = MaterialTheme.typography.labelSmall,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    WindowDialog(
                        title = "权限申请",
                        summary = "要使用这个应用\n需要允许\"在其它应用上显示\"权限.",
                        show = showDialog,
                        onDismissRequest = { showDialog.value = false }) {
                        val dismiss = LocalWindowDialogState.current
                        Spacer(Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextButton(
                                modifier = Modifier.weight(1f),
                                text = "取消",
                                onClick = { dismiss.invoke() })
                            Spacer(Modifier.width(16.dp))
                            Button(
                                modifier = Modifier.weight(1f), onClick = {
                                    dismiss.invoke()
                                    requestPermission(context)
                                }, colors = ButtonDefaults.buttonColorsPrimary()
                            ) {
                                Text("确定", color = MiuixTheme.colorScheme.onPrimary)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }


    }

    fun checkPermission(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

    fun requestPermission(context: Context) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION, "package:${context.packageName}".toUri()
        )
        context.startActivity(intent)

    }
}