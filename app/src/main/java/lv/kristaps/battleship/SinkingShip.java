package lv.kristaps.battleship;

public class SinkingShip {

    public static boolean isSunk(int position, int[] map){
        int direction = 0;
        if(map[position] == 4 || map[position] == 45 || map[position] == 6){
            map[position] = 99;
            direction = 1;
            while(true){
                if(direction == 1) {
                    if ((position) % 10 == 9) direction = -1;
                    else if (map[position + direction] == 1 || map[position + direction] == -3)
                        direction = -1;
                }
                if(direction == -1) {
                    if ((position) % 10 == 0) {
                        sinkHorizontalShip(position, map);
                        return true;
                    } else if (map[position + direction] == 1 || map[position + direction] == -3) {
                        sinkHorizontalShip(position, map);
                        return true;
                    }
                }
                position = position + direction;
            //    funeral = funeral + position + "";
                if(map[position] == 4 || map[position] == 45 || map[position] == 6 ) return false;
            }
        }
        if(map[position] == 8 || map[position] == 85 || map[position] == 2){
            map[position] = 99;
            direction = 10;
            while(true){
                if(direction == 10) {
                    if (position > 89) direction = -10;
                    else if (map[position + direction] == 1 || map[position + direction] == -3)
                        direction = -10;
                }
                if(direction == -10){
                    if(position < 10) {
                        sinkVerticalShip(position, map);
                        return true;
                    } else if (map[position + direction] == 1 || map[position + direction] == -3){
                        sinkVerticalShip(position, map);
                        return true;
                    }
                }
                position = position + direction;
                if(map[position] == 8 || map[position] == 85 || map[position] == 2 ) return false;
            }
        }
        return true;
    }
    public static void sinkHorizontalShip(int position, int[] map){
        boolean doContinue = true;
        int direction = 1;
        while(doContinue == true){
            if(direction == 1) {
                if ((position) % 10 == 9) {
                    direction = -1;
                    map[position] = -6;
                }
                else if (map[position + direction] == 1 || map[position + direction] == -3) {
                    direction = -1;
                    map[position] = -6;
                } else map[position] = -45;
            }
            if(direction == -1) {
                if ((position) % 10 == 0) {
                    map[position] = -4;
                    doContinue = false;
                } else if (map[position + direction] == 1 || map[position + direction] == -3) {
                    map[position] = -4;
                    doContinue = false;
                } else {
                    if (map[position] != -6) {
                        map[position] = -45;
                    }
                }
            }
//            map[position] = -1;
            position = position + direction;
        }
    }
    public static void sinkVerticalShip(int position, int[] map) {
        boolean doContinue = true;
        int direction = 10;
        while(doContinue == true) {
            map[position] = -1;
            if(direction == 10) {
                if (position > 89) {
                    direction = -10;
                    map[position] = -2;
                }
                else if (map[position + direction] == 1 || map[position + direction] == -3) {
                    direction = -10;
                    map[position] = -2;
                } else map[position] = -85;
            }
            if (direction == -10) {
                if (position < 10) {
                    map[position] = -8;
                    doContinue = false;
                } else if (map[position + direction] == 1 || map[position + direction] == -3) {
                    map[position] = -8;
                    doContinue = false;

                } else {
                    if (map[position] != -2) {
                        map[position] = -85;
                    }
                }
            }
            position = position + direction;
        }
    }
}
