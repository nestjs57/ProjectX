package com.arnoract.projectx.ui.reading.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties

@Composable
fun BottomDialogArticleSetting(
    title: String,
    id: String,
    onClickedDismiss: () -> Unit,
    onClickedRemoveArticle: (String) -> Unit
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
                .background(colorResource(id = R.color.white))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        overflow = TextOverflow.Ellipsis,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_cross),
                        contentDescription = null,
                        Modifier
                            .size(18.dp)
                            .clickable {
                                onClickedDismiss()
                            },
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.gray500))
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_bin),
                        contentDescription = null,
                        Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onClickedRemoveArticle(id)
                            },
                        text = stringResource(id = R.string.remove_from_reading_article_label),
                        fontSize = 18.sp,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}