package com.wellstech.tpictest.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.wellstech.tpictest.R;
import com.wellstech.tpictest.list_code.ListAdptDlgScalableGoodsImg;

import java.util.ArrayList;

public class CustomDialog extends Dialog {

    public enum DIALOG_CATEGORY {
        LOGIN,
        LOGOUT,
        PASSWORD,
        GOODS_IMAGE,
        FORM_INVALID,
        SELECT_INVALID,
        NO_CHILD_INFORM,
        EVALUATE_CONFIRM,
        EXIT
    }
    private final DIALOG_CATEGORY dialog_category;

    public interface DialogResponseListener {
        void getResponse(boolean response, Object data);
    }
    private DialogResponseListener dialogResponseListener;

    private ArrayList<Bitmap> bitmapList;

    public CustomDialog(@NonNull Context context, DIALOG_CATEGORY dialog_category) {
        super(context);
        this.dialog_category = dialog_category;
    }

    public CustomDialog(Context context, DIALOG_CATEGORY dialog_category, DialogResponseListener dialogResponseListener) {
        super(context);
        this.dialog_category = dialog_category;
        this.dialogResponseListener = dialogResponseListener;
    }

    public CustomDialog(Context context, DIALOG_CATEGORY dialog_category, DialogResponseListener dialogResponseListener, ArrayList<Bitmap> bitmapList) {
        super(context);
        this.dialog_category = dialog_category;
        this.dialogResponseListener = dialogResponseListener;
        this.bitmapList = bitmapList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationBar();

        switch (dialog_category) {
            case LOGIN:
                setContentView(R.layout.dialog_login_confirm);
                findViewById(R.id.btnDlgLoginNo).setOnClickListener(onClickListener);
                findViewById(R.id.btnDlgLoginYes).setOnClickListener(onClickListener);
                break;
            case LOGOUT:
                setContentView(R.layout.dialog_logout_confirm);
                findViewById(R.id.btnDlgLogoutNo).setOnClickListener(onClickListener);
                findViewById(R.id.btnDlgLogoutYes).setOnClickListener(onClickListener);
                break;
            case GOODS_IMAGE:
                setContentView(R.layout.dialog_goods_image);
                findViewById(R.id.iBtnDialogGoodsImgClose).setOnClickListener(onClickListener);
                DotsIndicator viewPagerIndicator = findViewById(R.id.indCtrGoodsCurrentImage);
                ViewPager2 goodsImgView = findViewById(R.id.vPgrDialogGoodsImages);
                goodsImgView.setAdapter(new ListAdptDlgScalableGoodsImg(bitmapList));
                goodsImgView.setOffscreenPageLimit(2);
                viewPagerIndicator.setViewPager2(goodsImgView);
                goodsImgView.setPageTransformer(new ZoomOutPageTransformer());
                break;
            case FORM_INVALID:
                setContentView(R.layout.dialog_form_invalid);
                findViewById(R.id.btnRegistryConfirm).setOnClickListener(onClickListener);
                break;
            case SELECT_INVALID:
                setContentView(R.layout.dialog_select_invalid);
                findViewById(R.id.btnSelectConfirm).setOnClickListener(onClickListener);
                break;
            case NO_CHILD_INFORM:
                setContentView(R.layout.dialog_no_child_inform);
                findViewById(R.id.btnNoChildConfirm).setOnClickListener(onClickListener);
                break;
            case EVALUATE_CONFIRM:
                setContentView(R.layout.dialog_evaluate_confirm);
                findViewById(R.id.btnDlgEvaluateNo).setOnClickListener(onClickListener);
                findViewById(R.id.btnDlgEvaluateYes).setOnClickListener(onClickListener);
                break;
            case EXIT:
                setContentView(R.layout.dialog_exit_confirm);
                findViewById(R.id.btnDlgExitNo).setOnClickListener(onClickListener);
                findViewById(R.id.btnDlgExitYes).setOnClickListener(onClickListener);
                break;
            default:
                break;
        }
        setLayoutParameter();
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btnDlgLoginNo:
            case R.id.btnDlgLogoutNo:
            case R.id.btnDlgExitNo:
            case R.id.btnDlgEvaluateNo:
                dialogResponseListener.getResponse(false, null);
                dismiss();
                break;
            case R.id.btnDlgLoginYes:
            case R.id.btnDlgLogoutYes:
            case R.id.btnDlgEvaluateYes:
            case R.id.btnNoChildConfirm:
            case R.id.iBtnDialogGoodsImgClose:
                dialogResponseListener.getResponse(true, null);
                dismiss();
                break;

            case R.id.btnRegistryConfirm:
            case R.id.btnSelectConfirm:
                dismiss();
                break;
            case R.id.btnDlgExitYes:
//                getOwnerActivity().moveTaskToBack(true);
//                getOwnerActivity().finishAndRemoveTask();
                System.exit(0);
                break;
            default:
                break;
        }
    };

    private void setLayoutParameter() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        layoutParams.dimAmount = 0.8f;
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        this.setCanceledOnTouchOutside(false);

        switch (dialog_category) {
            case LOGIN:
            case FORM_INVALID:
            case SELECT_INVALID:
            case NO_CHILD_INFORM:
            case EVALUATE_CONFIRM:
            case EXIT:
                layoutParams.height = displayMetrics.heightPixels;
                layoutParams.width = (int) (displayMetrics.widthPixels * 0.9);
                break;
            default:
                break;
        }

        getWindow().setAttributes(layoutParams);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
