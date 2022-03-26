package lv.kristaps.battleship;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter extends BaseAdapter {

    private final Context mContext;
    private final int[] playerMap;
    private final int[] imageid;

    // 1
    public Adapter(Context context, int[] playerMap, int[] Imageid) {
        this.mContext = context;
        this.playerMap = playerMap;
        this.imageid = Imageid;
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
//        ImageView dummyImgView = new ImageView(mContext);
//        ImageView imageView = (ImageView);
//        View gridView;
//        gridView.findViewById(R.id.grid_item_image);
//        dummyImgView.setImageResource(imageid[position]);
        TextView dummyTextView = new TextView(mContext);
        dummyTextView.setText(String.valueOf(playerMap[position]));
//        return dummyImgView;
        ImageView mImageView;

        if (convertView == null) {
            mImageView = new ImageView(mContext);
            mImageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setPadding(0, 0, 0, 0);
        } else {
            mImageView = (ImageView) convertView;
        }
        switch (playerMap[position]){
            case -3:
                mImageView.setImageResource(imageid[2]);
                break;
            case -1:
                mImageView.setImageResource(imageid[26]);
                break;
            case 0:
                mImageView.setImageResource(imageid[0]);
                break;
            case 1:
                mImageView.setImageResource(imageid[0]);
                break;
            case 2:
                mImageView.setImageResource(imageid[5]);
                break;
            case 4:
                mImageView.setImageResource(imageid[7]);
                break;
            case 5:
                mImageView.setImageResource(imageid[13]);
                break;
            case 6:
                mImageView.setImageResource(imageid[3]);
                break;
            case 8:
                mImageView.setImageResource(imageid[9]);
                break;
            case 45:
                mImageView.setImageResource(imageid[11]);
                break;
            case 85:
                mImageView.setImageResource(imageid[12]);
                break;
            case 99:
                mImageView.setImageResource(imageid[1]);
                break;
            default:
        }

        return mImageView;
    }
}