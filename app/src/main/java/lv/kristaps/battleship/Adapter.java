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
    private final int[] computerMap;
    private final int[] imageid;
    private final boolean isPlayer;
    private final boolean didWin;
    private int[] map;
    // 1
    public Adapter(Context context, int[] playerMap, int[] computerMap, int[] map, boolean isPlayer, boolean didWin, int[] Imageid) {
        this.mContext = context;
        this.playerMap = playerMap;
        this.computerMap = computerMap;
        this.isPlayer = isPlayer;
        this.didWin = didWin;
        this.imageid = Imageid;
        this.map = map;
    }

    // 2
    @Override
    public int getCount() {
        if (isPlayer){
            map = playerMap;
        } else {
            map = computerMap;
        }
        return map.length;
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

    public boolean isEnabled(int position) {
        if (isPlayer){
            map = playerMap;
        } else {
            map = computerMap;
        }
        if(didWin == true || isPlayer == true){
            if ((map[position] >= -100 && map[position] <= 100)) {
                return false;
            }
        } else {
            if ((map[position] >= -8 && map[position] <= -1) || map[position] == 99 || map[position] == -45 || map[position] == -85) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView mImageView;
        if (convertView == null) {
            mImageView = new ImageView(mContext);
            mImageView.setLayoutParams(new GridView.LayoutParams(110, 100));
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            int padding_in_dp = 3;  // 6 dps
//            final float scale = mContext.getResources().getDisplayMetrics().density;
//            int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
            mImageView.setPadding(0, 0, 0, 0);
        } else {
            mImageView = (ImageView) convertView;
        }
    //    mImageView = showPlayerImages(playerMap,mImageView,position);
        if (isPlayer) {
            mImageView = showImages(playerMap, -2, mImageView, imageid[6], position); // ku??a apak????jais gals (nogrimis)
            mImageView = showImages(playerMap, -4, mImageView, imageid[8], position); // ku??a kreisais gals (nogrimis)
            mImageView = showImages(playerMap, -6, mImageView, imageid[4], position); // ku??a labais gals (nogrimis)
            mImageView = showImages(playerMap, -8, mImageView, imageid[10], position); // ku??a aug????jais gals (nogrimis)
            mImageView = showImages(playerMap, -45, mImageView, imageid[12], position); // ku??a vidus horizont??li (nogrimis)
            mImageView = showImages(playerMap, -85, mImageView, imageid[14], position); // ku??a vidus vertik??li (nogrimis)
            mImageView = showImages(playerMap, -3, mImageView, imageid[2], position); // ????viens gar??m
            mImageView = showImages(playerMap, -1, mImageView, imageid[16], position); // uzspr??gusi m??na
            mImageView = showImages(playerMap, 0, mImageView, imageid[0], position); // ??dens
            mImageView = showImages(playerMap, 1, mImageView, imageid[0], position); // ??dens ap ku??i
            mImageView = showImages(playerMap, 2, mImageView, imageid[5], position); // ku??a apak????jais gals
            mImageView = showImages(playerMap, 4, mImageView, imageid[7], position); // ku??a kreisais gals
            mImageView = showImages(playerMap, 5, mImageView, imageid[15], position); // m??na
            mImageView = showImages(playerMap, 6, mImageView, imageid[3], position); // ku??a labais gals
            mImageView = showImages(playerMap, 8, mImageView, imageid[9], position); // ku??a aug????jais gals
            mImageView = showImages(playerMap, 45, mImageView, imageid[11], position); // ku??a vidus horizont??li
            mImageView = showImages(playerMap, 85, mImageView, imageid[13], position); // ku??a vidus vertik??li
            mImageView = showImages(playerMap, 99, mImageView, imageid[1], position); // ievainots ku??is
        } else {
            mImageView = showImages(computerMap, -2, mImageView, imageid[6], position); // ku??a apak????jais gals (nogrimis)
            mImageView = showImages(computerMap, -4, mImageView, imageid[8], position); // ku??a kreisais gals (nogrimis)
            mImageView = showImages(computerMap, -6, mImageView, imageid[4], position); // ku??a labais gals (nogrimis)
            mImageView = showImages(computerMap, -8, mImageView, imageid[10], position); // ku??a aug????jais gals (nogrimis)
            mImageView = showImages(computerMap, -45, mImageView, imageid[12], position); // ku??a vidus horizont??li (nogrimis)
            mImageView = showImages(computerMap, -85, mImageView, imageid[14], position); // ku??a vidus vertik??li (nogrimis)
            mImageView = showImages(computerMap, -3, mImageView, imageid[2], position); // ????viens gar??m
            mImageView = showImages(computerMap, -1, mImageView, imageid[16], position); // uzspr??gusi m??na
            mImageView = showImages(computerMap, 0, mImageView, imageid[0], position); // ??dens
            mImageView = showImages(computerMap, 1, mImageView, imageid[0], position); // ??dens ap ku??i
            mImageView = showImages(computerMap, 2, mImageView, imageid[0], position); // ku??a apak????jais gals
            mImageView = showImages(computerMap, 4, mImageView, imageid[0], position); // ku??a kreisais gals
            mImageView = showImages(computerMap, 5, mImageView, imageid[0], position); // m??na
            mImageView = showImages(computerMap, 6, mImageView, imageid[0], position); // ku??a labais gals
            mImageView = showImages(computerMap, 8, mImageView, imageid[0], position); // ku??a aug????jais gals
            mImageView = showImages(computerMap, 45, mImageView, imageid[0], position); // ku??a vidus horizont??li
            mImageView = showImages(computerMap, 85, mImageView, imageid[0], position); // ku??a vidus vertik??li
            mImageView = showImages(computerMap, 99, mImageView, imageid[1], position); // ievainots ku??is
        }
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