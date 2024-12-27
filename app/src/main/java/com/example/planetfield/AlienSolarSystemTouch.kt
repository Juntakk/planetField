package com.example.planetfield

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.core.view.marginTop
import java.util.Random
import kotlin.math.pow
import kotlin.math.sqrt

class AlienSolarSystemTouch : View {

    private var random : Random
    private var shipPaint : Paint
    private var planetPaint : Paint
    private var spacePaint : Paint

    private var spaceShip : SpaceShip = SpaceShip(182f,1757f)

    private var ship : Bitmap
    private var allPlanets: ArrayList<Planet>
    private var discoveredPlanets: ArrayList<Planet>

    private var alienSolarSystemCtx : Context = this.context
    private var end : Boolean
    private var planetDGW : DGW

    private var viewportWidth: Int = 0
    private var viewportHeight: Int = 0

    init{
        viewportWidth = resources.displayMetrics.widthPixels
        viewportHeight = resources.displayMetrics.heightPixels
    }

    constructor(ctx : Context) : super(ctx){
        end = false
        random = Random()
        shipPaint = Paint()
        planetPaint = Paint()
        spacePaint = Paint()
        shipPaint.isAntiAlias = true
        ship = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.spaceship),150,150,false)

        //DB
        planetDGW = DGW(alienSolarSystemCtx,"planets", null, 1)
        planetDGW.openDB()
        planetDGW.insertPlanets()

        //Planets
        allPlanets = planetDGW.getAllPlanets()
        discoveredPlanets = ArrayList()
        planetDGW.closeDB()

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(BitmapFactory.decodeResource(resources, R.drawable.space2), 0f,0f,spacePaint)
        canvas.drawBitmap(BitmapFactory.decodeResource(resources, R.drawable.space2), 0f,800f,spacePaint)
        canvas.drawBitmap(BitmapFactory.decodeResource(resources, R.drawable.space2), 0f,1600f,spacePaint)

        for (planet in allPlanets){
            val planetBitmap =Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, planet.image_path), planet.size.toInt(),planet.size.toInt(),false)
            canvas.drawBitmap(planetBitmap,planet.positionX.toFloat(), planet.positionY.toFloat(), planetPaint)
        }
        canvas.drawBitmap(ship,spaceShip.x,spaceShip.y, shipPaint)
        for (planet in allPlanets) {
            val distance = sqrt(
                (planet.positionX+planet.size/2 - spaceShip.x-75).toDouble().pow(2.0)
                        + (planet.positionY + planet.size/2 - spaceShip.y-75).toDouble().pow(2.0)
            )

            val threshold = 100

            if(distance <= threshold) {
                val txtView = TextView(alienSolarSystemCtx)
                txtView.text = "${planet.name} | ${planet.size}"

                txtView.setTextColor(Color.WHITE)

                txtView.visibility = VISIBLE

                txtView.measure(MeasureSpec.getSize(1000), MeasureSpec.getSize(1000))
                txtView.textSize = 30f
                txtView.layout(100, 100, 3000, 200)
                planet.status = 1

                if(planet !in discoveredPlanets){
                    discoveredPlanets.add(planet)
                    if(discoveredPlanets.size === 9){
                        end = true
                        val builder : AlertDialog.Builder = AlertDialog.Builder(alienSolarSystemCtx)
                        builder.setTitle("Game Over")
                        builder.setMessage("Thanks for playing")
                        builder.setPositiveButton("Ok"){ dialog, which ->
                            (alienSolarSystemCtx as Activity).finish()
                        }
                        builder.create()
                        builder.show()
                    }
                }
                txtView.draw(canvas)
            }}
}
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var action : Int = event.action
        val touchX : Int = event.x.toInt()
        val touchY : Int = event.y.toInt()


        when (action) {
            MotionEvent.ACTION_MOVE -> {
                spaceShip.x = touchX - 75f
                spaceShip.y = touchY - 75f

                invalidate()}}
        return true
    }
}