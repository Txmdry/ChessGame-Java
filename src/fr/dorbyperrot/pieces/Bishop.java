package fr.dorbyperrot.pieces;

import fr.dorbyperrot.chess.Case;

public class Bishop extends Piece {
    public Bishop(boolean pieceCouleur) {
        super(pieceCouleur, pieceCouleur ? "♗" : "♝", pieceCouleur ? "F" : "f");
    }

    @Override
    public boolean checkMove(Case askingCase) {
        return isDiagonalMovement(askingCase);
    }
}
