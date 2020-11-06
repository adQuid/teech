package model.longstate

class LongGame {

    val lowPeopleCulture = Culture(listOf(
            Perspective("seventy psalms", 40, "The Seventy Psalms are a lovely philosophy. They guide the young, and comfort the old. You would be wise to seek them.")
    ))

    val brightSeaCulture = Culture(listOf(Perspective("seventy psalms", -20, "A constant thorn in our side...")))

    val cultures = setOf(
            lowPeopleCulture,
            brightSeaCulture,
            Culture(listOf(Perspective("trains", 100, "I like trains")))
    )

    val characters = setOf(
            LongStateCharacter(1, brightSeaCulture),
            LongStateCharacter(2, brightSeaCulture),
            LongStateCharacter(3, lowPeopleCulture),
            LongStateCharacter(4, lowPeopleCulture),
            LongStateCharacter(5, lowPeopleCulture)
    )

}