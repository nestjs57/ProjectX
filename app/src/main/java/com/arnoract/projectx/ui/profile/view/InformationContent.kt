package com.arnoract.projectx.ui.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R

@Composable
fun InformationContent() {
    Column() {
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(id = R.string.about_us_label),
                fontSize = 18.sp,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.gray300))
        )
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(id = R.string.contact_us_label),
                fontSize = 18.sp,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(
            modifier = Modifier
                .background(colorResource(id = R.color.gray300))
                .height(1.dp)
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(id = R.string.message_from_writer),
                fontSize = 18.sp,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .background(colorResource(id = R.color.gray300))
                .height(1.dp)
                .fillMaxWidth()
        )
    }
}