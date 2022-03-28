package lv.kristaps.battleship;

import android.media.MediaPlayer;
import android.os.Handler;

import java.util.Random;

public class AI {

    boolean isTurn = true;
    SinkingShip sinkingShip;
    int computerScore = 0;
    int hitStatus = 100;
    boolean isVertical = false;


    public void AITurn(int[] map, MediaPlayer music, MediaPlayer music1){
        int position = hitStatus;
        while(isTurn){
            if(hitStatus != 100){ //ir trāpīts, bet nav zināms virziens
                if(isVertical) position = hitWoundedVertical(map, position);
                else position = hitWounded(map, position);
            }
            else{
                Random rand = new Random();
                position = rand.nextInt(100);
            }
            if ((map[position] >= -8 && map[position] <= -1) || map[position] == 99 || map[position] == -45 || map[position] == -85){
                continue; //šis ir lai score skaitītu
            }
            checkIfHit(map, position, music, music1);
        }
        isTurn = true;
    }

    public void checkIfHit(int[] map, int position, MediaPlayer music, MediaPlayer music1){
        if (map[position] == 1 || map[position] == 0) {
            map[position] = -3; // ja ir 1 vai 0, tad tur bus garām aizsauts un būs udens
            music1.start();
            isTurn = false;
        }else if (map[position] == 5){ //kuģis grimst
            music.start();
            map[position] = -1;
            computerScore++;
        }else {
            if(hitStatus == 100) hitStatus = position;
            if(SinkingShip.isSunk(position,map)) {
                hitStatus = 100;
                isVertical = false;
            }
            computerScore++;
            music.start();
        }
    }

    public int hitWounded(int[] map, int position){
        int direction = 1;
        while(true){
            if (map[position] != 99) return position;
            if(direction == 1) {
                if ((position) % 10 == 9) direction = -1;
                else if (map[position + direction] == -3) direction = -1;
            }
            if(direction == -1) {
                if ((position) % 10 == 0) return hitWoundedVertical(map, hitStatus);
                else if (map[position + direction] == -3) return hitWoundedVertical(map, hitStatus);
            }
            position = position + direction;
        }
    }

    public int hitWoundedVertical(int[] map, int position){
        int direction = 10;
        isVertical = true;
        while(true){
            if (map[position] != 99) return position;
            if(direction == 10) {
                if (position > 89) direction = -10;
                else if (map[position + direction] == -3) direction = -10;
            }
//            if(direction == -10){
//                if(position < 10) return 5;
//                else if (map[position + direction] == -3) return 5;
//            }
            position = position + direction;
        }
    }

}
