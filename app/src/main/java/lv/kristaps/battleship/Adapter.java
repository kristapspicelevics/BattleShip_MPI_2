package lv.kristaps.battleship;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter extends BaseAdapter {

    private final Context mContext;
    private final int[] playerMap;

    // 1
    public Adapter(Context context, int[] playerMap) {
        this.mContext = context;
        this.playerMap = playerMap;
    }

    // 2
    @Override
    public int getCount() {
        return playerMap.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView dummyTextView = new TextView(mContext);
        dummyTextView.setText(String.valueOf(playerMap[position]));
        return dummyTextView;
    }
}