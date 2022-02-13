package fr.dorbyperrot.pieces;

import fr.dorbyperrot.chess.Case;

public class Queen extends Piece {
    public Queen(boolean pieceCouleur) {
        super(pieceCouleur, pieceCouleur ? "♕" : "♛", pieceCouleur ? "D" : "d");
    }

    @Override
    public boolean checkMove(Case askingCase) {
        return this.isHorizontalMovement(askingCase) || this.isVerticalMovement(askingCase) || this.isDiagonalMovement(askingCase);
    }
}
