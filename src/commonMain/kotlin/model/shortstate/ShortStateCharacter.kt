package model.shortstate

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Image
import com.soywiz.korge.view.View
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class ShortStateCharacter: Entity {

    val id: Int
    var x: Int
    var y: Int

    var targetX: Int? = null
    var targetY: Int? = null

    constructor(id: Int, image: String, x: Int, y: Int): super(Coordinate(x,y), image){
        this.id = id
        this.x = x
        this.y = y
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ShortStateCharacter

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    override suspend fun display(): View {
        return Image(resourcesVfs[image].readBitmap())
    }
}