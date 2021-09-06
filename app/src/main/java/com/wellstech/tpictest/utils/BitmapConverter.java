package com.wellstech.tpictest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;

public class BitmapConverter {
    /**비트맵을 바이너리 바이트배열로 바꾸어주는 메서드 */
    public static String bitmapToByteArray(Bitmap bitmap) {
        String image;
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        image = byteArrayToBinaryString(byteArray);
        return image;
    }

    /**바이너리 바이트 배열을 스트링으로 바꾸어주는 메서드 */
    private static String byteArrayToBinaryString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte value : b) {
            sb.append(byteToBinaryString(value));
        }
        return sb.toString();
    }

    /**바이너리 바이트를 스트링으로 바꾸어주는 메서드 */
    private static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((n >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

    public static Bitmap resize(Context context, Bitmap bm) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        layoutParams.width = (int) (displayMetrics.widthPixels * 0.5);
        layoutParams.height = (int) (displayMetrics.heightPixels * 0.5);

        bm = Bitmap.createScaledBitmap(bm, layoutParams.width, layoutParams.height, true);
        return bm;
    }

    public static Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

//    private Bitmap resize(Bitmap bm){
//        Configuration config=getResources().getConfiguration();
//        if(config.smallestScreenWidthDp>=800)
//            bm = Bitmap.createScaledBitmap(bm, 400, 240, true);
//        else if(config.smallestScreenWidthDp>=600)
//            bm = Bitmap.createScaledBitmap(bm, 300, 180, true);
//        else if(config.smallestScreenWidthDp>=400)
//            bm = Bitmap.createScaledBitmap(bm, 200, 120, true);
//        else if(config.smallestScreenWidthDp>=360)
//            bm = Bitmap.createScaledBitmap(bm, 180, 108, true);
//        else
//            bm = Bitmap.createScaledBitmap(bm, 160, 96, true);
//        return bm;
//    }
}
