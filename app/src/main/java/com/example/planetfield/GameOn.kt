package com.example.planetfield

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GameOn : AppCompatActivity() {
    private lateinit var alienSolarSystemTouch : AlienSolarSystemTouch
    private lateinit var alienSolarSystemMove : AlienSolarSystemMove
    override protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alienSolarSystemTouch = AlienSolarSystemTouch(this)
        alienSolarSystemMove = AlienSolarSystemMove(this)

        setContentView(alienSolarSystemTouch)
        //setContentView(alienSolarSystemMove)
    }
}