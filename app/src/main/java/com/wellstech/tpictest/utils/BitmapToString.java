package com.wellstech.tpictest.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class BitmapToString {
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
}
