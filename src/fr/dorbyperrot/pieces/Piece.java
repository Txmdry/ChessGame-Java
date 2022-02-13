package fr.dorbyperrot.pieces;


import fr.dorbyperrot.chess.Case;

import static java.lang.Math.abs;

public abstract class Piece {
    private Case pieceCase;
    private final boolean pieceCouleur;
    private final String utfSymbol;
    private final String letterDisplayed;
    private boolean firstMove;

    public Piece(boolean pieceCouleur, String utfSymbol, String letterDisplayed) {
        this.pieceCouleur = pieceCouleur;
        this.utfSymbol = utfSymbol;
        this.letterDisplayed = letterDisplayed;
        this.firstMove = true;
    }

    /**
     * @return Case - Retourne la case où la pièce est posée.
     * @see Case
     */
    public Case getCase() {
        return this.pieceCase;
    }

    /**
     * Permet de changer la case de la pièce
     * @param pieceCase Case où la pièce s'est déplacée
     * @see Case
     */
    public void setCase(Case pieceCase) {
        this.pieceCase = pieceCase;
    }

    /**
     * @return boolean - Retourne couleur de la piece.
     */
    public boolean isPieceCouleur() {
        return pieceCouleur;
    }

    /**
     * @return String - Retourne le symbole UTF-8 de la pièce.
     */
    public String getUtfSymbol() {
        return utfSymbol;
    }

    /**
     * @return String - Retourne la lettre affichée de la pièce
     */
    public String getLetterDisplayed() {
        return letterDisplayed;
    }

    /**
     * @return boolean - Retourne True si c'est le premier mouvement de la pièce.
     */
    public boolean isFirstMove() {
        return firstMove;
    }

    /**
     * @param askingCase Case de destination
     * @return boolean - Retourne True si le mouvement est possible vis-à-vis de l'autre case
     * @see Case
     */
    public abstract boolean checkMove(Case askingCase);

    /**
     * @param value Change la valeur pour le premier mouvement
     */

    public void setFirstMove(boolean value) {
        this.firstMove = value;
    }

    /**
     * @param askingCase Case de destination
     * @return boolean - Retourne True si le mouvement est horizontal
     * @see Case
     */
    public boolean isHorizontalMovement(Case askingCase) {
        return this.getCase().getCasePosition().getY() == askingCase.getCasePosition().getY() && this.getCase().getCasePosition().getX() != askingCase.getCasePosition().getX();
    }

    /**
     * @param askingCase Case de destination
     * @return boolean - Retourne True si le mouvement est vertical
     * @see Case
     */
    public boolean isVerticalMovement(Case askingCase) {
        return this.getCase().getCasePosition().getY() != askingCase.getCasePosition().getY() && this.getCase().getCasePosition().getX() == askingCase.getCasePosition().getX();
    }

    /**
     * @param askingCase Case de destination
     * @return boolean - Retourne True si le mouvement est diagonal
     */
    public boolean isDiagonalMovement(Case askingCase) {
        int x = askingCase.getCasePosition().getX() - this.getCase().getCasePosition().getX();
        int y = askingCase.getCasePosition().getY() - this.getCase().getCasePosition().getY();

        return abs(x) == abs(y);
    }

    /**
     * @param askingCase Case de destination
     * @return boolean - Retourne True si le mouvement est autour de la pièce
     */
    public boolean isAroundMovement(Case askingCase) {
        int x = askingCase.getCasePosition().getX() - this.getCase().getCasePosition().getX();
        int y = askingCase.getCasePosition().getY() - this.getCase().getCasePosition().getY();
        return abs(x) <= 1 && abs(y) <= 1;
    }

    /**
     * @return String - Retourne les attributs de la pièce
     */
    @Override
    public String toString() {
        return "Piece{" +
                "pieceCase=" + pieceCase +
                ", pieceCouleur=" + pieceCouleur +
                ", utfSymbol='" + utfSymbol + '\'' +
                ", firstMove=" + firstMove +
                '}';
    }
}
