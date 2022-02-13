package fr.dorbyperrot.pieces;

import fr.dorbyperrot.chess.Case;

public class Rook extends Piece {
    public Rook(boolean pieceCouleur) {
        super(pieceCouleur, pieceCouleur ? "♖" : "♜", pieceCouleur ? "T" : "t");
    }

    @Override
    public boolean checkMove(Case askingCase) {
        if (this.isHorizontalMovement(askingCase) && !this.isVerticalMovement(askingCase))
            return true;
        return !this.isHorizontalMovement(askingCase) && this.isVerticalMovement(askingCase);
    }
}
