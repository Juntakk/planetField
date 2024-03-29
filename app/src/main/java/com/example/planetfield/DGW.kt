package com.example.planetfield

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class DGW : SQLiteOpenHelper {
    private lateinit var DB : SQLiteDatabase
    private var sun_img : Int = R.drawable.sun
    private var mercury_img : Int = R.drawable.mercury
    private var venus_img : Int = R.drawable.venus
    private var earth_img : Int = R.drawable.earth
    private var mars_img : Int = R.drawable.mars
    private var jupiter_img : Int = R.drawable.jupiter
    private var saturn_img : Int = R.drawable.saturn
    private var uranus_img : Int = R.drawable.uranus
    private var neptune_img : Int = R.drawable.neptune


    constructor(ctx: Context, name: String, factory: CursorFactory?, version: Int):super(ctx,name, factory,version){
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    override fun onCreate(db: SQLiteDatabase) {
        var query1 : String = ("CREATE TABLE IF NOT EXISTS planets(id Integer PRIMARY KEY, name string, size float, status Integer, image_path Integer, positionX Integer, positionY Integer);")
        db.execSQL(query1)
    }

    public fun openDB(){
        this.DB = writableDatabase
    }

    public fun closeDB(){
        this.DB.close()
    }
    public fun insertPlanets(){
        val insertQueries = listOf(
            "INSERT INTO planets VALUES(1, 'Sun', 500, 0, $sun_img,800,-220);",
            "INSERT INTO planets VALUES(2, 'Mercury', 75, 0, $mercury_img,512,185);",
            "INSERT INTO planets VALUES(3, 'Venus', 100, 0, $venus_img, 340, 361);",
            "INSERT INTO planets VALUES(4, 'Earth', 150, 0, $earth_img, 702,552);",
            "INSERT INTO planets VALUES(5, 'Mars', 85, 0, $mars_img, 378, 803);",
            "INSERT INTO planets VALUES(6, 'Jupiter', 300, 0, $jupiter_img, 632, 950);",
            "INSERT INTO planets VALUES(7, 'Saturn', 275, 0, $saturn_img, 321, 1200);",
            "INSERT INTO planets VALUES(8, 'Uranus', 150, 0, $uranus_img, 531, 1616);",
            "INSERT INTO planets VALUES(9, 'Neptune', 125, 0, $neptune_img, 501, 1931);"
        )
        insertQueries.forEach { query ->
            DB.execSQL(query)
        }
    }
    public fun getAllPlanets() : ArrayList<Planet>{
        var allPlanets : ArrayList<Planet> = ArrayList()

        var c : Cursor = this.DB.rawQuery("SELECT * FROM planets;", null)

        var idIndex:Int = c.getColumnIndex("id")
        var nameIndex:Int = c.getColumnIndex("name")
        var sizeIndex:Int = c.getColumnIndex("size")
        var statusIndex:Int = c.getColumnIndex("status")
        var imageIndex:Int = c.getColumnIndex("image_path")
        var xIndex:Int = c.getColumnIndex("positionX")
        var yIndex:Int = c.getColumnIndex("positionY")

        if(c != null && c.moveToFirst()) {
            do {
                allPlanets.add(
                    Planet(c.getInt(idIndex),
                        c.getString(nameIndex),
                        c.getFloat(sizeIndex),
                        c.getInt(statusIndex),
                        c.getInt(imageIndex),
                        c.getInt(xIndex),
                        c.getInt(yIndex))
                )
            } while (c.moveToNext())
        }
        return allPlanets
    }
}