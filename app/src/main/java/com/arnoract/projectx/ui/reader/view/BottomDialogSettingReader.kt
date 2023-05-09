package com.arnoract.projectx.ui.reader.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.reader.ReaderSettingUtil.getAppBarColor
import com.arnoract.projectx.ui.reader.ReaderSettingUtil.getBackgroundModalColor
import com.arnoract.projectx.ui.reader.ReaderSettingUtil.getDrawableTint
import com.arnoract.projectx.ui.reader.ReaderSettingUtil.getFontColor
import com.arnoract.projectx.ui.reader.model.ReaderSetting
import com.arnoract.projectx.ui.reader.model.SettingBackground
import com.arnoract.projectx.ui.reader.model.SettingFontSize
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties

@Composable
fun BottomDialogSettingReader(
    readerSetting: ReaderSetting?,
    onClickedDismiss: () -> Unit,
    onClickedTextSize: (SettingFontSize) -> Unit,
    onClickedBackgroundModel: (SettingBackground) -> Unit
) {
    BottomSheetDialog(
        onDismissRequest = {
            onClickedDismiss()
        },
        properties = BottomSheetDialogProperties(dismissWithAnimation = true),
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(getBackgroundModalColor(readerSetting?.backgroundMode))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.setting_reading_label),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        color = getFontColor(value = readerSetting?.backgroundMode)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_cross),
                        contentDescription = null,
                        Modifier
                            .size(18.dp)
                            .clickable {
                                onClickedDismiss()
                            },
                        colorFilter = ColorFilter.tint(getDrawableTint(value = readerSetting?.backgroundMode))
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
                TextSizeContent(readerSetting, onClickedTextSize)
                Spacer(modifier = Modifier.height(16.dp))
//                SpaceBetween()
//                Spacer(modifier = Modifier.height(16.dp))
                BackgroundContent(readerSetting, onClickedBackgroundModel)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun TextSizeContent(
    readerSetting: ReaderSetting?, onClickedTextSize: (SettingFontSize) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_text),
            modifier = Modifier.size(21.dp),
            contentDescription = null,
            colorFilter = ColorFilter.tint(getDrawableTint(value = readerSetting?.backgroundMode))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.text_size_label),
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            color = getFontColor(value = readerSetting?.backgroundMode)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        val isSmall = readerSetting?.fontSizeMode == SettingFontSize.SMALL
        val isNormal = readerSetting?.fontSizeMode == SettingFontSize.NORMAL
        val isLarge = readerSetting?.fontSizeMode == SettingFontSize.LARGE

        Button(
            border = BorderStroke(
                if (isSmall) 2.dp else 1.dp,
                colorResource(id = if (isSmall) R.color.primary_red else R.color.gray500)
            ),
            shape = RoundedCornerShape(10),
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(getAppBarColor(value = readerSetting?.backgroundMode)),
            elevation = ButtonDefaults.elevation(0.dp),
            onClick = {
                onClickedTextSize(SettingFontSize.SMALL)
            },
        ) {
            Text(
                stringResource(id = R.string.small_label),
                color = getFontColor(value = readerSetting?.backgroundMode)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Button(
            border = BorderStroke(
                if (isNormal) 2.dp else 1.dp,
                colorResource(id = if (isNormal) R.color.primary_red else R.color.gray500)
            ),
            shape = RoundedCornerShape(10),
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(getAppBarColor(value = readerSetting?.backgroundMode)),
            elevation = ButtonDefaults.elevation(0.dp),
            onClick = {
                onClickedTextSize(SettingFontSize.NORMAL)
            },
        ) {
            Text(
                stringResource(id = R.string.normal_label),
                color = getFontColor(value = readerSetting?.backgroundMode)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Button(
            border = BorderStroke(
                if (isLarge) 2.dp else 1.dp,
                colorResource(id = if (isLarge) R.color.primary_red else R.color.gray500)
            ),
            shape = RoundedCornerShape(10),
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(getAppBarColor(value = readerSetting?.backgroundMode)),
            elevation = ButtonDefaults.elevation(0.dp),
            onClick = {
                onClickedTextSize(SettingFontSize.LARGE)
            },
        ) {
            Text(
                stringResource(id = R.string.large_label),
                color = getFontColor(value = readerSetting?.backgroundMode)
            )
        }
    }
}

@Composable
private fun BackgroundContent(
    readerSetting: ReaderSetting?, onClickedBackgroundModel: (SettingBackground) -> Unit
) {
    val isDay = readerSetting?.backgroundMode == SettingBackground.DAY
    val isNight = readerSetting?.backgroundMode == SettingBackground.NIGHT

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_background),
            modifier = Modifier.size(21.dp),
            contentDescription = null,
            colorFilter = ColorFilter.tint(getDrawableTint(value = readerSetting?.backgroundMode))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.color_background_label),
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            color = getFontColor(value = readerSetting?.backgroundMode)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedButton(
            border = BorderStroke(
                if (isDay) 2.dp else 1.dp,
                colorResource(id = if (isDay) R.color.primary_red else R.color.gray500)
            ),
            shape = RoundedCornerShape(10),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.only_white)),
            modifier = Modifier.weight(1f),
            onClick = {
                onClickedBackgroundModel(SettingBackground.DAY)
            },
        ) {
            Text(
                text = stringResource(id = R.string.day_label),
                color = colorResource(id = R.color.only_black)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Button(
            border = BorderStroke(
                if (isNight) 2.dp else 1.dp,
                colorResource(id = if (isNight) R.color.primary_red else R.color.background_dark_mode)
            ),
            shape = RoundedCornerShape(10),
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.background_dark_mode)),
            elevation = ButtonDefaults.elevation(0.dp),
            onClick = {
                onClickedBackgroundModel(SettingBackground.NIGHT)
            },
        ) {
            Text(
                stringResource(id = R.string.night_label),
                color = colorResource(id = R.color.only_white)
            )
        }
    }
}