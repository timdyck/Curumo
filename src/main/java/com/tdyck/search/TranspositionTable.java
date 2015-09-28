package com.tdyck.search;

import java.util.HashMap;
import java.util.Map;

import com.tdyck.board.movement.Move;
import com.tdyck.gameplay.Gameplay;

public class TranspositionTable {

    private Zobrist zobrist;
    private Map<Long, TableEntry> table;

    public TranspositionTable() {
        zobrist = new Zobrist();
        table = new HashMap<Long, TableEntry>();
    }

    public boolean containsEntry(Gameplay game) {
        long key = zobrist.genZobristKey(game);

        return table.containsKey(key);
    }

    public double getEntryScore(Gameplay game) {
        long key = zobrist.genZobristKey(game);

        return table.get(key).getScore();
    }

    public void addEntry(Gameplay game, Move bestMove, int depth, double score) {
        long key = zobrist.genZobristKey(game);
        TableEntry value = new TableEntry(bestMove, depth, score);

        table.put(key, value);
    }

    public void removeEntry(Gameplay game) {
        long key = zobrist.genZobristKey(game);

        table.remove(key);
    }
}
