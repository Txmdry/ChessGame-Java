package fr.dorbyperrot.chess;

import fr.dorbyperrot.game.Display;
import fr.dorbyperrot.pieces.*;

import java.util.TreeMap;

import static java.lang.Math.abs;

public class Chessboard {
    private final TreeMap<Integer, Case> cases = new TreeMap<>();

    public Chessboard() {
        boolean color = false;

        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                int caseNum = getIndexForCase(x, y);
                cases.put(caseNum, new Case(color, caseNum));
                color = !color;
            }
            color = !color;
        }

        cases.get(11).setPiece(new Rook(false));
        cases.get(12).setPiece(new Knight(false));
        cases.get(13).setPiece(new Bishop(false));
        cases.get(14).setPiece(new Queen(false));
        cases.get(15).setPiece(new King( false));
        cases.get(16).setPiece(new Bishop( false));
        cases.get(17).setPiece(new Knight( false));
        cases.get(18).setPiece(new Rook( false));
        cases.get(21).setPiece(new Pawn( false));
        cases.get(22).setPiece(new Pawn( false));
        cases.get(23).setPiece(new Pawn( false));
        cases.get(24).setPiece(new Pawn( false));
        cases.get(25).setPiece(new Pawn( false));
        cases.get(26).setPiece(new Pawn( false));
        cases.get(27).setPiece(new Pawn( false));
        cases.get(28).setPiece(new Pawn( false));

        //Other side//

        cases.get(78).setPiece(new Pawn(true));
        cases.get(77).setPiece(new Pawn(true));
        cases.get(76).setPiece(new Pawn(true));
        cases.get(75).setPiece(new Pawn(true));
        cases.get(74).setPiece(new Pawn(true));
        cases.get(73).setPiece(new Pawn(true));
        cases.get(72).setPiece(new Pawn(true));
        cases.get(71).setPiece(new Pawn(true));
        cases.get(88).setPiece(new Rook(true));
        cases.get(87).setPiece(new Knight(true));
        cases.get(86).setPiece(new Bishop(true));
        cases.get(84).setPiece(new Queen(true));
        cases.get(85).setPiece(new King(true));
        cases.get(83).setPiece(new Bishop(true));
        cases.get(82).setPiece(new Knight(true));
        cases.get(81).setPiece(new Rook(true));
    }

    public TreeMap<Integer, Case> getCases() {
        return cases;
    }

    /**
     * @param coords Prends en parametre les coordonnees d'une case.
     * @return Case Permet de recuperer une case.
     */
    public Case getCaseByCoordinates(String coords) {
        return this.getCases().get(getIndexFromCoords(coords));
    }

    /**
     * Permet de deplacer une piece d'une case à une autre.
     * @param piece  Prends en 1er parametre la piece.
     * @param toCase Prends en 2nd parametre la case d'arrivee.
     */
    public void movePiece(Piece piece, Case toCase) {
        piece.setFirstMove(false);
        this.cases.get(getIndexForCase(piece.getCase().getCasePosition().getX(), piece.getCase().getCasePosition().getY())).setPiece(null);
        this.cases.get(getIndexForCase(toCase.getCasePosition().getX(), toCase.getCasePosition().getY())).setPiece(piece);
    }

    /**
     * @param piece  Prends en 1er parametre la piece.
     * @param toCase Prends en 2nd parametre la case d'arrivee.
     * @return boolean return True si le deplacement est valide, False dans le cas inverse.
     */
    public boolean validateMove(Piece piece, Case toCase) {
        int x = toCase.getCasePosition().getX() - piece.getCase().getCasePosition().getX();
        int y = toCase.getCasePosition().getY() - piece.getCase().getCasePosition().getY();

        if (piece.getCase().equals(toCase))
            return false;
        if (x != 0 && y == 0)
            return validateHorizontalMove(piece, toCase);
        else if (x == 0 && y != 0)
            return validateVerticalMove(piece, toCase);
        else if (abs(x) == abs(y))
            return validateDiagonalMove(piece, toCase);
        else return abs(x) == 1 && abs(y) == 2 || abs(x) == 2 && abs(y) == 1;
    }

    /**
     * @param piece  Prends en 1er parametre la piece.
     * @param toCase Prends en 2nd parametre la case d'arrivee.
     * @return boolean return True si le deplacement est possible à l'horizontal, False dans le cas inverse.
     */
    public boolean validateHorizontalMove(Piece piece, Case toCase) {
        boolean pieceEncounter = false;
        int x = toCase.getCasePosition().getX() - piece.getCase().getCasePosition().getX();

        for (int i = 1; i <= abs(x); i++) {
            if (pieceEncounter)
                return false;
            Case currentCase = this.cases.get(getIndexForCase((x > 0 ? piece.getCase().getCasePosition().getX() + i : piece.getCase().getCasePosition().getX() - i), piece.getCase().getCasePosition().getY()));
            if (currentCase.isCaseBusy() && piece.getCase().getPiece().isPieceCouleur() == currentCase.getPiece().isPieceCouleur())
                return false;
            if (currentCase.isCaseBusy())
                pieceEncounter = true;
        }
        return true;
    }

    /**
     * @param piece  Prends en 1er parametre la piece.
     * @param toCase Prends en 2nd parametre la case d'arrivee.
     * @return boolean return True si le deplacement est possible à la vertical, False dans le cas inverse.
     */
    public boolean validateVerticalMove(Piece piece, Case toCase) {
        boolean pieceEncounter = false;
        int y = toCase.getCasePosition().getY() - piece.getCase().getCasePosition().getY();

        for (int i = 1; i <= abs(y); i++) {
            if (pieceEncounter)
                return false;
            Case currentCase = this.cases.get(getIndexForCase(piece.getCase().getCasePosition().getX(), (y > 0 ? piece.getCase().getCasePosition().getY() + i : piece.getCase().getCasePosition().getY() - i)));
            if (currentCase.isCaseBusy() && piece.getCase().getPiece().isPieceCouleur() == currentCase.getPiece().isPieceCouleur())
                return false;
            if (currentCase.isCaseBusy())
                pieceEncounter = true;
        }
        return true;
    }

    /**
     * @param piece  Prends en 1er parametre la piece.
     * @param toCase Prends en 2nd parametre la case d'arrivee.
     * @return boolean-retourne True si le deplacement est possible en diagonale, False dans le cas inverse.
     */
    public boolean validateDiagonalMove(Piece piece, Case toCase) {
        boolean pieceEncounter = false;
        int x = toCase.getCasePosition().getX() - piece.getCase().getCasePosition().getX();
        int y = toCase.getCasePosition().getY() - piece.getCase().getCasePosition().getY();

        for (int i = 1; i <= abs(x); i++) {
            if (pieceEncounter)
                return false;
            Case currentCase = this.cases.get(getIndexForCase((x > 0 ? piece.getCase().getCasePosition().getX() + i : piece.getCase().getCasePosition().getX() - i), (y > 0 ? piece.getCase().getCasePosition().getY() + i : piece.getCase().getCasePosition().getY() - i)));
            if (currentCase.isCaseBusy() && piece.getCase().getPiece().isPieceCouleur() == currentCase.getPiece().isPieceCouleur())
                return false;
            if (currentCase.isCaseBusy())
                pieceEncounter = true;
        }
        return true;
    }

    public static int getIndexForCase(int x, int y) {
        return Integer.parseInt(Integer.toString(y) + x);
    }

    public static int getIndexFromCoords(String coords) {
        return getIndexForCase(Display.alpha.indexOf(Character.toString(coords.charAt(0))) + 1, Integer.parseInt(Character.toString(coords.charAt(1))));
    }

}