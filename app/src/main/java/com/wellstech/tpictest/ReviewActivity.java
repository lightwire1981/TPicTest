package com.wellstech.tpictest;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wellstech.tpictest.db.DBRequestType;
import com.wellstech.tpictest.db.DatabaseRequest;
import com.wellstech.tpictest.list_code.ListAdapterEvalChild;
import com.wellstech.tpictest.list_code.ListItemEvalChild;
import com.wellstech.tpictest.list_code.RecyclerDecoration;
import com.wellstech.tpictest.utils.ChildOrderConvert;
import com.wellstech.tpictest.utils.PreferenceSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    private JSONObject GoodsInfo;
    ArrayList<String> useChildId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getGoodsInfo();
        setWidget();

        hideNavigationBar();
    }

    private void getGoodsInfo() {
        try {
            GoodsInfo = new JSONObject(getIntent().getStringExtra("goodsInfo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setWidget() {
        try {
            Glide.with(getBaseContext()).
                    load(GoodsInfo.getString("detailImageData")).
                    placeholder(R.drawable.tp_icon_brand01_on).
                    diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                    into((ImageView)findViewById(R.id.iVwReviewGoodsImg));
            ((TextView)findViewById(R.id.tVwReviewGoodsName)).setText(GoodsInfo.getString("goodsNm"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView childListView = findViewById(R.id.rclVwReviewChildList);
        setLayoutManager(childListView);
        getChildInfo(childListView);

        findViewById(R.id.iBtnReviewExit).setOnClickListener(v -> finish());
    }

    private void getChildInfo(RecyclerView childListView) {
        String userInfo = PreferenceSetting.loadPreference(getBaseContext(), PreferenceSetting.PREFERENCE_KEY.USER_INFO);
        JSONObject object = new JSONObject();
        try {
            object.put("id", (new JSONObject(userInfo)).getString("id"));
            new DatabaseRequest(getBaseContext(), result -> {
                if(result[0].equals("NO_CHILD")) {
                    return;
                }
                ArrayList<ListItemEvalChild> mList = new ArrayList<>();
                try {
                    JSONArray childList = new JSONArray(result[0]);
                    for(int index=0; index<childList.length(); index++) {
                        JSONObject child = childList.getJSONObject(index);
                        ListItemEvalChild item = new ListItemEvalChild();
                        item.setChildIdx(child.getString("idx"));
                        item.setChildOrder(ChildOrderConvert.ConvertIntToOrder(getBaseContext(), child.getString("child_order")));
                        item.setChildNick(child.getString("child_nick"));
                        mList.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ListAdapterEvalChild listAdapterEvalChild = new ListAdapterEvalChild(mList, result[0], checkBoxSelectListener);
                childListView.setAdapter(listAdapterEvalChild);
            }).execute(DBRequestType.GET_CHILD.name(), object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private final ListAdapterEvalChild.CheckBoxSelectListener checkBoxSelectListener = new ListAdapterEvalChild.CheckBoxSelectListener() {

        @Override
        public void onSelectedPosition(CheckBox checkBox, int position) {

        }

        @Override
        public void onSelectedCheckBox(CheckBox checkBox, boolean isChecked) {
            if (isChecked) {
                useChildId.add(checkBox.getTag().toString());
            } else {
                useChildId.remove(checkBox.getTag().toString());
            }

        }
    };


    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(25, 0));
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}