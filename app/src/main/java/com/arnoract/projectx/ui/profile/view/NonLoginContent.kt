package com.arnoract.projectx.ui.profile.view

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.profile.ProfileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun NonLoginContent() {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "เกี่ยวกับเรา",
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
                text = "ติดต่อเรา",
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
                text = "ข้อความจากนักเขียน",
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

        val context = LocalContext.current
        val token = stringResource(R.string.default_web_client_id)
        val launcher = registerGoogleActivityResultLauncher()

        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .clickable {
                    val signInOptions =
                        GoogleSignInOptions
                            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(token)
                            .requestEmail()
                            .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, signInOptions)
                    launcher.launch(googleSignInClient.signInIntent)
                }
                .background(colorResource(id = R.color.purple_500)),
            contentAlignment = Alignment.Center
        ) {
            Row() {
                Text(
                    text = "",
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.white)
                )
                Text(
                    text = "เข้าสู่ระบบด้วย",
                    modifier = Modifier,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.white),
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp), contentAlignment = Alignment.CenterStart
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        modifier = Modifier
                            .size(21.dp)
                            .align(Alignment.CenterStart),
                        contentDescription = null,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "v.1.0.0",
            fontSize = 18.sp,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun registerGoogleActivityResultLauncher(): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            //viewModel.signInWithGoogleToken(account.idToken ?: "")
        } catch (e: ApiException) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun NonLoginContentPreview() {
    NonLoginContent()
}