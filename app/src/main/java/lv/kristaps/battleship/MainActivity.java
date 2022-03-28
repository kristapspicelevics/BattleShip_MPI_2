package lv.kristaps.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    static int[] playerMap = new int [100];
    static int[] computerMap = new int [100];
    static int[] map = new int [100];
    String funeral = "";
    Handler handler = new Handler();
    Runnable runnable;
    //static int[] mapIndex = new int [100];
    TextView playerScoreText;
    TextView cpuScoreText;
    int maxPoints = 20;
    int playerScore = 0;
    int score = 0;
    int delay = 2000;
    String computerScoreString = "Computer: ";
    String playerScoreString = "Player: ";
    String win = "win";
    String lose = "lose";
    String surrender= "surrender";
    String scoreString;
    Button buttonRandom, buttonStart, buttonSurrender, buttonAlert;
    AI ai;
    Adapter adapter;
    SinkingShip sinkingShip;
    int gridIndex;
    boolean isPlayer = true;
    boolean didWin = false;
    MediaPlayer music;
    MediaPlayer music1;
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
        buttonRandom = (Button) findViewById(R.id.buttonRandom);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonSurrender = (Button) findViewById(R.id.buttonSurrender);
        buttonAlert = (Button) findViewById(R.id.buttonAlert);
        gridView = (GridView)findViewById(R.id.grid_view);
        ai = new AI();
        displayScores();
        generateMaps();
        setGrid();
        buttonSurrender.setEnabled(false);
        //alert = (Button)findViewById(R.id.btnAlert);
        buttonAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Instructions");
                alertDialog.setMessage(getString(R.string.instruction));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okey",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which){
                            dialog.dismiss();
                        }
                    });
                alertDialog.show();
            }
        });
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ai.computerScore = 0;
                playerScore = 0;
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Let's start game!");
                alertDialog.setMessage(getString(R.string.start_game));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okey",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which){
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                isPlayer = false;
                setGrid();
                gridView.setEnabled(true);
            }
        });

        buttonSurrender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result(surrender, ai.computerScore, playerScore);
                buttonStart.setEnabled(false);
                buttonRandom.setEnabled(true);
                handler.removeCallbacksAndMessages(null);
            }
        });
        buttonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayer = true;
                ai.computerScore = 0;
                playerScore = 0;
                didWin = false;
                displayScores();
                generateMaps();
                setGrid();
                buttonStart.setEnabled(true);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                buttonStart.setEnabled(false);
                buttonRandom.setEnabled(false);
                buttonSurrender.setEnabled(true);
                if (isPlayer){
                    map = playerMap;
                    score = playerScore;
                } else {
                    map = computerMap;
                    score = ai.computerScore;
                }
            //    Toast.makeText(MainActivity.this, "" + map[position], Toast.LENGTH_SHORT).show();
                checkIfHit(map, position);
                gridIndex = position;//saglabā to vērtību, kas ir pirms šaviena
                gridView.setAdapter(adapter);
            }
        });
    }

    public void setGrid(){
        adapter = new Adapter(MainActivity.this, playerMap, computerMap, map, isPlayer, didWin, imageId);
        gridView.setAdapter(adapter);
    }

    public void generateMaps() {
        Generator.populateMap(playerMap);
        Generator.generateMap(playerMap);
        Generator.populateMap(computerMap);
        Generator.generateMap(computerMap);
    }

    public void displayScores() {
        displayScore(playerScore, playerScoreString, playerScoreText);
        displayScore(ai.computerScore, computerScoreString, cpuScoreText);
    }

    public void checkIfHit(int[] map, int position){
        music = MediaPlayer.create(MainActivity.this, R.raw.gunshot);
        music1 = MediaPlayer.create(MainActivity.this, R.raw.water);
        if (map[position] == 1 || map[position] == 0) {
            map[position] = -3; // ja ir 1 vai 0, tad tur bus garām aizsauts un būs udens
            music1.start();//water
            gridView.setEnabled(false);
            if(!isPlayer) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isPlayer = true;
                        setGrid();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ai.AITurn(playerMap, music, music1);
                                setGrid();
                                displayScores();
                            }
                        }, delay);
                        displayScores();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isPlayer = false;
                                setGrid();
//                                displayScore(playerScore, playerScoreString, playerScoreText);
//                                displayScore(ai.computerScore, computerScoreString, cpuScoreText);
                                gridView.setEnabled(true);
                            }
                        }, delay + delay);
                    }
                }, delay);
            }
        }else if (map[position] == 5){ // trāpa mīnai
            map[position] = -1;
            playerScore++;
            displayScores();
            music.start();//gun
        }else {
            sinkingShip.isSunk(position,map);
            playerScore++;
            displayScores();
            music.start();//gun

        }
        if (playerScore >= maxPoints){
            result(win, ai.computerScore, playerScore);
            didWin = true;
            setGrid();

        }else if(ai.computerScore >= maxPoints){
            result(lose, ai.computerScore, playerScore);
            didWin = true;
            setGrid();
        }
    }

    public void displayScore(int score, String text, TextView scoreText){
        String scoreString = String.valueOf(score);
        scoreText.setText(text + scoreString);
    }

    public void result(String result, int pc, int player){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(getString(R.string.end));
        if (result.equals(win)) {
            alertDialog.setMessage("Congratulations, you " +result+ "!\n" + "Result was " +player+ ":"+ pc);
        } else {
            alertDialog.setMessage("You " +result+ "!\n" + "Result was " +player+ ":"+ pc);
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okey",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        buttonRandom.setEnabled(true);
        buttonSurrender.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.quit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
