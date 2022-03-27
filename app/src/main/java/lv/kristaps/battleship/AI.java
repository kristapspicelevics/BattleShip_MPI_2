package lv.kristaps.battleship;

import android.widget.Toast;

import java.util.Random;

public class AI {
    static boolean isTurn = true;
    SinkingShip sinkingShip;

    public static void AITurn(int[] map){
        int shipStartPos = 100;
        while(isTurn){
            Random rand = new Random();
            shipStartPos = rand.nextInt(100);
            checkIfHit(map, shipStartPos);
        }
        isTurn = true;
    }

    public static void checkIfHit(int[] map, int position){
        if (map[position] == 1 || map[position] == 0) {
            map[position] = -3; // ja ir 1 vai 0, tad tur bus garām aizsauts un būs udens
            isTurn = false;
        }else if (map[position] == 5){ //kuģis grimst
            map[position] = -1;
        }else {
            SinkingShip.isSunk(position,map);
        }
    }



}
