package com.marsanix.pelajarankuapp

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.marsanix.pelajarankuapp.ui.theme.PelajaranKuAppTheme


class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()

        // menonaktifkan auto rotate screen
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false }

        setContent {
            PelajaranKuAppTheme {
                Greeting2()
            }
        }
    }
}

@Composable
fun Greeting2() {
    val context = LocalContext.current
    val sharedPreferencesManager = remember {
        SharedPreferencesManager(context)
    }
    var name by remember { mutableStateOf(sharedPreferencesManager.userName) }

    LaunchedEffect(sharedPreferencesManager) {
        if(sharedPreferencesManager.isSubmittedName) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Transparent
                    ),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            ),
    ) {

        Image(
            painter = painterResource(id = R.drawable.onboarding_bg),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = -((screenHeight.dp / 2) / 2) + 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                //.background(Color(0x63000000), shape = RoundedCornerShape(15.dp))
                //.border(1.dp, Color.White, RoundedCornerShape(15.dp))
                shape = RoundedCornerShape(30.dp),
                onValueChange = {
                    name = it
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color(0xFFD0D0D0)
                ),
                value = name?:"",
                textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                label = { Text(text = "Masukkan nama Anda!", color = Color.White) },
            )

            Button(onClick = {

                sharedPreferencesManager.userName = name
                sharedPreferencesManager.isSubmittedName = true
                context.startActivity(Intent(context, MainActivity::class.java))

            }, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 40.dp, end = 40.dp)) {
                Text(text = "Lanjutkan!")
            }

        }



    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    PelajaranKuAppTheme {
        Greeting2()
    }
}