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
    TextView text;
    Button button;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

                adapter = new Adapter(MainActivity.this, playerMap);
                gridView.setAdapter(adapter);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(MainActivity.this, "" + playerMap[position], Toast.LENGTH_SHORT).show();
                playerMap[position] = -1;
                gridView.setAdapter(adapter);
            }
        });
    }


}
