package me.padi.orientationfaker

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.IconButton
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.extra.SuperDialog
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.Platform
import top.yukonga.miuix.kmp.utils.platform

@Composable
fun AboutDialog(
) {
    val showDialog = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    IconButton(
        modifier = Modifier.padding(start = if (platform() != Platform.IOS && platform() != Platform.Android) 10.dp else 20.dp),
        onClick = {
            showDialog.value = true
            focusManager.clearFocus()
        },
        holdDownState = showDialog.value
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "About",
            modifier = Modifier
                .size(50.dp)
                .padding(4.dp),
            colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.primary)
        )
    }

    SuperDialog(
        show = showDialog, title = stringResource(R.string.about), onDismissRequest = {
            showDialog.value = false
        }) {
        val uriHandler = LocalUriHandler.current

        val versionName = BuildConfig.VERSION_NAME
        val versionCode = BuildConfig.VERSION_CODE.toString()

        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Icon",
                modifier = Modifier.size(45.dp),
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.primary)
            )
            Column {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "$versionName ($versionCode)",
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.view_source) + " ",
            )
            Text(
                text = AnnotatedString(
                    text = "GitHub", spanStyle = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = MiuixTheme.colorScheme.primary
                    )
                ), modifier = Modifier.clickable(
                    onClick = {
                        uriHandler.openUri("https://github.com/paditianxiu")
                    })
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.join_channel) + " ",
            )
            Text(
                text = AnnotatedString(
                    text = "Telegram", spanStyle = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = MiuixTheme.colorScheme.primary
                    )
                ), modifier = Modifier.clickable(
                    onClick = {
                        uriHandler.openUri("https://t.me/niubimokuai")
                    },
                )
            )
        }
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = stringResource(R.string.opensource_info)
        )
    }
}