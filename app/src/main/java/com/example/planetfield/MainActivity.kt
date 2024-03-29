package com.example.planetfield

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var startPage : StartPage
    private lateinit var planetDGW : DGW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startPage = StartPage(this)
        setContentView(startPage)
    }
}