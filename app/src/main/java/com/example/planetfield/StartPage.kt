package com.example.planetfield

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class StartPage : View {

    private var startPageContext : Context
    private var MERCURY : Bitmap
    private var VENUS  : Bitmap
    private var EARTH : Bitmap
    private var MARS : Bitmap
    private var JUPITER : Bitmap
    private var SATURN : Bitmap
    private var URANUS : Bitmap
    private var NEPTUNE : Bitmap
    private var SUN : Bitmap
    private var startBtnDefault : Bitmap
    private var startBtnPressed : Bitmap
    private var playBtnState : Boolean = false
    private var screenW : Int = 0
    private var screenH : Int = 0


    constructor(ctx : Context):super(ctx){
        startPageContext = ctx

        SUN = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.sun),750,750,false)
        MERCURY = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.mercury),75,75,false)
        VENUS = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.venus),125,125,false)
        EARTH = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.earth),150,150,false)
        MARS = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.mars),85,85,false)
        JUPITER = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.jupiter),300,300,false)
        SATURN = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.saturn),275,275,false)
        URANUS = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.uranus),175,175,false)
        NEPTUNE = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.neptune),190,190,false)
        startBtnDefault = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.startbutton1), 250,250,false)
        startBtnPressed = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.startbutton2), 250,250,false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var p0 = Paint()
        var bgPaint = Paint()
        bgPaint.color = Color.DKGRAY
        canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), bgPaint)

        canvas.drawBitmap(SUN,width/2 - SUN.width/2f,-550f,p0)
        canvas.drawBitmap(MERCURY,width/2 - MERCURY.width/2f,150f + MERCURY.width,p0)
        canvas.drawBitmap(VENUS,width/2 - VENUS.width/2f,350f,p0)
        canvas.drawBitmap(EARTH,width/2 - EARTH.width/2f,525f,p0)
        canvas.drawBitmap(MARS,width/2 - MARS.width/2f,750f,p0)
        canvas.drawBitmap(JUPITER,width/2 - JUPITER.width/2f,900f,p0)
        canvas.drawBitmap(SATURN,width/2 - SATURN.width/2f,1250f,p0)
        canvas.drawBitmap(URANUS,width/2 - URANUS.width/2f,1550f,p0)
        canvas.drawBitmap(NEPTUNE,width/2 - NEPTUNE.width/2f,1775f,p0)




        if(playBtnState){
            canvas.drawBitmap(startBtnPressed, 50f,height-300f,p0)
        }else{
            canvas.drawBitmap(startBtnDefault,50f,height - 300f,p0)
        }

        var titlePaint = Paint()
        titlePaint.color = Color.BLACK
        titlePaint.textSize = 120f
        canvas.drawText("PLANET     FIELD",40f,800f,titlePaint)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenH = h
        screenW = w
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action : Int = event.action
        val touchX : Int = event.x.toInt()
        val touchY : Int = event.y.toInt()

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                if ((touchX > 50f &&
                            touchX < 50f + startBtnDefault.width) && (touchY > height - 300 &&
                            (touchY < height-300 + startBtnDefault.height))) {
                    playBtnState = true
                }
            }
            MotionEvent.ACTION_UP -> {
                if (playBtnState) {
                    val gameIntent = Intent(startPageContext, GameOn::class.java)
                    startPageContext.startActivity(gameIntent)
                }
                playBtnState = false
            }
            MotionEvent.ACTION_MOVE -> {
            }
        }
        invalidate()
        return true
    }



}