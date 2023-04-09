package com.arnoract.projectx.ui.util

import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.reader.ReaderSettingUtil
import com.arnoract.projectx.ui.reader.model.ReaderSetting
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties

@Composable
fun StructureSentenceDialog(
    readerSetting: ReaderSetting?,
    url: String,
    onClickedDismiss: () -> Unit
) {
    BottomSheetDialog(
        onDismissRequest = {
            onClickedDismiss()
        },
        properties = BottomSheetDialogProperties(
            dismissWithAnimation = true,
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            behaviorProperties = BottomSheetBehaviorProperties(state = BottomSheetBehaviorProperties.State.Expanded)
        ),
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(ReaderSettingUtil.getBackgroundModalColor(readerSetting?.backgroundMode))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.structure_sentence_label),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        color = ReaderSettingUtil.getFontColor(value = readerSetting?.backgroundMode)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_cross),
                        contentDescription = null,
                        Modifier
                            .size(18.dp)
                            .clickable {
                                onClickedDismiss()
                            },
                        colorFilter = ColorFilter.tint(ReaderSettingUtil.getDrawableTint(value = readerSetting?.backgroundMode))
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colorResource(id = R.color.gray300))
                )
                Spacer(modifier = Modifier.height(16.dp))
                WebViewScreen(url)
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

@Composable
fun WebViewScreen(htmlString: String) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    AndroidView(factory = { webView }) {
        webView.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", null)
    }
}