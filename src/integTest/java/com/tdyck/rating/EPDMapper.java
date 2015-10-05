package com.tdyck.rating;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import junitparams.mappers.DataMapper;

public class EPDMapper implements DataMapper  {

    @Override
    public Object[] map(Reader reader) {
        BufferedReader br = new BufferedReader(reader);
        String line;
        List<Object[]> result = new LinkedList<>();
        try {
            while ((line = br.readLine()) != null) {
                String[] epd = line.split(";");
                String[] fen = epd[0].split(" ");
                String bestMove = fen[fen.length - 1];
                String[] fenString = Arrays.copyOfRange(fen, 0, fen.length - 2);
                StringBuilder fenBuilder = new StringBuilder();
                for (String f : fenString) {
                    fenBuilder.append(f);
                    fenBuilder.append(" ");
                }
                result.add(new Object[] {fenBuilder.toString().trim(), bestMove});
            }
            return result.toArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static class EPD {
        
        private String bestMove;
        private String fenString;
        
        public EPD(String fenString, String bestMove) {
            this.fenString = fenString;
            this.bestMove = bestMove;
        }

        public String getBestMove() {
            return bestMove;
        }

        public String getFENString() {
            return fenString;
        }
    }
}
