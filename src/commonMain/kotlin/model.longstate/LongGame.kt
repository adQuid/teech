package model.longstate

class LongGame {

    val cultures = setOf(
            Culture(listOf(Perspective("trains", 100, "I like trains")))
    )

    val characters = setOf(
            LongStateCharacter(1, cultures.first()),
            LongStateCharacter(2, cultures.first()),
            LongStateCharacter(3, cultures.first()),
            LongStateCharacter(4, cultures.first()),
            LongStateCharacter(5, cultures.first())
    )

}