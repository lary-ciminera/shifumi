package content;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Lar on 27/03/2016.
 * ArtificialIntelligences
 */
public class ArtificialIntelligences implements Serializable{
    private final Difficulty difficulty;
    public int total;
    private Integer caseRock;
    private Integer casePaper;

    public ArtificialIntelligences(Difficulty d) {
        this.difficulty =d;
        memory = new HashMap<>();
        for (PlayableMove.MoveType s : PlayableMove.MoveType.values()){
            memory.put(s,0);
        }
        total = 0;
    }

    static HashMap<PlayableMove.MoveType,Integer> memory;
    public  PlayableMove.MoveType playComputer() {
        PlayableMove.MoveType moveType = null;
        switch (this.difficulty){

            case dumb:
                moveType = PlayableMove.MoveType.indexToMoveType((int) Math.floor(Math.random() * 3));
                break;
            case elephant:
                double floor = Math.floor(Math.random() * total);
                caseRock = memory.get(PlayableMove.MoveType.rock);
                casePaper=caseRock + memory.get(PlayableMove.MoveType.paper);
                if (floor<caseRock){
                    moveType = PlayableMove.MoveType.paper;
                }else if (floor <casePaper){
                    moveType = PlayableMove.MoveType.scissor;
                }else {
                    moveType = PlayableMove.MoveType.rock;
                }
                break;
        }
        return moveType;
    }
    public enum Difficulty{
        // a dumb AI that only pick at random
        dumb,
        // a smarter ai that remember user move analize frequency and is it against him
        elephant,;

        public static Difficulty getFromIndex(int difficultyIndex) {
            switch (difficultyIndex){
                case 0 : return dumb;
                case 1: return elephant;
            }
            return null;
        }
    }

    public  PlayableMove playComputerAsMove() {
        return new PlayableMove(playComputer());
    }

    public  void saveResult(PlayableMove.MoveType outcome) {


            Integer outComeTotal = memory.get(outcome);
            outComeTotal++;
            total ++;
            memory.put(outcome,outComeTotal);


    }
}
