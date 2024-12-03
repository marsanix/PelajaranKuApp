package com.marsanix.pelajarankuapp

sealed class Screen (val route: String){
    data object Home : Screen("home")
    data object Materi: Screen("materi")
    data object MateriDetail: Screen("materi_detail")
    data object Guru: Screen("guru")
}