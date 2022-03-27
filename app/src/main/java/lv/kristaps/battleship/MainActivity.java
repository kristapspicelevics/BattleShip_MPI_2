package lv.kristaps.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    GridView gridView;
    static int[] playerMap = new int [100];
    static int[] computerMap = new int [100];
    static int[] map = new int [100];
    String funeral = "";
    //static int[] mapIndex = new int [100];
    TextView scoreText;
    int computerScore = 0;
    int playerScore = 0;
    int score = 0;
    String computerScoreString;
    String playerScoreString;
    String scoreString;
    Button button, buttonPlayer, buttonCPU;
    Adapter adapter;
    SinkingShip sinkingShip;
    int gridIndex;
    boolean isPlayer = true;
    boolean didWin = false;
    int[] imageId = {
            R.drawable.blue, //0
            R.drawable.fire_in_water,
            R.drawable.missed_shot,
            R.drawable.pakala_hor_kreisi,
            R.drawable.pakala_hor_labi,
            R.drawable.pakala_ver_augsu, // 5
            R.drawable.pakala_ver_leju,
            R.drawable.prieksa_hor_kreisi,
            R.drawable.prieksa_hor_labi,
            R.drawable.prieksa_ver_augsu,
            R.drawable.prieksa_ver_leju, // 10
            R.drawable.vidus_hor,
            R.drawable.vidus_ver,
            R.drawable.vieninieks,
            R.drawable.back_hor_kreisi_dead,
            R.drawable.back_hor_labi_dead, // 15
            R.drawable.back_vert_augsu_dead,
            R.drawable.back_vert_leju_dead,
            R.drawable.front_hor_kreisi_dead,
            R.drawable.front_hor_labi_dead,
            R.drawable.front_vert_augsu_dead, // 20
            R.drawable.front_vert_leju_dead,
            R.drawable.midle_hor_kreisi_dead,
            R.drawable.midle_hor_labi_dead,
            R.drawable.midle_ver_augsu_dead,
            R.drawable.midle_ver_leju_dead, // 25
            R.drawable.vieninieks_dead,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
//        for (Integer i=0; i<100; i++){
//            mapIndex[i]=i;
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Generator.populateMap(playerMap);
        Generator.generateMap(playerMap);
        Generator.populateMap(computerMap);
        Generator.generateMap(computerMap);
        scoreText = (TextView) findViewById(R.id.label);
        button = (Button) findViewById(R.id.button);
        buttonPlayer = (Button) findViewById(R.id.buttonPlayer);
        buttonCPU = (Button) findViewById(R.id.buttonCPU);
        gridView = (GridView)findViewById(R.id.grid_view);

        buttonPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayer = true;
                displayScore(playerScore, "Player Score: ");
                adapter = new Adapter(MainActivity.this, playerMap, computerMap, map, isPlayer, didWin, imageId);
                gridView.setAdapter(adapter);
            }
        });
        buttonCPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayer = false;
                displayScore(computerScore, "Computer Score: ");
                adapter = new Adapter(MainActivity.this, playerMap, computerMap, map, isPlayer, didWin, imageId);
                gridView.setAdapter(adapter);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computerScore = 0;
                playerScore = 0;
                displayScore(playerScore, "Player Score: ");
                displayScore(computerScore, "Computer Score: ");
                didWin = false;
                Generator.populateMap(playerMap);
                Generator.generateMap(playerMap);
                Generator.populateMap(computerMap);
                Generator.generateMap(computerMap);
                adapter = new Adapter(MainActivity.this, playerMap, computerMap, map, isPlayer, didWin, imageId);
                gridView.setAdapter(adapter);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (isPlayer){
                    map = playerMap;
                    score = playerScore;
                } else {
                    map = computerMap;
                    score = computerScore;
                }
                Toast.makeText(MainActivity.this, "" + map[position], Toast.LENGTH_SHORT).show();

                gridIndex = position;//saglabā to vērtību, kas ir pirms šaviena
//
                //VAJAG PADOT LĪDZI TO KEYVALUE KAUT KĀ, JO PATREIZ IR PIEEJAMS TIKAI TAS CIPARS, KAS IR
                //ATTIECĪGAJĀ KVADRANTĀ

                //-3 apzīmē aizšautu garām lauku
                //-1 apzīmē nogrimušu kuģi
                //99 apzīmē ievainotu kuģi
//                if (map[position] == 1 || map[position] == 0) {
//                    map[position] = -3; // ja ir 1 vai 0, tad tur bus garām aizsauts un būs udens
//                }else if(map[position] == -1 || map[position] == 99 || map[position] == -3 ){ //šauj pa lauku, kuram jau ir trāpīts
//                    //tad netiek netekas mainīts un atkārtoti var šaut, jo divreiz pa vienu un to pašu lauku nevar šaut
//                }else if (map[position] == 5){ //kuģis grimst
//                    map[position] = -1;
//                }else {
//                    map[position] = 99;
//                    //isSunk();
//                }
//                switch (map[position]){
//                    case 1:
//                        gridIndex = map[position];//saglabā to vērtību, kas bija iepriekš
//                        map[position] = -1;// ja ir 1 vai 0, tad tur bus garām aizsauts un būs udens
//                        break;
//                    case 45:
//                        map[position] = 99;
//                        break;
//                    default:
//                        //tad netiek netekas mainīts un atkārtoti var šaut, jo divreiz pa vienu un to pašu lauku nevar šaut
//                }
                if (map[position] == 1 || map[position] == 0) {
                    map[position] = -3; // ja ir 1 vai 0, tad tur bus garām aizsauts un būs udens
//                }else if(playerMap[position] == -1 || playerMap[position] == 99 || playerMap[position] == -3 ){ //šauj pa lauku, kuram jau ir trāpīts
                    //tad netiek netekas mainīts un atkārtoti var šaut, jo divreiz pa vienu un to pašu lauku nevar šaut
                }else if (map[position] == 5){ //kuģis grimst
                    map[position] = -1;
                    addScore(isPlayer);

                }else {
                    boolean a = SinkingShip.isSunk(position,map);
                    Toast.makeText(MainActivity.this, "" + a, Toast.LENGTH_SHORT).show();
                    funeral = "";
                    addScore(isPlayer);
                }
                gridView.setAdapter(adapter);
            }
        });
    }

    public void addScore(boolean isPlayer){
        if (isPlayer){
            playerScore++;
            displayScore(playerScore, "Player Score: ");
            if(playerScore >= 20){
                didWin = true;
                adapter = new Adapter(MainActivity.this, playerMap, computerMap, map, isPlayer, didWin, imageId);
            }
        } else {
            computerScore++;
            displayScore(computerScore, "Computer Score: ");
            if(computerScore >= 20){
                didWin = true;
                adapter = new Adapter(MainActivity.this, playerMap, computerMap, map, isPlayer, didWin, imageId);
            }
        }
    }

    public void displayScore(int score, String text){
        String scoreString = String.valueOf(score);
        scoreText.setText(text + scoreString);
    }

}
