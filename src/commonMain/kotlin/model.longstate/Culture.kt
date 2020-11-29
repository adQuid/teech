package model.longstate

import serialize.Serializer

class Culture {

    val id: String
    val perspectives: MutableCollection<Perspective>
    val politeGreetings: Set<String>

    constructor(saveString: String){
        val map = Serializer.mapFromJsonString(saveString)

        id = map["id"] as String
        perspectives = (map["perspectives"] as List<Map<String,Any>>).map { Perspective(it) }.toMutableSet()
        politeGreetings = (map["politeGreetings"] as List<String>).toSet()
    }

    fun perspectiveOn(topic: String): Perspective?{
        return perspectives.firstOrNull { it.topic == topic }
    }

}