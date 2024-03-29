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

class AlienSolarSystemMove : View, SensorEventListener {

    private var random: Random
    private var shipPaint: Paint
    private var planetPaint: Paint
    private var spacePaint: Paint

    private var shipX: Int
    private var shipY: Int
    private var ship: Bitmap
    private var allPlanets: ArrayList<Planet>
    private var discoveredPlanets: ArrayList<Planet>

    private var alienSolarSystemCtx: Context = this.context
    private var end: Boolean
    private var planetDGW: DGW

    private var viewportWidth: Int = 0
    private var viewportHeight: Int = 0

    private val sensorManager: SensorManager = alienSolarSystemCtx.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) as Sensor

    init {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        viewportWidth = resources.displayMetrics.widthPixels
        viewportHeight = resources.displayMetrics.heightPixels
    }

    constructor(ctx: Context) : super(ctx) {
        end = false
        random = Random()
        shipX = 182
        shipY = 1757
        shipPaint = Paint()
        planetPaint = Paint()
        spacePaint = Paint()
        shipPaint.isAntiAlias = true
        ship = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.spaceship),
            150,
            150,
            false
        )

        //DB
        planetDGW = DGW(alienSolarSystemCtx, "planets", null, 1)
        planetDGW.openDB()
//        planetDGW.insertPlanets()

        //Planets
        allPlanets = planetDGW.getAllPlanets()
        discoveredPlanets = ArrayList()
        planetDGW.closeDB()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.space2),
            0f,
            0f,
            spacePaint
        )
        canvas.drawBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.space2),
            0f,
            800f,
            spacePaint
        )
        canvas.drawBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.space2),
            0f,
            1600f,
            spacePaint
        )

        for (planet in allPlanets) {
            val planetBitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(
                    resources,
                    planet.image_path
                ), planet.size.toInt(), planet.size.toInt(), false
            )
            canvas.drawBitmap(
                planetBitmap,
                planet.positionX.toFloat(),
                planet.positionY.toFloat(),
                planetPaint
            )
        }
        canvas.drawBitmap(ship, shipX.toFloat(), shipY.toFloat(), shipPaint)
        for (planet in allPlanets) {
            val distance = sqrt(
                (planet.positionX + planet.size / 2 - shipX - 75).toDouble().pow(2.0)
                        + (planet.positionY + planet.size / 2 - shipY - 75).toDouble().pow(2.0)
            )

            val threshold = 100

            if (distance <= threshold) {
                val txtView = TextView(alienSolarSystemCtx)
                txtView.text = "${planet.name} | ${planet.size}"

                txtView.setTextColor(Color.WHITE)

                txtView.visibility = VISIBLE

                txtView.measure(MeasureSpec.getSize(1000), MeasureSpec.getSize(1000))
                txtView.textSize = 30f
                txtView.layout(100, 100, 3000, 200)
                planet.status = 1

                if (planet !in discoveredPlanets) {
                    discoveredPlanets.add(planet)
                    if (discoveredPlanets.size === 9) {
                        end = true
                        val builder: AlertDialog.Builder = AlertDialog.Builder(alienSolarSystemCtx)
                        builder.setTitle("Game Over")
                        builder.setMessage("Thanks for playing")
                        builder.setPositiveButton("Ok") { dialog, which ->
                            (alienSolarSystemCtx as Activity).finish()
                        }
                        builder.create()
                        builder.show()
                    }
                }
                txtView.draw(canvas)
            }
        }
    }


    override fun onSensorChanged(event: SensorEvent) {
        var distance: Float = sqrt(event.values[0].pow(2) + event.values[1].pow(2))

        val minX = 0
        val minY = 0
        val maxX = viewportWidth - ship.width
        val maxY = viewportHeight - ship.height

        val newX = (shipX + event.values[0] * (distance * 10)).toInt()
        val newY = (shipY + event.values[1] * distance * -10).toInt()

        shipX = newX.coerceIn(minX, maxX)
        shipY = newY.coerceIn(minY, maxY)

        invalidate()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

}