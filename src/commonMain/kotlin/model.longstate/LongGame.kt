package model.longstate

import java.io.File

class LongGame {

    val cultures: Set<Culture>

    val characters:Set<LongStateCharacter>

    constructor(){
        val cultureStrings = File("data/cultures").walk().map {
            try{
                it.readText()
            } catch (e: Exception){
                println("failure")
                ""
            }
        }

        cultures = cultureStrings.filter{it != ""}.map{Culture(it)}.toSet()

        characters = setOf(
                LongStateCharacter(1, "armored ekf.png", cultures.first{it.id == "brightsea"}),
                LongStateCharacter(2, "elf lady.png", cultures.first{it.id == "low"}),
                LongStateCharacter(3, "old bard.png", cultures.first{it.id == "brightsea"}),
                LongStateCharacter(4, "old bard.png", cultures.first{it.id == "brightsea"}),
                LongStateCharacter(5, "old bard.png", cultures.first{it.id == "brightsea"})
        )
    }
}