package com.marsanix.pelajarankuapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {

    val materiViewModel: MateriViewModel = viewModel()
    val materiSetting by  materiViewModel.materiSetting.observeAsState()
    val lastActivity by materiViewModel.lastActivity.observeAsState()

    LaunchedEffect(lastActivity) {
        materiViewModel.getLastActivity()
    }

    LaunchedEffect(materiSetting) {
        materiViewModel.getMateriSetting("materi_terakhir_dilihat")
    }

    Column(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()
        .padding(top = 70.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

//        val images = listOf("https://media.npr.org/assets/img/2021/08/11/gettyimages-1279899488_wide-f3860ceb0ef19643c335cb34df3fa1de166e2761-s1100-c50.jpg",
//            "https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492__480.jpg",
//            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrfPnodZbEjtJgE-67C-0W9pPXK8G9Ai6_Rw&usqp=CAU",
//            "https://i.ytimg.com/vi/E9iP8jdtYZ0/maxresdefault.jpg",
//            "https://comdn.shopify.com/s/files/1/0535/2738/0144/articles/shutterstock_149121098_360x.jpg")

        val images = listOf(
            R.drawable.slide1,
            R.drawable.slide2,
            R.drawable.slide3
        )

        ImageSlider(images, materiSetting, lastActivity)

    }

}

@Composable
fun ImageSlider(images: List<Any>, materiSetting: MateriSettingEntity?, materiSettingLastLogin: MateriSettingEntity?) {
    var currentImageIndex by remember { mutableIntStateOf(0) }
    var isAnimating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val sharedPreferencesManager = remember {
        SharedPreferencesManager(context)
    }

    var yourName by remember { mutableStateOf(sharedPreferencesManager.userName) }
    LaunchedEffect(sharedPreferencesManager.userName) {
        yourName = sharedPreferencesManager.userName
    }

    LaunchedEffect(currentImageIndex) {
        while (true) {
            delay(5000L)
            if (!isAnimating) {
                val nextIndex = (currentImageIndex + 1) % images.size
                currentImageIndex = nextIndex
            }
        }

    }

    Column(modifier = Modifier.fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
        ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .align(Alignment.CenterHorizontally)
        ) {
            // Scrollable Row of Cards
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(images) { index, image ->
                    Card(
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp)
                            .clickable {
                                if (index != currentImageIndex && !isAnimating) {
                                    isAnimating = true
                                    coroutineScope.launch {
                                        val delayMillis = 500L
                                        // Wait for the card to change color before animating
                                        delay(delayMillis / 2)
                                        currentImageIndex = index
                                        delay(delayMillis)
                                        isAnimating = false
                                    }
                                }
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = image),
                            contentDescription = "My Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .width(300.dp)
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )

                    }
                }

            }

        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .graphicsLayer {
                    shadowElevation = 2f
                    shape = RoundedCornerShape(16.dp)
                    clip = true
                }
                .drawWithContent {
                    drawContent()
                    drawCircle(
                        color = Color.LightGray.copy(alpha = 0.25f),
                        radius = 420f,
                        center = Offset(size.width - 50, size.height -480 )
                    )

                }
            ,
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF673AB7))
        ) {
            Row {

                Icon(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 0.dp, bottom = 16.dp),
                    painter = painterResource(R.drawable.baseline_person_24),
                    contentDescription = "",
                    tint = Color.White
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selamat Datang, ${yourName}!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Aktivitas terakhir: ${materiSettingLastLogin?.value}",
                        fontSize = 14.sp,
                        color = Color(0xFFDCDCDC)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .graphicsLayer {
                        shadowElevation = 2f
                        shape = RoundedCornerShape(16.dp)
                        clip = true
                    }
                    .drawWithContent {
                        drawContent()
                        drawCircle(
                            color = Color.LightGray.copy(alpha = 0.25f),
                            radius = 450f,
                            center = Offset(size.width +90, size.height -600 )
                        )

                    }

                ,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF03A9F4))

            ) {

                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 55.dp)
                            .wrapContentSize(),
                    ) {

                        Row {
                            Icon(
                                modifier = Modifier.padding(end = 4.dp),
                                painter = painterResource(R.drawable.baseline_menu_book_24),
                                contentDescription = "",
                                tint = Color.White
                            )
                            Text("Materi selanjutnya:", fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        Column(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .offset(0.dp, 45.dp)
                                .padding(4.dp)
                        ) {

                            materiSetting?.let {
                                val materiSelanjutnya = getNextItem(it.value)
                                val materiTerakhir = getSelectedItem(it.value)
                                if(materiSelanjutnya == materiTerakhir) {
                                    Text(text = "Terima kasih! Anda sudah menyelesaikan seluruh materi.", color = Color.White)
                                } else {
                                    Text(text = materiSelanjutnya.title, color = Color.White)
                                }
                            } ?: run {
                                Text(text ="Silahkan masuk ke halaman Materi!", color = Color.White)
                            }

                        }

                    }

            }

            Spacer(modifier = Modifier.width(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .graphicsLayer {
                        shadowElevation = 2f
                        shape = RoundedCornerShape(16.dp)
                        clip = true
                    }
                    .drawWithContent {
                        drawContent()
                        drawCircle(
                            color = Color.LightGray.copy(alpha = 0.25f),
                            radius = 450f,
                            center = Offset(size.width +90, size.height -600 )
                        )

                    }
                ,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50))
            ) {

                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 55.dp)
                        .wrapContentSize()

                    ,
                ) {

                    Row {
                        Icon(
                            modifier = Modifier.padding(end = 4.dp),
                            painter = painterResource(R.drawable.baseline_history_24),
                            contentDescription = "",
                            tint = Color.White
                        )
                        Text("Materi terlahir dilihat:", fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(0.dp, 45.dp)
                            .padding(4.dp)
                    ) {

                        materiSetting?.let {
                            val materiTerakhir = getSelectedItem(it.value)
                            if (materiTerakhir != null) {
                                Text(text = materiTerakhir.title, color = Color.White)
                            } else {
                                Text(text ="Belum ada materi yang di buka.", color = Color.White)
                            }
                        } ?: run {
                            Text(text ="Belum ada materi yang di buka.", color = Color.White)
                        }

                    }

                }

            }
        }

    }
}

