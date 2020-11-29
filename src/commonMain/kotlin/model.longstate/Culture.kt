package model.longstate

import serialize.Serializer

class Culture {

    val perspectives: MutableCollection<Perspective>

    constructor(perspectives: Collection<Perspective>){
        this.perspectives = perspectives.toMutableSet()
    }

    constructor(saveString: String){
        val map = Serializer.mapFromJsonString(saveString)

        perspectives = (map["perspectives"] as List<Map<String,Any>>).map { Perspective(it) }.toMutableSet()
    }

    fun perspectiveOn(topic: String): Perspective?{
        return perspectives.firstOrNull { it.topic == topic }
    }

}