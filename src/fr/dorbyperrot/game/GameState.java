package fr.dorbyperrot.game;

public class GameState {
    private final boolean check;
    private final boolean PAT;
    private final boolean checkMate;

    public GameState(boolean check, boolean PAT, boolean checkMate) {
        this.check = check;
        this.PAT = PAT;
        this.checkMate = checkMate;
    }

    public boolean isCheck() {
        return check;
    }

    public boolean isPAT() {
        return PAT;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "check=" + check +
                ", PAT=" + PAT +
                ", checkMate=" + checkMate +
                '}';
    }
}
