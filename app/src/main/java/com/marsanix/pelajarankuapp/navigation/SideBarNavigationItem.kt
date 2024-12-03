package com.marsanix.pelajarankuapp

import android.annotation.SuppressLint


enum class SideBarNavigationItem(
    val title: String,
    val icon: Int,
    val slug: String = "",
    val content: String = "",
    val youtube: String = ""
) {

    // Pengantar jangan di hapus dan jangan diubah
    @SuppressLint("ResourceType")
    Pengantar(
        icon = R.drawable.baseline_menu_book_24,
        title = "Pengantar",
        slug = "pengantar",
        content = """
            Selamat datang di dunia pemrograman Kotlin! 🌟
          
            Kotlin adalah bahasa pemrograman modern yang dikembangkan oleh JetBrains, dirancang untuk menjadi jelas, ringkas, dan aman. Sejak diperkenalkan, Kotlin telah mendapatkan popularitas yang pesat, terutama karena kemampuannya untuk bekerja mulus dengan Java, serta dukungan resminya dari Google untuk pengembangan aplikasi Android.

            Belajar Kotlin memberikan Anda keuntungan untuk menulis kode yang lebih bersih dan lebih mudah dipelihara. Dengan sintaks yang intuitif dan fitur-fitur canggih seperti null safety, extension functions, dan coroutines, Kotlin membantu pengembang menciptakan aplikasi yang lebih efisien dan andal.

            Dalam perjalanan pembelajaran ini, Anda akan mempelajari:

            Dasar-Dasar Kotlin: Sintaks dasar, tipe data, dan kontrol alur.

            Pemrograman Berorientasi Objek: Kelas, objek, pewarisan, dan polymorphism.

            Fitur Lanjutan: Higher-order functions, lambdas, dan coroutines.

            Pengembangan Android: Membangun aplikasi Android menggunakan Kotlin, mulai dari dasar hingga fitur-fitur lanjutan.

            Mari kita mulai petualangan ini dan menjelajahi potensi luar biasa yang ditawarkan oleh Kotlin. Bersiaplah untuk mengasah keterampilan Anda dan menciptakan kode yang indah serta berfungsi dengan optimal. Selamat belajar, dan semoga sukses! 🚀
        """.trimIndent(),

    ),
    Materi1(
        icon = R.drawable.baseline_menu_book_24,
        title = "1. Dasar-dasar Kotlin",
        slug = "dasar-dasar-kotlin",
        youtube = "B8tKvlpCHh8",
        content = """
            Dasar-Dasar Kotlin Singkat
            
            Kotlin adalah bahasa pemrograman modern dari JetBrains yang populer untuk pengembangan aplikasi Android karena interoperabilitasnya dengan Java dan sintaks yang bersih.
            
            Struktur Dasar
            Program Kotlin dimulai dengan fungsi main:

            fun main() {
                println("Hello, Kotlin!")
            }
            
            Tipe Data
            Deklarasi variabel menggunakan val (immutable) dan var (mutable):
     
            val name: String = "John"
            var age: Int = 25
            
            Fungsi
            Mendefinisikan fungsi dengan fun:

            fun add(a: Int, b: Int) = a + b
            
            Kontrol Alur
            Pernyataan if-else, when, for, dan while:

            val max = if (a > b) a else b
            
            when (x) {
                1 -> println("One")
                2 -> println("Two")
                else -> println("Other")
            }
            
            for (i in 1..5) println(i)
            
            while (i <= 5) {
                println(i)
                i++
            }
            
            Null Safety
            Menghindari NullPointerException dengan null safety:

            var name: String? = "Kotlin"
            name = null
            println(name?.length)
            
            Classes dan Objects
            Mendefinisikan kelas dan objek:

            class Person(val name: String, var age: Int)
            
            val person = Person("John Doe", 25)
            println("Name: '$'{person.name}, Age: '$'{person.age}")
            
            Collections
            Menggunakan koleksi seperti List, Set, dan Map:

            val numbers = listOf(1, 2, 3, 4, 5)
            val user = mapOf("name" to "John Doe", "age" to 25)
            
            Selamat menjelajah dan belajar Kotlin! 🚀😊
        """.trimIndent()
    ),
    Materi2(
        icon = R.drawable.baseline_menu_book_24,
        title = "2. Menulis Kondisional di Kotlin",
        slug = "menulis-kondisional-di-kotlin",
        youtube = "R15LROrkoiM",
        content = ""
    ),
    Materi3(
        icon = R.drawable.baseline_menu_book_24,
        title = "3. Menggunakan nullability di Kotlin",
        slug = "menggunakan-nullability-di-kotlin",
        youtube = "aZiER2bnNX4",
        content = ""
    ),

//    Materi4(
//        icon = R.drawable.baseline_menu_book_24,
//        title = "4. Menggunakan class dan object di Kotlin"
//    ),
//    Materi5(
//        icon = R.drawable.baseline_menu_book_24,
//        title = "5. Menggunakan jenis fungsi dan ekspresi lambda di Kotlin"
//    ),
//    Materi6(
//        icon = R.drawable.baseline_menu_book_24,
//        title = "6. Praktik: Dasar-dasar Kotlin"
//    ),


    // Setting jangan dihapus dan jangan diubah
    Setting(
        icon = R.drawable.baseline_settings_24,
        title = "Setting"
    )

}