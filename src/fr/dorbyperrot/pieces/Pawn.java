package fr.dorbyperrot.pieces;

import fr.dorbyperrot.chess.Case;

import static java.lang.Math.abs;

public class Pawn extends Piece {
    public Pawn(boolean pieceCouleur) {
        super(pieceCouleur, pieceCouleur ? "♙" : "♟", pieceCouleur ? "P" : "p");
    }

    @Override
    public boolean checkMove(Case askingCase) {
        int x = this.getCase().getCasePosition().getX() - askingCase.getCasePosition().getX();
        int y = this.getCase().getCasePosition().getY() - askingCase.getCasePosition().getY();
        if (x == 0 && !askingCase.isCaseBusy()) {
            if (y == (this.isPieceCouleur() ? 2 : -2))
                return this.isFirstMove();

            return y == (this.isPieceCouleur() ? 1 : -1);
        } else if (abs(x) == 1 && askingCase.isCaseBusy()) {
            if (y == (this.isPieceCouleur() ? 1 : -1))
                return askingCase.isCaseBusy() && (this.isPieceCouleur() != askingCase.getPiece().isPieceCouleur());

        }
        return false;
    }
}
