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


    public boolean isEnabled(int position) {
        if (playerMap[position] == -3 || playerMap[position] == -1 || playerMap[position] == 99) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView mImageView;
        if (convertView == null) {
            mImageView = new ImageView(mContext);
            mImageView.setLayoutParams(new GridView.LayoutParams(110, 100));
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setPadding(0, 0, 0, 0);
        } else {
            mImageView = (ImageView) convertView;
        }
    //    mImageView = showPlayerImages(playerMap,mImageView,position);
        mImageView = showImages(playerMap,-3,mImageView,imageid[2],position);
        mImageView = showImages(playerMap,-1,mImageView,imageid[26],position);
        mImageView = showImages(playerMap,0,mImageView,imageid[0],position);
        mImageView = showImages(playerMap,1,mImageView,imageid[0],position);
        mImageView = showImages(playerMap,2,mImageView,imageid[5],position);
        mImageView = showImages(playerMap,4,mImageView,imageid[7],position);
        mImageView = showImages(playerMap,5,mImageView,imageid[13],position);
        mImageView = showImages(playerMap,6,mImageView,imageid[3],position);
        mImageView = showImages(playerMap,8,mImageView,imageid[9],position);
        mImageView = showImages(playerMap,45,mImageView,imageid[11],position);
        mImageView = showImages(playerMap,85,mImageView,imageid[12],position);
        mImageView = showImages(playerMap,99,mImageView,imageid[1],position);

        return mImageView;
    }

    private ImageView showImages(int[] map, int value, ImageView mImageView, int image, int position) {
        if (map[position] == value) {
            mImageView.setImageResource(image);
        }
        return mImageView;
    }

//    private ImageView showPlayerImages(int[] map, ImageView mImageView, int position) {
//        switch (map[position]){
//            case -3:
//                mImageView.setImageResource(imageid[2]);
//                break;
//            case -1:
//                mImageView.setImageResource(imageid[26]);
//                break;
//            case 0:
//                mImageView.setImageResource(imageid[0]);
//                break;
//            case 1:
//                mImageView.setImageResource(imageid[0]);
//                break;
//            case 2:
//                mImageView.setImageResource(imageid[5]);
//                break;
//            case 4:
//                mImageView.setImageResource(imageid[7]);
//                break;
//            case 5:
//                mImageView.setImageResource(imageid[13]);
//                break;
//            case 6:
//                mImageView.setImageResource(imageid[3]);
//                break;
//            case 8:
//                mImageView.setImageResource(imageid[9]);
//                break;
//            case 45:
//                mImageView.setImageResource(imageid[11]);
//                break;
//            case 85:
//                mImageView.setImageResource(imageid[12]);
//                break;
//            case 99:
//                mImageView.setImageResource(imageid[1]);
//                break;
//            default:
//        }
//        return mImageView;
//    }
}