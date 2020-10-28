package model.shortstate

class Coordinate {

    var x: Int
    var y: Int

    constructor(x: Int, y:Int){
        this.x = x
        this.y = y
    }

    fun plus(other: Coordinate): Coordinate{
        return Coordinate(x + other.x, y + other.y)
    }
}