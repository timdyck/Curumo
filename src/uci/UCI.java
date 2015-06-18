package uci;

import gameplay.Gameplay;

import java.util.Map;
import java.util.Scanner;

import board.BoardUtils;
import board.FEN;
import board.PieceType;
import board.movement.Move;

/**
 * Implements the Universal Chess Interface
 */
public class UCI {

    private Gameplay game;

    public static String ENGINE_NAME = "Curumo v1";
    public static String AUTHOR_NAME = "Tim Dyck";

    public void executeUCI() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("uci")) {
                identifyEngine();
            } else if (input.equals("isReady")) {
                isReady();
            } else if (input.startsWith("setOption")) {
                setOption(input.substring(input.indexOf(" ") + 1));
            } else if (input.equals("ucinewgame")) {
                uciNewGame();
            } else if (input.startsWith("position")) {
                setPosition(input.substring(input.indexOf(" ") + 1));
            } else if (input.equals("go")) {
                go(input.substring(input.indexOf(" ") + 1));
            } else if (input.equals("print")) {
                print(input.substring(input.indexOf(" ") + 1));
            } else if (input.equals("quit")) {
                break;
            }

        }

        scanner.close();
    }

    private void identifyEngine() {
        System.out.println("id name " + ENGINE_NAME);
        System.out.println("id author " + AUTHOR_NAME);
        System.out.println("uciok");
    }

    private void isReady() {
        System.out.println("readyok");
    }

    private void setOption(String input) {
        // Set options
    }

    private void uciNewGame() {
        game = null;
    }

    private void setPosition(String input) {
        if (input.contains("startpos")) {
            game = new Gameplay();
        } else if (input.contains("fen")) {
            // TODO: Need to make FEN method return a game!
            game = new Gameplay(FEN.fromFenString(input), PieceType.Colour.WHITE);
        }

        if (input.contains("moves")) {
            // Execute all the given moves
            String movesStr = input.substring(input.indexOf("moves") + 6);
            String[] moves = movesStr.split(" ");
            makeMoves(moves);
        }
    }

    private void makeMoves(String... moveStrs) {
        for (String moveStr : moveStrs) {
            Map<String, Move> movesMap = game.getMovesMap();

            if (!movesMap.containsKey(moveStr)) {
                throw new IllegalStateException("Move " + moveStr + " is not possible! Debug your engine bro.");
            }

            game.executeMove(movesMap.get(moveStr));
        }
    }

    private void go(String substring) {
        // Search for the best moves
    }

    private void print(String substring) {
        BoardUtils.printBoard(game.getBoard());
    }

    public Gameplay getGame() {
        return game;
    }
}
