package com.larypipot.ozo.app;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import content.ArtificialIntelligences;
import content.PlayableMove;
import junit.framework.Assert;

import java.util.HashMap;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    PlayableMove rock = new PlayableMove(PlayableMove.MoveType.rock);
    PlayableMove paper = new PlayableMove(PlayableMove.MoveType.paper);
    PlayableMove scissor = new PlayableMove(PlayableMove.MoveType.scissor);

    public ApplicationTest() {
        super(Application.class);
    }

    @SmallTest
    public void testFightOutcomeWin() {
        Assert.assertEquals(PlayableMove.Outcome.win, rock.winsVs(scissor));
        Assert.assertEquals(PlayableMove.Outcome.win, scissor.winsVs(paper));
        Assert.assertEquals(PlayableMove.Outcome.win, paper.winsVs(rock));
    }

    @SmallTest
    public void testFightOutcomeLose() {
        Assert.assertEquals(PlayableMove.Outcome.lose, rock.winsVs(paper));
        Assert.assertEquals(PlayableMove.Outcome.lose, paper.winsVs(scissor));
        Assert.assertEquals(PlayableMove.Outcome.lose, scissor.winsVs(rock));
    }

    @SmallTest
    public void testFightOutcomeTie() {
        Assert.assertEquals(PlayableMove.Outcome.tie, rock.winsVs(rock));
        Assert.assertEquals(PlayableMove.Outcome.tie, paper.winsVs(paper));
        Assert.assertEquals(PlayableMove.Outcome.tie, scissor.winsVs(scissor));
    }

    @SmallTest
    public void testFightOutcomeWinCase() {
        Assert.assertEquals(PlayableMove.Outcome.win, rock.winsVsCase(scissor));
        Assert.assertEquals(PlayableMove.Outcome.win, scissor.winsVsCase(paper));
        Assert.assertEquals(PlayableMove.Outcome.win, paper.winsVsCase(rock));
    }

    @SmallTest
    public void testFightOutcomeLoseCase() {
        Assert.assertEquals(PlayableMove.Outcome.lose, rock.winsVsCase(paper));
        Assert.assertEquals(PlayableMove.Outcome.lose, paper.winsVsCase(scissor));
        Assert.assertEquals(PlayableMove.Outcome.lose, scissor.winsVsCase(rock));
    }

    @SmallTest
    public void testFightOutcomeTieCase() {
        Assert.assertEquals(PlayableMove.Outcome.tie, rock.winsVsCase(rock));
        Assert.assertEquals(PlayableMove.Outcome.tie, paper.winsVsCase(paper));
        Assert.assertEquals(PlayableMove.Outcome.tie, scissor.winsVsCase(scissor));
    }

    //testing if the computer is random enought if he plays less that 101 000 same move on a 300 000 play, it's concidareted random enough
    @SmallTest
    public void testRandomnessofComputer() {
        HashMap<PlayableMove.MoveType, Integer> stats = new HashMap<>();
        ArtificialIntelligences artificialIntelligence = new ArtificialIntelligences(ArtificialIntelligences.Difficulty.dumb);
        for (int i = 0; i < 300000; i++) {
            PlayableMove.MoveType key = artificialIntelligence.playComputer();
            if (stats.get(key) == null) {
                stats.put(key, 0);
            }
            Integer value = stats.get(key);
            value++;
            stats.put(key, value);
        }
        for (PlayableMove.MoveType moveType : stats.keySet()) {
            Assert.assertEquals(true, stats.get(moveType) < 101000);
        }
    }

    //testing if the computer mode elephant win at least 250 000 time vs a player that play always the same move 300 0000
    @SmallTest
    public void testMemoryOfComputerMoves() {
        HashMap<PlayableMove.Outcome, Integer> stats = new HashMap<>();
        ArtificialIntelligences artificialIntelligence = new ArtificialIntelligences(ArtificialIntelligences.Difficulty.elephant);
        PlayableMove opponant = new PlayableMove(PlayableMove.MoveType.paper);
        for (int i = 0; i < 300000; i++) {
            PlayableMove key = artificialIntelligence.playComputerAsMove();
            PlayableMove.Outcome result = key.winsVs(opponant);

            if (stats.get(result) == null) {
                stats.put(result, 0);
            }
            Integer value = stats.get(result);
            value++;
            stats.put(result, value);
            artificialIntelligence.saveResult(PlayableMove.MoveType.paper);
        }

            Assert.assertEquals(true, stats.get(PlayableMove.Outcome.win) > 250000);

    }
}