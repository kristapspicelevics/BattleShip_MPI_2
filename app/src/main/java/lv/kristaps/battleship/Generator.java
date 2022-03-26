package lv.kristaps.battleship;

import java.util.Random;

public class Generator {

    static int[] playerMap = new int [100];
    static int[] allShips = new int[] {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

    public static void generateMap(int[] map){
        Random rand = new Random();
        Random rend = new Random();
        int shipStartPos = 100;
        for (int ship : allShips){
            shipStartPos = 100;
            int directionRand = 0;
            while(shipStartPos == 100) {
                shipStartPos = rand.nextInt(100);
                System.out.println(" " + shipStartPos);
                directionRand = rend.nextInt(2);
                if(directionRand == 0) shipStartPos = findPlaceForShipHorizontal(ship, shipStartPos, map);
                else shipStartPos = findPlaceForShipVertical(ship, shipStartPos, map);
            }
            if(directionRand == 0) updateMapHorizontal(ship, shipStartPos, map);
            else updateMapVertical(ship, shipStartPos, map);
            printMap(map);
        }
    }

    public static int findPlaceForShipHorizontal(int shipLength, int shipStartPos, int[] map) {
        System.out.println("Horizontal " + shipLength);
        if(map[shipStartPos] == 0 && shipLength == 1) { //ja mīna un nav aizņemts lauciņš, apstiprinam
            return shipStartPos;
        }
        for(int i = 0; i < shipLength; i++) { //iterē cauri kuģa garumam, apskatot katru kuģim vajadzīgo laukumu
            if((shipStartPos + i) % 10 == 0 && i != 0) return 100; //ja sākas jaunā rindā un tas nav kuģa pirmais elements, tad skipojam. Tas arī čeko, vai indekss neaiziet aiz 99, kas ir outOfBounds
            if(map[shipStartPos + i] != 0) return 100; //ja lauciņš aizņemts, skipojam
        }
        return shipStartPos;
    }

    public static int findPlaceForShipVertical(int shipLength, int shipStartPos, int[] map) {
        System.out.println("Vertical " + shipLength);
        if(map[shipStartPos] == 0 && shipLength == 1) { //ja mīna un nav aizņemts lauciņš, apstiprinam
            return shipStartPos;
        }
        for(int i = 0; i < shipLength * 10; i = i + 10) { //iterē cauri kuģa garumam, apskatot katru kuģim vajadzīgo laukumu
            if(shipStartPos + i > 99) return 100; //ja iziet ārpus laukuma rāmjiem, tas arī čeko vai index neiziet aiz 99, kas ir outOfBounds
            if(map[shipStartPos + i] != 0) return 100; //ja lauciņš aizņemts, skipojam
        }
        return shipStartPos;
    }

    public static void updateMapHorizontal(int shipLength, int shipStartPos, int[] map) { //šobrīd jau ir zināms, ka kuģis ielien pozīcijā, zinot sākuma punktu, garumu un virzienu
        for(int i = 0; i < shipLength; i++) {
            int curPos = shipStartPos + i; //konkrētais lauciņš, kuru apskatam
            if(shipLength == 1) map[shipStartPos] = 5; //ja ir mīna, tad marķējam
            else if(i == 0) map[curPos] = 4; //marķē kuģa kreiso galu
            else if(i == shipLength - 1) map[curPos] = 6; //marķē kuģa labo galu
            else map[curPos] = 45; //marķē kuģa vidusdaļu
            //tiek apkārt salikti marķējumi, lai kuģi nesaskartos
            if(curPos % 10 != 0) { //ja nav pirmā kolonna
                if(map[curPos - 1] == 0) map[curPos - 1] = 1; //ieliek marķējuma pa kreisi no kuģa
            }
            if(curPos % 10 != 9) { //ja nav pēdējā kolonna
                if(map[curPos + 1] == 0) map[curPos + 1] = 1; //ieliek marķējumu pa labi no kuģa
            }
            if(curPos > 9) { //ja nav pirmā rinda
                if(map[curPos - 10] == 0) map[curPos - 10] = 1; //ieliek marķējumu virs lauciņa
                if(curPos % 10 != 0) { //ja nav arī pirmā kolonna,
                    if(map[curPos - 11] == 0) map[curPos - 11] = 1; //ieliek marķējumu pa diagonāli augšā-pa kreisi
                }
                if(curPos % 10 != 9) { //ja nav pēdējā rinda,
                    if(map[curPos - 9] == 0) map[curPos - 9] = 1; //ieliek marķējumu pa diagonāli augšā-pa labi
                }
            }
            if(curPos < 90) { //ja nav pēdējā rinda
                if(map[curPos + 10] == 0) map[curPos + 10] = 1; //ieliek marķējumu zem lauciņa
                if(curPos % 10 != 0) { //ja nav pirmā kolonna
                    if(map[curPos + 9] == 0) map[curPos + 9] = 1; //ieliek marķējumu pa diagonāli lejā-pa kreisi
                }
                if(curPos % 10 != 9) { //ja nav pēdējā kolonna
                    if(map[curPos + 11] == 0) map[curPos + 11] = 1; //ieliek marķējumu pa diagonāli lejā-pa labi
                }
            }
        }
    }

    public static void updateMapVertical(int shipLength, int shipStartPos, int[] map) {
        for(int i = 0; i < shipLength * 10; i = i + 10) {
            int curPos = shipStartPos + i; //konkrētais lauciņš, kuru apskatam
            if(shipLength == 1) map[shipStartPos] = 5; //ja ir mīna, tad marķējam
            else if(i == 0) map[curPos] = 8; //marķē kuģa augšējo galu
            else if(i == shipLength * 10 - 10) map[curPos] = 2; //marķē kuģa apakšējo galu
            else map[curPos] = 85; //marķē kuģa vidusdaļu
            //tiek apkārt salikti marķējumi, lai kuģi nesaskartos
            System.out.println("curPos = " + curPos);
            if(curPos % 10 != 0) { //ja nav pirmā kolonna
                if(map[curPos - 1] == 0) map[curPos - 1] = 1; //ieliek marķējuma pa kreisi no kuģa
            }
            if(curPos % 10 != 9) { //ja nav pēdējā kolonna
                if(map[curPos + 1] == 0) map[curPos + 1] = 1; //ieliek marķējumu pa labi no kuģa
            }
            if(curPos > 9) { //ja nav pirmā rinda
                if(map[curPos - 10] == 0) map[curPos - 10] = 1; //ieliek marķējumu virs lauciņa
                if(curPos % 10 != 0) { //ja nav arī pirmā kolonna,
                    if(map[curPos - 11] == 0) map[curPos - 11] = 1; //ieliek marķējumu pa diagonāli augšā-pa kreisi
                }
                if(curPos % 10 != 9) { //ja nav pēdējā rinda,
                    if(map[curPos - 9] == 0) map[curPos - 9] = 1; //ieliek marķējumu pa diagonāli augšā-pa labi
                }
            }
            if(curPos < 90) { //ja nav pēdējā rinda
                if(map[curPos + 10] == 0) map[curPos + 10] = 1; //ieliek marķējumu zem lauciņa
                if(curPos % 10 != 0) { //ja nav pirmā kolonna
                    if(map[curPos + 9] == 0) map[curPos + 9] = 1; //ieliek marķējumu pa diagonāli lejā-pa kreisi
                }
                if(curPos % 10 != 9) { //ja nav pēdējā kolonna
                    if(map[curPos + 11] == 0) map[curPos + 11] = 1; //ieliek marķējumu pa diagonāli lejā-pa labi
                }
            }
        }
    }

    public static void populateMap(int[] map) {
        for(int i = 0; i < 100; i++){
            map[i] = 0;
        }
    }

    public static void printMap(int[] map) {
        for(int i = 0; i < 100; i++){
            System.out.print(map[i] + " ");
            if(i % 10 == 9) System.out.println();
        }
        System.out.println();
    }

}
