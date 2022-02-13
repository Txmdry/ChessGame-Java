package fr.dorbyperrot.game;

import fr.dorbyperrot.pieces.Piece;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private final String name;
    private final boolean team;
    private final ArrayList<Piece> pieceTeam;
    private Piece king;

    public Player(String name, boolean team) {
        this.name = name;
        this.team = team;
        this.pieceTeam = new ArrayList<>();
    }

    public Player(boolean team) {
        this.team = team;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Merci de donner un nom à l'équipe des pièces " + (this.team ? "noires" : "blanches"));
        this.name = scanner.next();
        this.pieceTeam = new ArrayList<>();
    }

    /**
     * @return String - Retourne le nom du joueur.
     */
    public String getName() {
        return name;
    }

    /**
     * @return boolean - Retourne l'équipe du joueur.
     */
    public boolean isTeam() {
        return team;
    }

    /**
     * @return ArrayList de Piece - Retourne les pièces de l'équipe.
     * @see Piece
     */
    public ArrayList<Piece> getPieceTeam() {
        return pieceTeam;
    }

    /**
     * @return Retourne le Roi de l'équipe.
     * @see Piece
     */
    public Piece getKing() {
        return king;
    }

    /**
     * @param king - Change l'instance du Roi de l'équipe.
     * @see Piece
     */
    public void setKing(Piece king) {
        this.king = king;
    }
}
