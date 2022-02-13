package fr.dorbyperrot.game;

import fr.dorbyperrot.chess.Case;
import fr.dorbyperrot.chess.Chessboard;
import fr.dorbyperrot.chess.Game;
import fr.dorbyperrot.pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Display {
    private Game game;
    private boolean utfSymbol = true;
    private boolean gameStatus = true;
    public static final ArrayList<String> alpha = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));

    /**
     * void - Crée une nouvelle instance de partie
     */
    public void createNewGame() {
        game = new Game();
        this.handleGameRound();
    }

    /**
     * void - Gère le déroulement de la partie
     */
    private void handleGameRound() {
        String askingCase = null;
        Scanner scanner = new Scanner(System.in);

        String utfSymbol;
        do {
            System.out.println("Souhaitez-vous afficher les symboles des pièces ? (Peut décaler les pièces en fonction des écrans)");
            System.out.println("Répondez par Oui (Symboles) ou par Non (Lettres)");
            utfSymbol = scanner.nextLine();
        } while (!utfSymbol.matches("(^Oui$)|(^Non$)"));

        if (utfSymbol.equalsIgnoreCase("Non"))
            this.utfSymbol = false;


        while (this.gameStatus) {
            this.printChess();
            while (askingCase == null) {
                GameState gameState = this.getGame().checkGameState();

                if (gameState.isCheckMate()) {
                    System.out.println("Fin de partie ! Victoire de l'équipe " + this.getGame().getOppositePlayer().getName());
                    this.gameStatus = false;
                    break;
                }

                if (gameState.isPAT()) {
                    System.out.println("Fin de partie ! Egalité pour les deux équipes.");
                    this.gameStatus = false;
                    break;
                }

                System.out.println("Au tour de l'équipe " + this.getGame().getCurrentPlayer().getName() + ". Ecrivez votre pièce à déplacer (Ou écrivez Abandon).");
                askingCase = scanner.nextLine();

                if (askingCase.equalsIgnoreCase("Abandon")) {
                    System.out.println("Abandon ! Victoire de l'équipe " + this.getGame().getOppositePlayer().getName());
                    this.gameStatus = false;
                    break;
                }

                if (!askingCase.matches("[a-h]\\d{1,8}")) {
                    askingCase = null;
                    System.out.println("Choix incorrect, merci de réessayer.");
                    continue;
                }

                Case currentCase = this.getGame().getChessboard().getCaseByCoordinates(askingCase);
                if (!currentCase.isCaseBusy()) {
                    askingCase = null;
                    System.out.println("Il n'y a pas de pion sur cette case, merci de réessayer.");
                    continue;
                }

                Piece piece = currentCase.getPiece();
                if (piece.isPieceCouleur() != this.getGame().getCurrentPlayer().isTeam()) {
                    askingCase = null;
                    System.out.println("Ce n'est pas un pion de votre couleur, merci de réessayer.");
                    continue;
                }

                System.out.println("Vous avez sélectionné: " + piece.getCase().getCaseDisplay(this.utfSymbol) + ". Ecrivez une case de destination.");
                askingCase = scanner.nextLine();

                if (!askingCase.matches("[a-h]\\d{1,8}")) {
                    askingCase = null;
                    System.out.println("Choix incorrect, merci de réessayer.");
                    continue;
                }

                Case targetCase = this.getGame().getChessboard().getCaseByCoordinates(askingCase);

                boolean moveCheck = piece.checkMove(targetCase);
                boolean validateMove = this.getGame().getChessboard().validateMove(currentCase.getPiece(), targetCase);
                if (!moveCheck || !validateMove) {
                    askingCase = null;
                    System.out.println("Déplacement non valide, merci de réessayer.");
                    continue;
                }

                this.getGame().getChessboard().movePiece(currentCase.getPiece(), targetCase);

                gameState = this.getGame().checkGameState();
                if (gameState.isCheck()) {
                    System.out.println("Vous ne pouvez pas mettre votre Roi en échec, effectuez un autre mouvement.");
                    this.getGame().getChessboard().movePiece(targetCase.getPiece(), currentCase);
                    askingCase = null;
                    continue;
                }

                this.getGame().handlePromote();
                this.getGame().toggleCurrentPlayer();

            }
            askingCase = null;
        }
    }

    /**
     * void - Permet l'affichage de l'Chessboard.
     */
    public void printChess() {
        StringBuilder chessStr = new StringBuilder();

        chessStr.append("\n---------------------------------------------------\n");

        for (int y = 8; y >= 1; y--) {
            chessStr.append(y).append(" | ");
            for (int x = 1; x <= 8; x++) {
                chessStr.append(this.game.getChessboard().getCases().get(Chessboard.getIndexForCase(x, y)).getCaseDisplay(this.utfSymbol)).append("   ");
            }
            chessStr.append("\n");
        }

        chessStr.append("---------------------------------------------------\n  | ");


        for (String letter : alpha)
            chessStr.append(letter).append("   ");

        System.out.println(chessStr);
    }

    /**
     * @return game - Retourne l'instance de partie
     */
    public Game getGame() {
        return game;
    }
}


