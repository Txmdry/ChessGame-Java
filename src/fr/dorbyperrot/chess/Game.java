package fr.dorbyperrot.chess;

import fr.dorbyperrot.game.GameState;
import fr.dorbyperrot.game.Player;
import fr.dorbyperrot.pieces.*;

import java.util.Scanner;

public class Game {
    private final Chessboard chessboard;
    private final Player playerB;
    private final Player playerW;
    private boolean currentPlayer;

    public Game() {
        this.chessboard = new Chessboard();
        this.playerB = new Player(true); // Joueur avec pions noirs
        this.playerW = new Player(false); // Joueur avec pions blancs
        this.currentPlayer = false;

        for (Case currentCase : this.chessboard.getCases().values()) {
            if (currentCase.isCaseBusy()) {
                if (currentCase.getPiece().isPieceCouleur() == this.playerB.isTeam()) {
                    this.playerB.getPieceTeam().add(currentCase.getPiece());
                    if (currentCase.getPiece() instanceof King)
                        this.playerB.setKing(currentCase.getPiece());
                } else {
                    this.playerW.getPieceTeam().add(currentCase.getPiece());
                    if (currentCase.getPiece() instanceof King)
                        this.playerW.setKing(currentCase.getPiece());
                }
            }
        }
    }

    /**
     * @return Chessboard - Retourne l'instance de l'échiquier
     * @see Chessboard
     */
    public Chessboard getChessboard() {
        return chessboard;
    }

    /**
     * Permet de récupérer le joueur qui joue les pièces noir
     * @return Player - Retourne le joueur qui joue les pièces noir
     * @see Player
     */
    public Player getPlayerB() {
        return playerB;
    }

    /**
     * Permet de récupérer le joueur qui joue les pièces blanches
     * @return Retourne le joueur qui joue les pièces blanches
     * @see Player
     */
    public Player getPlayerW() {
        return playerW;
    }

    /**
     * Permet de récupérer le joueur qui joue
     * @return Retourne le joueur qui joue actuellement
     * @see Player
     */
    public Player getCurrentPlayer() {
        return currentPlayer ? this.playerB : this.playerW;
    }

    /**
     * Permet de récupérer le joueur qui opposé à celui qui joue
     * @return Retourne le joueur adverse
     * @see Player
     */
    public Player getOppositePlayer() {
        return currentPlayer ? this.playerW : this.playerB;
    }

    /**
     * Permet d'échanger le joueur actuel
     */
    public void toggleCurrentPlayer() {
        this.currentPlayer = !this.currentPlayer;
    }

    /**
     * Appeler cette fonction permet de vérifier si une promotion est possible
     * @see Pawn
     */
    public void handlePromote() {
        for (Piece piece : this.getCurrentPlayer().getPieceTeam()) {
            if (piece.getCase().isCaseBusy()) {
                if (piece instanceof Pawn)
                    if (piece.getCase().getCasePosition().getY() == 1 || piece.getCase().getCasePosition().getY() == 8) {
                        String out;
                        do {
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("Un pion peut être promu, entrez votre choix (Dame / Tour / Fou / Cavalier)");
                            out = scanner.nextLine();
                            switch (out) {
                                case "Dame":
                                    piece.getCase().setPiece(new Queen(true));
                                    break;
                                case "Tour":
                                    piece.getCase().setPiece(new Rook(true));
                                    break;
                                case "Fou":
                                    piece.getCase().setPiece(new Bishop(true));
                                    break;
                                case "Cavalier":
                                    piece.getCase().setPiece(new Knight(true));
                                    break;
                                default:
                                    break;
                            }
                        } while (!out.matches("(^Dame$)|(^Tour$)|(^Fou$)|(^Cavalier$)"));
                        System.out.println("La pièce a été promu !");
                    }
            }
        }
    }

    /**
     * Permet de connaître le statut actuel de la partie
     * @return Retourne l'objet GameState
     * @see GameState
     */
    public GameState checkGameState() {
        return new GameState(this.playerIsCheck(), this.playerIsPAT(), this.playerIsCheckMate());
    }

    /**
     * @return Retourne si le joueur actuel est en échec
     * @see #playerIsCheck(Case) 
     */
    public boolean playerIsCheck() {
        return this.playerIsCheck(null);
    }

    /**
     *
     * @param currentCase Case où le Roi souhaite se déplacer (If null, Case où se situe le roi)
     * @return boolean - Retourne si le joueur actuel est en échec
     * @see Case
     */
    public boolean playerIsCheck(Case currentCase) {
        for (Piece piece : this.getOppositePlayer().getPieceTeam())
            if (piece.checkMove(currentCase == null ? this.getCurrentPlayer().getKing().getCase() : currentCase) && this.getChessboard().validateMove(piece, currentCase == null ? this.getCurrentPlayer().getKing().getCase() : currentCase))
                return true;

        return false;
    }

    /**
     * @return boolean - Retourne si le joueur actuel est en échec et mat
     * @see Piece#checkMove(Case)
     * @see Chessboard#validateMove(Piece, Case)
     */
    public boolean playerIsCheckMate() {
        int counterIteration = 0;
        int counterImpossibleMovements = 0;
        if (!playerIsCheck())
            return false;

        for (Case currentCase : this.getChessboard().getCases().values()) {
            if (!this.getCurrentPlayer().getKing().checkMove(currentCase) || !this.getChessboard().validateMove(this.getCurrentPlayer().getKing(), currentCase))
                counterImpossibleMovements += 1;
            else if (playerIsCheck(currentCase))
                counterImpossibleMovements += 1;

            counterIteration += 1;
        }
        return counterIteration + counterImpossibleMovements != 0 && counterImpossibleMovements == counterIteration;
    }

    /**
     * @return boolean - Retourne si le joueur est en PAT
     * @see Piece#checkMove(Case)
     * @see Chessboard#validateMove(Piece, Case)
     */
    public boolean playerIsPAT() {
        int counterIteration = 0;
        int counterImpossibleMovements = 0;
        if (playerIsCheck())
            return false;

        for (Piece piece : this.getCurrentPlayer().getPieceTeam()) {
            for (Case currentCase : this.getChessboard().getCases().values()) {
                if(!piece.checkMove(currentCase) || !this.getChessboard().validateMove(piece, currentCase))
                    counterImpossibleMovements += 1;

                else if (playerIsCheck(currentCase))
                    counterImpossibleMovements += 1;

                counterIteration += 1;
            }
        }
        return counterIteration + counterImpossibleMovements != 0 && counterImpossibleMovements == counterIteration;
    }
}
