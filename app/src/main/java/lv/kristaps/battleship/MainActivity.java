package lv.kristaps.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    GridView gridView;
    static int[] playerMap = new int [100];
    static int[] computerMap = new int [100];
    static int[] map = new int [100];
    String funeral = "";
    //static int[] mapIndex = new int [100];
    TextView playerScoreText;
    TextView cpuScoreText;
    int maxPoints = 20;
    int playerScore = 0;
    int score = 0;
    String computerScoreString = "Computer: ";
    String playerScoreString = "Player: ";
    String scoreString;
    Button button, buttonPlayer, buttonCPU, buttonAlert;
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
            R.drawable.back_hor_kreisi,
            R.drawable.back_hor_kreisi_dead,
            R.drawable.back_vert_augsu, // 5
            R.drawable.back_vert_augsu_dead,
            R.drawable.front_hor_kreisi,
            R.drawable.front_hor_kreisi_dead,
            R.drawable.front_vert_augsu,
            R.drawable.front_vert_augsu_dead, // 10
            R.drawable.midle_hor,
            R.drawable.midle_hor_dead,
            R.drawable.midle_vert,
            R.drawable.midle_vert_dead,
            R.drawable.mina, // 15
            R.drawable.mina_dead,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState){



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerScoreText = (TextView) findViewById(R.id.player);
        cpuScoreText = (TextView) findViewById(R.id.cpu);
        button = (Button) findViewById(R.id.button);
        buttonPlayer = (Button) findViewById(R.id.buttonPlayer);
        buttonCPU = (Button) findViewById(R.id.buttonCPU);
        buttonAlert = (Button) findViewById(R.id.buttonAlert);
        gridView = (GridView)findViewById(R.id.grid_view);
        ai = new AI();
        displayScore(playerScore, playerScoreString, playerScoreText);
        displayScore(ai.computerScore, computerScoreString, cpuScoreText);
        Generator.populateMap(playerMap);
        Generator.generateMap(playerMap);
        Generator.populateMap(computerMap);
        Generator.generateMap(computerMap);
        //alert = (Button)findViewById(R.id.btnAlert);
        buttonPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayer = true;
                adapter = new Adapter(MainActivity.this, playerMap, computerMap, map, isPlayer, didWin, imageId);
                gridView.setAdapter(adapter);
            }
        });
        buttonAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //music.start();
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Instructions");
                alertDialog.setMessage("The purpose of the game is to defeat your opponent's fleet before enemy destroyed your fleet. You can change the layout of your ships by pressing Randomize before the game starts. Attacks can be made by pressing on the playing field. You can take one shot on the opponent's playing field in one move. If the shot is accurate, it can be repeated, but if the shot is inaccurate, the move passes to the opponent's player.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okey",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which){
                                dialog.dismiss();
                        }
                    });
                alertDialog.show();
            }
        });
        buttonCPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayer = false;
                adapter = new Adapter(MainActivity.this, playerMap, computerMap, map, isPlayer, didWin, imageId);
                gridView.setAdapter(adapter);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayer = true;
                ai.computerScore = 0;
                playerScore = 0;
                displayScore(playerScore, playerScoreString, playerScoreText);
                displayScore(ai.computerScore, computerScoreString, cpuScoreText);
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
            //    Toast.makeText(MainActivity.this, "" + map[position], Toast.LENGTH_SHORT).show();

                gridIndex = position;//saglabā to vērtību, kas ir pirms šaviena
                checkIfHit(map, position);
                gridView.setAdapter(adapter);
            }
        });
    }

    public void checkIfHit(int[] map, int position){
        MediaPlayer music = MediaPlayer.create(MainActivity.this, R.raw.gunshot);
        MediaPlayer music1 = MediaPlayer.create(MainActivity.this, R.raw.water);
        if (map[position] == 1 || map[position] == 0) {
            map[position] = -3; // ja ir 1 vai 0, tad tur bus garām aizsauts un būs udens
            music1.start();//water
            if(!isPlayer){
                ai.AITurn(playerMap);
            }
        }else if (map[position] == 5){ // trāpa mīnai
            map[position] = -1;
            playerScore++;
            music.start();//gun
        }else {
            sinkingShip.isSunk(position,map);
            playerScore++;
            music.start();//gun
        }
        displayScore(playerScore, playerScoreString, playerScoreText);
        displayScore(ai.computerScore, computerScoreString, cpuScoreText);
        if(playerScore >= maxPoints || ai.computerScore >= maxPoints){
            didWin = true;
            adapter = new Adapter(MainActivity.this, playerMap, computerMap, map, isPlayer, didWin, imageId);
        }
    }


    public void displayScore(int score, String text, TextView scoreText){
        String scoreString = String.valueOf(score);
        scoreText.setText(text + scoreString);
    }

}
