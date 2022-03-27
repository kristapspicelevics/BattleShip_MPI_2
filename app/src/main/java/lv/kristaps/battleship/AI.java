package lv.kristaps.battleship;

import java.util.Random;

public class AI {

    boolean isTurn = true;
    SinkingShip sinkingShip;
    int computerScore = 0;

    public void AITurn(int[] map){
        int shipStartPos = 100;
        while(isTurn){
            Random rand = new Random();
            shipStartPos = rand.nextInt(100);
            if ((map[shipStartPos] >= -8 && map[shipStartPos] <= -1) || map[shipStartPos] == 99 || map[shipStartPos] == -45 || map[shipStartPos] == -85){
                continue;
            }
            checkIfHit(map, shipStartPos);
        }
        isTurn = true;
    }

    public void checkIfHit(int[] map, int position){
        if (map[position] == 1 || map[position] == 0) {
            map[position] = -3; // ja ir 1 vai 0, tad tur bus garām aizsauts un būs udens
            isTurn = false;
        }else if (map[position] == 5){ //kuģis grimst
            map[position] = -1;
            computerScore++;
        }else {
            SinkingShip.isSunk(position,map);
            computerScore++;
        }
    }

}
