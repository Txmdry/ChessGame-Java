package fr.dorbyperrot.pieces;

import fr.dorbyperrot.chess.Case;

public class Knight extends Piece {
    public Knight(boolean pieceCouleur) {
        super(pieceCouleur, pieceCouleur ? "♘" : "♞", pieceCouleur ? "C" : "c");
    }

    @Override
    public boolean checkMove(Case askingCase) {
        return !askingCase.isCaseBusy() || this.getCase().getPiece().isPieceCouleur() != askingCase.getPiece().isPieceCouleur();
    }
}
