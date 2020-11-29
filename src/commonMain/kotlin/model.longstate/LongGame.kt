package model.longstate

import java.io.File

class LongGame {

    val lowPeopleCulture = Culture(listOf(
            Perspective("seventy psalms", 40, "The Seventy Psalms are a lovely philosophy. They guide the young, and comfort the old. You would be wise to seek them.")
    ))

    val brightSeaCulture = Culture(listOf(Perspective("seventy psalms", -20, "A constant thorn in our side...")))

    val cultures: Set<Culture> /*setOf(
            lowPeopleCulture,
            brightSeaCulture,
            Culture(listOf(Perspective("trains", 100, "I like trains")))
    )*/

    val characters = setOf(
            LongStateCharacter(1, "armored ekf.png", brightSeaCulture),
            LongStateCharacter(2, "elf lady.png", brightSeaCulture),
            LongStateCharacter(3, "old bard.png", lowPeopleCulture),
            LongStateCharacter(4, "old bard.png", lowPeopleCulture),
            LongStateCharacter(5, "old bard.png", lowPeopleCulture)
    )

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
    }
}