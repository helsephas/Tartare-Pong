package models.match

import models.Match
import models.shots.ShotType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MatchShotTest {

    private lateinit var match: Match

    @BeforeEach
    fun setUp() {
        this.match = Match().startGame()
    }

    @Test
    fun addShotToMatchSimpleSuccess(){
        match.currentShotType = ShotType.SIMPLE
        match.addDrink(1)
        match.addShot()
        Assertions.assertEquals(match.shots.size, 1)
        Assertions.assertTrue(match.isDrinkScored(1))
        Assertions.assertFalse(match.isDrinkScoredDefinitivelyScoredForCurrentTeam(1))
        Assertions.assertFalse(match.isDrinkScoredDefinitivelyScoredForCurrentTeam(2))
        Assertions.assertFalse(match.isDrinkScoredDefinitivelyScoredForOtherTeam(1))
        Assertions.assertFalse(match.isDrinkScoredDefinitivelyScoredForOtherTeam(2))
        Assertions.assertFalse(match.getAttackTeam().getPlayerByNumber(1).trickShotAvailable)
    }

    @Test
    fun addShotToMatchSimpleFailed(){
        match.currentShotType = ShotType.SIMPLE
        match.addShot()
        Assertions.assertEquals(match.shots.size, 1)
        Assertions.assertFalse(match.isDrinkScored(1))
        Assertions.assertFalse(match.isDrinkScoredDefinitivelyScoredForCurrentTeam(1))
        Assertions.assertFalse(match.isDrinkScoredDefinitivelyScoredForOtherTeam(1))
        Assertions.assertEquals(match.currentPlayerPlaying, match.teams[0].getPlayerByNumber(2))
        Assertions.assertTrue(match.getAttackTeam().getPlayerByNumber(1).trickShotAvailable)
    }
}