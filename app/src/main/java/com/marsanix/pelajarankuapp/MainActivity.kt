package com.marsanix.pelajarankuapp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.marsanix.pelajarankuapp.ui.theme.PelajaranKuAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

const val APP_NAME: String = "PelajaranKu"

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()

        // menonaktifkan auto rotate screen
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            PelajaranKuAppTheme {

                val navController: NavHostController = rememberNavController()

                Surface(
                    color = MaterialTheme.colorScheme.background) {
                    Greeting(navController)
                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting(navController: NavHostController = rememberNavController()) {
    PelajaranKuApp(navController)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PelajaranKuAppTheme {
        Greeting()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable

fun PelajaranKuApp(navController: NavHostController = rememberNavController()) {

    /* Start for SideBar */

    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
    var selectedNavigationItem by remember { mutableStateOf(SideBarNavigationItem.Pengantar) }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density

    val screenWidth = remember {
        derivedStateOf { (configuration.screenWidthDp * density).roundToInt() }
    }
    val offsetValue by remember { derivedStateOf { (screenWidth.value / 4.2).dp } }
    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
        label = "Animated Offset"
    )
    val animatedScale by animateFloatAsState(
        targetValue = if (drawerState.isOpened()) 0.9f else 1f,
        label = "Animated Scale"
    )

    BackHandler(enabled = drawerState.isOpened()) {
        drawerState = CustomDrawerState.Closed
    }

    /* End for SideBar */


    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        CustomDrawer(
            selectedNavigationItem = selectedNavigationItem,
            onNavigationItemClick = {
                selectedNavigationItem = it

                navController.navigate("materi_detail/${it.slug}")
                drawerState = CustomDrawerState.Closed

            },
            onCloseClick = { drawerState = CustomDrawerState.Closed }
        )
        MainContent(
            modifier = Modifier
                .offset(x = animatedOffset)
                .scale(scale = animatedScale)
                .coloredShadow(
                    color = Color.Black,
                    alpha = 0.1f,
                    shadowRadius = 50.dp
                ),
            drawerState = drawerState,
            onDrawerClick = { drawerState = it },
            navController
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateTime(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    return current.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    drawerState: CustomDrawerState,
    onDrawerClick: (CustomDrawerState) -> Unit,
    navController: NavHostController = rememberNavController(),
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val materiViewModel: MateriViewModel = viewModel()
    materiViewModel.upsertSetting(
        MateriSettingEntity(
            "aktivitas_terakhir",
            getCurrentDateTime()
        )
    )


    Scaffold(
        modifier = modifier
            .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                onDrawerClick(CustomDrawerState.Closed)
            },
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text(text = APP_NAME, color = Color.White) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Black // Set the background color to the primary theme color
                ),
                navigationIcon = {
                    IconButton(onClick = { onDrawerClick(drawerState.opposite()) }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Icon",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {

            FloatingActionButton(
                modifier = Modifier
                    .scale(1.1f, 1.1f)
                    .offset(0.dp, 35.dp),
                onClick = {
                    navController.navigate("materi")
                },
                containerColor = if (currentRoute == "materi") Color.DarkGray else Color.Black,
                shape = CircleShape,
            ) {
                Icon(painterResource(R.drawable.baseline_menu_book_24), tint = Color.White, contentDescription = "")
            }

        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBar(
                cutoutShape = RoundedCornerShape(50),
                backgroundColor = Color.Black,
                content = {
                    BottomNavigation(
                        backgroundColor = Color.Black
                    ) {
                        BottomNavigationItem(

                            selected = currentRoute == "home",
                            onClick = {
                                navController.navigate("home")
                            },
                            icon = {
                                Icon(
                                    Icons.Filled.Home,
                                    contentDescription = "home",
                                    tint = Color.White
                                )
                            },
                            label = { Text(text = "Home", color = Color.White) },
                            selectedContentColor = Color.White,
                            alwaysShowLabel = false,
                        )

                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .scale(1.6f, 1.6f)
                                        .offset(0.dp, (-21).dp)
                                    ,
                                    imageVector =  Icons.Filled.AddCircle,
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            },
                            selected = currentRoute == "materi",
                            onClick = {

                                // content.value = "Halaman Materi"
                                // selectedItem.value = "materi"
                            }

                        )

                        BottomNavigationItem(
                            selected = currentRoute == "guru",
                            onClick = {
                                navController.navigate("guru")
                            },
                            icon = {
                                Icon(
                                    Icons.Filled.Person,
                                    contentDescription = "guru",
                                    tint = Color.White
                                )
                            },
                            label = { Text(text = "Guru", color = Color.White) },
                            selectedContentColor = Color.White,
                            alwaysShowLabel = false
                        )
                    }
                }
            )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
            ) {

                composable(Screen.Home.route) {
                    HomeScreen(navController)
                }

                composable(Screen.Materi.route) {
                    MateriScreen(navController)
                }

                composable(Screen.MateriDetail.route + "/{slug}") { backStackEntry ->
                    val slug = backStackEntry.arguments?.getString("slug")
                    if (slug != null) {
                        MateriDetailScreen(navController, slug)
                    }
                }

                composable(Screen.Guru.route) {
                    GuruScreen(navController)
                }

            }

        }
    }
}