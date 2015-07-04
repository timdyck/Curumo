package uci;

import gameplay.Gameplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import board.BoardUtils;
import board.FEN;
import board.movement.Move;

/**
 * Implements the Universal Chess Interface
 */
public class UCI {

    private static Gameplay game;

    public static String ENGINE_NAME = "Curumo 1.0";
    public static String AUTHOR_NAME = "Tim Dyck";

    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("uci")) {
                identifyEngine();
            } else if (input.equals("isready")) {
                isReady();
            } else if (input.startsWith("setOption")) {
                setOption(input.substring(input.indexOf(" ") + 1));
            } else if (input.equals("ucinewgame")) {
                uciNewGame();
            } else if (input.startsWith("position")) {
                setPosition(input.substring(input.indexOf(" ") + 1));
            } else if (input.startsWith("go")) {
                go(input.substring(input.indexOf(" ") + 1));
            } else if (input.equals("print")) {
                print(input.substring(input.indexOf(" ") + 1));
            } else if (input.equals("quit")) {
                break;
            }

        }

        scanner.close();
    }

    private static void identifyEngine() {
        System.out.println("id name " + ENGINE_NAME);
        System.out.println("id author " + AUTHOR_NAME);
        System.out.println("uciok");
    }

    private static void isReady() {
        System.out.println("readyok");
    }

    private static void setOption(String input) {
        // Set options
    }

    private static void uciNewGame() {
        game = new Gameplay();
    }

    private static void setPosition(String input) {
        if (input.contains("startpos")) {
            game = new Gameplay();
        } else if (input.contains("fen")) {
            game = FEN.fromFenString(input);
        }

        if (input.contains("moves")) {
            // Execute all the given moves
            String movesStr = input.substring(input.indexOf("moves") + 6);
            String[] moves = movesStr.split(" ");
            makeMoves(moves);
        }
    }

    private static void makeMoves(String... moveStrs) {
        for (String moveStr : moveStrs) {
            Map<String, Move> movesMap = game.getMovesMap();

            if (!movesMap.containsKey(moveStr)) {
                throw new IllegalStateException("Move " + moveStr + " is not possible!? Debug your engine bro.");
            }

            game.executeMove(movesMap.get(moveStr));
        }
    }

    private static void go(String substring) {
        // TODO: Make this not pick a random move!
        Map<String, Move> movesMap = game.getMovesMap();
        int randIndex = new Random().nextInt(movesMap.size());

        List<String> keys = new ArrayList<String>(movesMap.keySet());
        System.out.println("bestmove " + keys.get(randIndex));

    }

    private static void print(String substring) {
        BoardUtils.printBoard(game.getBoard());
    }

    public Gameplay getGame() {
        return game;
    }
}
