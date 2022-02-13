package fr.dorbyperrot.pieces;

import fr.dorbyperrot.chess.Case;

public class King extends Piece {
    public King(boolean pieceCouleur) {
        super(pieceCouleur, pieceCouleur ? "♔" : "♚", pieceCouleur ? "R" : "r");
    }

    @Override
    public boolean checkMove(Case askingCase) {
        return this.isAroundMovement(askingCase);
    }
}
