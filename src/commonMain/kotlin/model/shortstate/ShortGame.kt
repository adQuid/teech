package model.shortstate

class ShortGame {

    val characters = listOf(ShortStateCharacter(1, "armored ekf.png", 200, 500),
            ShortStateCharacter(2, "old bard.png", 600, 300),
            ShortStateCharacter(3, "old bard.png", 600, 250)
    , ShortStateCharacter(4, "old bard.png", 600, 200)
    , ShortStateCharacter(5, "old bard.png", 600, 150))

    val communications = mutableListOf<Communication>()

}