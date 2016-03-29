package content;

/**
 * Created by Lary on 27/03/2016.
 * PlayableMove
 */
public class PlayableMove {
    public static final int NumerOfMoveType = 3;
    public MoveType moveType = MoveType.rock;

    public PlayableMove(MoveType type) {
        this.moveType = type;
    }

    public static MoveType stringToType(String moveName) {
        MoveType result = null;
        if (moveName.equals(MoveType.rock.name())) {
            result = MoveType.rock;
        } else if (moveName.equals(MoveType.paper.name())) {
            result = MoveType.paper;
        } else if (moveName.equals(MoveType.scissor.name())) {
            result = MoveType.scissor;
        }

        return result;
    }

    /**
     * winsVs compare object with parameter to see which one won the round
     * More complicated than winsVsCase but more scalable
     * @param opponant - PlayableMove
     * @return Outcome
     */
    public Outcome winsVs(PlayableMove opponant) {
        int fight = ((this.moveType.ordinal() - opponant.moveType.ordinal() + 3) % 3);
        return Outcome.indexToOutcome(fight);
    }

    /**
     * winsVsCase compare object with opponant parameter to see which one won the round
     * @param opponant - PlayableMove
     * @return Outcome
     */
    public Outcome winsVsCase(PlayableMove opponant) {
        switch (this.moveType) {
            case rock:
                switch (opponant.moveType) {
                    case rock:
                        return Outcome.tie;
                    case paper:
                        return Outcome.lose;
                    case scissor:
                        return Outcome.win;
                }
            case paper:
                switch (opponant.moveType) {
                    case rock:
                        return Outcome.win;
                    case paper:
                        return Outcome.tie;
                    case scissor:
                        return Outcome.lose;
                }
            case scissor:
                switch (opponant.moveType) {
                    case rock:
                        return Outcome.lose;
                    case paper:
                        return Outcome.win;
                    case scissor:
                        return Outcome.tie;
                }
        }
        return null;
    }

    public enum MoveType {
        rock,
        paper,
        scissor;
        public static MoveType indexToMoveType(int fight) {
            switch (fight){
                case 0: return rock;

                case 1 : return paper;

                case 2 : return scissor;
            }

            return null;
        }
    }

    public enum Outcome {
        tie, win , lose;

        public static Outcome indexToOutcome(int fight) {
            switch (fight){
                case 0: return tie;
                case 1 : return win;
                case 2 : return lose;
            }

            return tie;
        }
    }
}
