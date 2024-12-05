package com.chessd.chess.utils;

import java.util.ArrayList;
import java.util.List;


public class King extends Figure {

    public King(){}
    public King(String name, String color, String position){
        super(name, color, position);
    }
    @Override
    List<String> avaibleMoves() {
        List<String> moves = new ArrayList<>();
        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{-1, -1}); // Góra-lewo
        directions.add(new int[]{-1, 0});  // Góra
        directions.add(new int[]{-1, 1});  // Góra-prawo
        directions.add(new int[]{0, -1});  // Lewo
        directions.add(new int[]{0, 1});   // Prawo
        directions.add(new int[]{1, -1}); // Dół-lewo
        directions.add(new int[]{1, 0});  // Dół
        directions.add(new int[]{1, 1});


        return moves;
    }
}
