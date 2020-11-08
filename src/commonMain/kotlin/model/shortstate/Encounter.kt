package model.shortstate

class Encounter {
    companion object{
        val SIZE = 100
    }

    val tiles: MutableList<MutableList<Int>>
    val spawns: Collection<Spawn>

    constructor(){
        tiles = emptyTileset()
        spawns = listOf(Spawn(200, 200, 1), Spawn(300,200, 2), Spawn(400,400, 3))
    }

    fun emptyTileset(): MutableList<MutableList<Int>>{
        val retval = mutableListOf<MutableList<Int>>()

        for(x in 0..SIZE){
            retval.add(mutableListOf())
            for(y in 0..SIZE){
                retval[x].add(0)
            }
        }

        return retval
    }

}