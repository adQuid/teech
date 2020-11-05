package model.longstate

class Culture {

    val perspectives: MutableCollection<Perspective>

    constructor(perspectives: Collection<Perspective>){
        this.perspectives = perspectives.toMutableSet()
    }

    fun perspectiveOn(topic: String): Perspective?{
        return perspectives.firstOrNull { it.topic == topic }
    }

}