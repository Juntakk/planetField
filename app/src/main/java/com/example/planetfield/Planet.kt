package com.example.planetfield

class Planet {
     var id : Int
     var name : String
     var size : Float
     var status : Int
     var image_path : Int
     var positionX : Int
     var positionY : Int

    constructor(id:Int, name:String, size:Float,status:Int,image_path:Int, positionX:Int, positionY:Int){
        this.id = id
        this.name = name
        this.size = size
        this.status = status
        this.image_path = image_path
        this.positionX = positionX
        this.positionY = positionY

    }
}