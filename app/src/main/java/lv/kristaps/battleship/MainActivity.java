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
    AI ai;
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
        ai = new AI();
        buttonPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayer = true;
                displayScore(ai.computerScore, "Computer Score: ");
                adapter = new Adapter(MainActivity.this, playerMap, computerMap, map, isPlayer, didWin, imageId);
                gridView.setAdapter(adapter);
            }
        });
        buttonCPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayer = false;
                displayScore(playerScore, "Player Score: ");

                adapter = new Adapter(MainActivity.this, playerMap, computerMap, map, isPlayer, didWin, imageId);
                gridView.setAdapter(adapter);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ai.computerScore = 0;
                playerScore = 0;
                if (isPlayer){
                    displayScore(ai.computerScore, "Computer Score: ");
                } else {
                    displayScore(playerScore, "Player Score: ");
                }
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
                    score = ai.computerScore;
                }
                Toast.makeText(MainActivity.this, "" + map[position], Toast.LENGTH_SHORT).show();

                gridIndex = position;//saglabā to vērtību, kas ir pirms šaviena
                checkIfHit(map, position);
                gridView.setAdapter(adapter);
            }
        });
    }

    public void checkIfHit(int[] map, int position){
        if (map[position] == 1 || map[position] == 0) {
            map[position] = -3; // ja ir 1 vai 0, tad tur bus garām aizsauts un būs udens
            if(!isPlayer){
                ai.AITurn(playerMap);
            }
        }else if (map[position] == 5){ //kuģis grimst
            map[position] = -1;
            addScore();
        }else {
            sinkingShip.isSunk(position,map);
            addScore();
        }
        if(playerScore >= 20 || ai.computerScore >= 20){
            didWin = true;
            adapter = new Adapter(MainActivity.this, playerMap, computerMap, map, isPlayer, didWin, imageId);
        }
    }

    public void addScore(){
        if (!isPlayer){
            playerScore++;
            displayScore(playerScore, "Player Score: ");
        } else {
            System.out.println("Score "+ai.computerScore);
            displayScore(ai.computerScore, "Computer Score: ");
        }
    }

    public void displayScore(int score, String text){
        String scoreString = String.valueOf(score);
        scoreText.setText(text + scoreString);
    }

}
