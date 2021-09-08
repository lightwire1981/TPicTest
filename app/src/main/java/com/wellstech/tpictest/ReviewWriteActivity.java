package com.wellstech.tpictest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wellstech.tpictest.db.DBRequestType;
import com.wellstech.tpictest.db.DatabaseRequest;
import com.wellstech.tpictest.fragments.MyPageReviewFragment;
import com.wellstech.tpictest.list_code.ListAdapterEvalChild;
import com.wellstech.tpictest.list_code.ListAdptPhotoForReview;
import com.wellstech.tpictest.list_code.ListItemEvalChild;
import com.wellstech.tpictest.list_code.RecyclerDecoration;
import com.wellstech.tpictest.utils.BitmapConverter;
import com.wellstech.tpictest.utils.ChildOrderConvert;
import com.wellstech.tpictest.utils.CustomDialog;
import com.wellstech.tpictest.utils.PreferenceSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ReviewWriteActivity extends AppCompatActivity {

    private JSONObject GoodsInfo;
    private JSONObject ReviewInfo;
    private final ArrayList<String> useChildId = new ArrayList<>();
    private Float evalValue;
    private String reviewString = "";
    private final ArrayList<Bitmap> photoList = new ArrayList<>();
    private final ArrayList<Uri> uriList = new ArrayList<>();
    private Button btnGetPhoto;
    private RecyclerView photoView;

    private ActivityResultLauncher<Intent> resultLauncher;

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);
        getGoodsInfo();
        setPhotoButton();

        hideNavigationBar();
    }

    private void getGoodsInfo() {
        switch (getIntent().getStringExtra("CALL_TYPE")) {
            case "GoodsInfoActivity":
                ReviewInfo = GoodsInfoActivity.reviewObject;
                try {
                    GoodsInfo = new JSONObject(getIntent().getStringExtra("goodsInfo"));
                    setWidget();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "MyPageReviewFragment":
                ReviewInfo = MyPageReviewFragment.reviewObject;
                try {
                    getGoodsInfoFromDB(ReviewInfo.getString("goodsNo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "PhotoActivity":
                setWidget();
                break;
            default:
                break;
        }
    }

    private void getGoodsInfoFromDB(String goodsNo) {
        JSONObject data =  new JSONObject();
        try {
            data.put("goodsNo", goodsNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new DatabaseRequest(getBaseContext(), result -> {
            try {
                GoodsInfo = new JSONArray(result[0]).getJSONObject(0);
                setWidget();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).execute(DBRequestType.GET_GOODS_INFO.name(), data.toString());
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

        TextView evaluateValue = findViewById(R.id.tVwReviewEvaluatingValue);
        RatingBar reviewEvaluate = findViewById(R.id.rtnBrReviewEvaluating);
        reviewEvaluate.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            evaluateValue.setText(getString(R.string.txt_review_evaluate_template, String.valueOf((int)v)));
            evalValue = v;
        });
        reviewEvaluate.setRating(3f);

        TextView stringCount = findViewById(R.id.tVwReviewStringCount);
        stringCount.setText(getString(R.string.txt_review_text_limit, String.valueOf(0)));
        EditText reviewDescription = findViewById(R.id.eTxtReview);
        reviewDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                stringCount.setText(getString(R.string.txt_review_text_limit, editable.length()+""));
                reviewString = editable.toString();
            }
        });

        photoView = findViewById(R.id.rclVwReviewPhoto);
        setLayoutManager(photoView);


        ProgressDialog progressDialog = new ProgressDialog(ReviewWriteActivity.this);
        progressDialog.setMessage("리뷰를 등록 중입니다...");

        findViewById(R.id.btnReviewRegistration).setOnClickListener(view -> {
            if (useChildId.size() < 1) {
                new CustomDialog(ReviewWriteActivity.this, CustomDialog.DIALOG_CATEGORY.NONE_SELECT_CHILD, (response, data) -> hideNavigationBar()).show();
                return;
            }

            if (reviewString.isEmpty()) {
                Toast.makeText(getBaseContext(), "리뷰 내용을 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.show();
            StringBuilder childIds = new StringBuilder();
            for (String id : useChildId) {
                if (useChildId.indexOf(id) > 0) {
                    childIds.append(",");
                }
                childIds.append(id);
            }

//            ArrayList<String> bitmapString = new ArrayList<>();
            JSONArray bitmapArray = new JSONArray();
            if (photoList.size() > 0) {
                for(Bitmap bitmap : photoList) {
                    JSONObject data = new JSONObject();
                    try {
                        bitmap = BitmapConverter.resize(getBaseContext(), bitmap);
                        data.put("img", BitmapConverter.bitmapToByteArray(bitmap));
                        bitmapArray.put(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    bitmapString.add(BitmapToString.bitmapToByteArray(bitmap));
                }
            }
            JSONObject data = new JSONObject();
            try {
                JSONObject userInfo = new JSONObject(PreferenceSetting.loadPreference(getBaseContext(), PreferenceSetting.PREFERENCE_KEY.USER_INFO));
                data.put("id", userInfo.getString("id"));
                data.put("goodsNo", GoodsInfo.getString("goodsNo"));
                data.put("child_ids", childIds.toString());
                data.put("goods_evaluate", (evalValue)+"");
                data.put("review", reviewString);
                data.put("photo_data", bitmapArray.length() > 0 ? bitmapArray.toString():"");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            new DatabaseRequest(getBaseContext(), result -> {
                if (result[0].equals("INSERT_OK")) {
                    progressDialog.dismiss();
                    new CustomDialog(ReviewWriteActivity.this, CustomDialog.DIALOG_CATEGORY.INSERT_CONFIRM, (response, data1) -> this.finish()).show();
                }
            }).execute(DBRequestType.INSERT_REVIEW.name(), data.toString());
        });

        findViewById(R.id.iBtnReviewExit).setOnClickListener(v -> finish());
    }

    private void setPhotoButton() {
        btnGetPhoto = findViewById(R.id.btnAddReviewPhoto);
        btnGetPhoto.setText(getString(R.string.txt_review_photo_template, uriList.size()+""));
        btnGetPhoto.setOnClickListener(view -> {
            if (uriList.size() == 5) {
                Toast.makeText(getBaseContext(), "이미지가 가득 찼습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncher.launch(intent);
        });
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        Uri uri = intent != null ? intent.getData() : null;
                        uriList.add(uri);
                        new Thread(() -> {
                            try {
                                photoList.add(Glide.with(getBaseContext()).asBitmap().load(uri).submit().get());
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                        setPhoto();
                    }
                });
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

    private void setPhoto() {
        ListAdptPhotoForReview adapter = new ListAdptPhotoForReview(uriList, position -> {
            uriList.remove(position);
            photoList.remove(position);
            setPhoto();
        });
        btnGetPhoto.setText(getString(R.string.txt_review_photo_template, uriList.size()+""));
        photoView.setAdapter(adapter);
    }


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