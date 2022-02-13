package fr.dorbyperrot.chess;

import fr.dorbyperrot.pieces.Piece;

public class Case {

    private final boolean caseColor;
    private final CasePosition casePosition;
    private boolean caseBusy;

    private Piece piece;

    public Case(boolean caseColor, int caseNum) {
        this.caseColor = caseColor;
        this.casePosition = new CasePosition(caseNum);
        this.caseBusy = false;
    }

    public Case(boolean caseColor, int caseNum, Piece piece) {
        this.caseColor = caseColor;
        this.casePosition = new CasePosition(caseNum);
        this.caseBusy = true;
        this.piece = piece;
    }

    /**
     * @param piece Prends en parametre une piece.
     *              Permet l'insertion d'une piece sur une case.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        this.caseBusy = piece != null;
        if (piece != null)
            piece.setCase(this);
    }

    /**
     * @param symbol Vrai si les symboles sont affichés, Faux si les lettres sont affichées
     * @return String - Retourne le symbole d'une case à afficher.
     */
    public String getCaseDisplay(boolean symbol) {
        return this.caseBusy ? (symbol ? this.piece.getUtfSymbol() : this.piece.getLetterDisplayed()) : (this.caseColor ? "*" : "+");
    }

    /**
     * @return boolean - Retourne la couleur d'une case.
     */
    public boolean isCaseColor() {
        return this.caseColor;
    }

    /**
     * @return CasePosition - Retourne la position d'une case.
     * @see CasePosition
     */
    public CasePosition getCasePosition() {
        return this.casePosition;
    }

    /**
     * @return boolean - Retourne True si une case est occupé ou False si vide.
     */
    public boolean isCaseBusy() {
        return this.caseBusy;
    }

    /**
     * @return Piece - Retourne la pièce posée sur la case.
     * @see Piece
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * @return String - retourne l'échiquier, la couleur d'une case, la position de la case, si elle est occupée et la piece qui s'y trouve.
     */
    @Override
    public String toString() {
        return "Case{" +
                "caseColor=" + caseColor +
                ", casePosition=" + casePosition.toString() +
                ", caseBusy=" + caseBusy + '}';
    }

    public class CasePosition {
        private int x;
        private int y;

        public CasePosition(int caseNum) {
            this.x = Integer.parseInt(Integer.toString(caseNum).substring(1, 2));
            this.y = Integer.parseInt(Integer.toString(caseNum).substring(0, 1));
        }

        public CasePosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * @return int - Retourne l'abscisse d'une case.
         */
        public int getX() {
            return x;
        }

        /**
         * @return int - Retourne l'ordonnee d'une case.
         */
        public int getY() {
            return y;
        }

        /**
         * @param caseNum Prends en parametre le numero d'une case.
         *                Permet d'affecter une position en abscisse et ordonnee a une case.
         */
        public void setPosition(int caseNum) {
            this.x = Integer.parseInt(Integer.toString(caseNum).substring(1, 2));
            this.y = Integer.parseInt(Integer.toString(caseNum).substring(0, 1));
        }

        /**
         * @param x Prends en parametre le numero d'une case en abscisse.
         *          Permet d'affecter une position en abscisse a une case.
         */
        public void setX(int x) {
            this.x = x;
        }

        /**
         * @param y Prends en parametre le numero d'une case en ordonnee.
         *          Permet d'affecter une position en ordonnee a une case.
         */
        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "CasePosition{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
