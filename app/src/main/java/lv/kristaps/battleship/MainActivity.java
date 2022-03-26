package lv.kristaps.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    GridView gridView;
    static int[] playerMap = new int [100];
    //static int[] mapIndex = new int [100];
    TextView text;
    Button button;
    Adapter adapter;
    int gridIndex;
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
        text = (TextView) findViewById(R.id.label);
        button = (Button) findViewById(R.id.button);
        gridView = (GridView)findViewById(R.id.grid_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Generator.populateMap(playerMap);
                Generator.generateMap(playerMap);

                adapter = new Adapter(MainActivity.this, playerMap, imageId);
                gridView.setAdapter(adapter);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(MainActivity.this, "" + playerMap[position], Toast.LENGTH_SHORT).show();

                gridIndex = position;//saglabā to vērtību, kas ir pirms šaviena
//
                //VAJAG PADOT LĪDZI TO KEYVALUE KAUT KĀ, JO PATREIZ IR PIEEJAMS TIKAI TAS CIPARS, KAS IR
                //ATTIECĪGAJĀ KVADRANTĀ

                //-3 apzīmē aizšautu garām lauku
                //-1 apzīmē nogrimušu kuģi
                //99 apzīmē ievainotu kuģi
                if (playerMap[position] == 1 || playerMap[position] == 0) {
                    playerMap[position] = -3; // ja ir 1 vai 0, tad tur bus garām aizsauts un būs udens
                }else if(playerMap[position] == -1 || playerMap[position] == 99 || playerMap[position] == -3 ){ //šauj pa lauku, kuram jau ir trāpīts
                    //tad netiek netekas mainīts un atkārtoti var šaut, jo divreiz pa vienu un to pašu lauku nevar šaut
                }else if (playerMap[position] == 5){ //kuģis grimst
                    playerMap[position] = -1;
                }else {
                    playerMap[position] = 99;
                    //isSunk();
                }
                switch (playerMap[position]){
                    case 1:
                        gridIndex = playerMap[position];//saglabā to vērtību, kas bija iepriekš
                        playerMap[position] = -1;// ja ir 1 vai 0, tad tur bus garām aizsauts un būs udens
                        break;
                    case 45:
                        playerMap[position] = 99;
                        break;
                    default:
                        //tad netiek netekas mainīts un atkārtoti var šaut, jo divreiz pa vienu un to pašu lauku nevar šaut
                }
                gridView.setAdapter(adapter);
            }
        });
    }


}
