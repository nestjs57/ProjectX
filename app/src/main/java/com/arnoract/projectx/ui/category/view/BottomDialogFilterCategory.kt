package com.arnoract.projectx.ui.category.view

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.category.model.UiCategoryFilter
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties

@Composable
fun BottomDialogFilterCategory(
    filter: UiCategoryFilter?,
    onClickedDismiss: () -> Unit,
    onClickedFilter: (UiCategoryFilter) -> Unit
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
                        text = stringResource(id = R.string.filter_label),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
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
                Row {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onClickedFilter(UiCategoryFilter.TOTAL)
                            },
                        text = stringResource(id = R.string.total_label),
                        fontSize = 18.sp,
                    )
                    if (filter == UiCategoryFilter.TOTAL) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_check_circle),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(colorResource(id = R.color.green_sukhumvit))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onClickedFilter(UiCategoryFilter.READING)
                            },
                        text = stringResource(id = R.string.reading_label),
                        fontSize = 18.sp,
                    )
                    if (filter == UiCategoryFilter.READING) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_check_circle),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(colorResource(id = R.color.green_sukhumvit))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onClickedFilter(UiCategoryFilter.NEVER_READ)
                            },
                        text = stringResource(id = R.string.never_read_label),
                        fontSize = 18.sp,
                    )
                    if (filter == UiCategoryFilter.NEVER_READ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_check_circle),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(colorResource(id = R.color.green_sukhumvit))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}