package com.wellstech.tpictest.utils;

import android.content.Context;
import com.wellstech.tpictest.R;
import java.util.HashMap;
import java.util.Map;

public class ChildOrderConvert {

    /**
     * Convert int value to order String
     * @param context require Context to getString Resource
     * @param orderString "0" ~ "9"
     * @return "첫째" ~ "열째"
     */
    public static String ConvertIntToOrder(Context context, String orderString) {
        Map<Integer, String> ChildOrder = new HashMap<>();
        String[] childOrder = context.getResources().getStringArray(R.array.txt_my_kids_order);
        for (int index=0; index < childOrder.length; index++) {
            ChildOrder.put(index, childOrder[index]);
        }

        return ChildOrder.get(Integer.parseInt(orderString));
    }
}
