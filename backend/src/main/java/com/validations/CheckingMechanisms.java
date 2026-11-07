package com.validations;

public class CheckingMechanisms {


    public static Move selectedMove(String move) {
        // e2, e4
        move = move.toLowerCase();
        move = move.replace(" ","");
        move = move.replace(",","");
        char[] charMove = move.toCharArray();
        int[] intMove = new int[4];
        for (int i = 0; i < charMove.length; i++) {
            if(i % 2 == 0) {
                intMove[i] = (int) charMove[i] - 97;// Casting char to int to get ASCII value
            } else {
                intMove[i] = (int) charMove[i] - 49;
            }
        }
        Position from = new Position(intMove[1],intMove[0]);
        Position to = new Position(intMove[3],intMove[2]);
        return new Move(from,to, null);
    }

}
