package com.arnoract.projectx.ui.profile.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.arnoract.projectx.R

@Composable
fun InformationContent() {
    Column {
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_id_card),
                    modifier = Modifier.size(17.dp),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.about_us_label),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
        )
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/profile.php?id=100090905786664")
            )
            val context = LocalContext.current

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_facebook),
                    modifier = Modifier.size(17.dp),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.contact_us_label),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .clickable {
                            startActivity(context, intent, null)
                        },
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Spacer(
            modifier = Modifier
                .background(colorResource(id = R.color.white))
                .height(1.dp)
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_mail_dot),
                    modifier = Modifier.size(17.dp),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.message_from_writer),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .background(colorResource(id = R.color.white))
                .height(1.dp)
                .fillMaxWidth()
        )
    }
}