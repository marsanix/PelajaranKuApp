package com.marsanix.pelajarankuapp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@SuppressLint("UseOfNonLambdaOffsetOverload", "UnrememberedMutableState")
@Composable
fun MateriDetailScreen(navController: NavController, slug: String) {

    fun getSelectedItem(slug: String): SideBarNavigationItem? {
        return SideBarNavigationItem.entries.find { it.slug == slug }
    }

    val selectedNavigationItem by remember { mutableStateOf(getSelectedItem(slug)) }
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val materiViewModel: MateriViewModel = viewModel()
    val materi by materiViewModel.materi.observeAsState()

    LaunchedEffect(slug) {
        materiViewModel.getMateri(slug)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 65.dp, horizontal = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        selectedNavigationItem?.let {

            if (materi == null) {
                CircularProgressIndicator()
                materiViewModel.insert(
                    MateriEntity(
                        materiId = null,
                        title = it.title,
                        icon = it.icon,
                        slug = it.slug,
                        content = it.content,
                        youtube = it.youtube,
                        isOpened = true
                    )
                )

                materiViewModel.upsertSetting(
                    MateriSettingEntity(
                        "materi_terakhir_dilihat",
                        it.slug
                    )
                )

//                Toast.makeText(
//                    LocalContext.current,
//                    "Materi telah di tandai",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
                    .requiredWidth(screenWidth)
                    .zIndex(1f)
                    .graphicsLayer {
                        translationY = scrollState.value.toFloat() // Offset based on scroll
                    }
            ) {
                if(it.youtube.isNotEmpty()) {
                    YoutubePlayer(
                        youtubeVideoId = it.youtube,
                        lifecycleOwner = LocalLifecycleOwner.current
                    )
                }
            }

            // title
            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 30.dp),
                text = it.title,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
            )

            // content body
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 24.dp),
                text = it.content,
                textAlign = TextAlign.Unspecified,
            )
        }
    }
}
