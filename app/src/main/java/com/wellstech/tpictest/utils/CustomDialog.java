package com.wellstech.tpictest.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.wellstech.tpictest.R;
import com.wellstech.tpictest.list_code.ListAdptDlgPopupImage;
import com.wellstech.tpictest.list_code.ListAdptDlgScalableGoodsImg;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class CustomDialog extends Dialog {

    private Timer timer;

    public enum DIALOG_CATEGORY {
        LOGIN,
        LOGOUT,
        JOIN_AGREE_CONFIRM,
        EMAIL,
        PASSWORD,
        POPUP_IMAGE,
        GOODS_IMAGE,
        DELETE_REVIEW_CONFIRM,
        NONE_SELECT_CHILD,
        INSERT_CONFIRM,
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
            case EMAIL:
                setContentView(R.layout.dialog_not_found);
                ((TextView)findViewById(R.id.tVwLoginErrorMessage)).setText(R.string.txt_dlg_not_found_user);
                findViewById(R.id.btnLoginErrorConfirm).setOnClickListener(onClickListener);
                break;
            case PASSWORD:
                setContentView(R.layout.dialog_not_found);
                ((TextView)findViewById(R.id.tVwLoginErrorMessage)).setText(R.string.txt_dlg_not_found_pwd);
                findViewById(R.id.btnLoginErrorConfirm).setOnClickListener(onClickListener);
                break;
            case JOIN_AGREE_CONFIRM:
                setContentView(R.layout.dialog_join_agree_confirm);
                findViewById(R.id.btnDlgTermsAgreeConfirm).setOnClickListener(onClickListener);
                break;
            case POPUP_IMAGE:
                setContentView(R.layout.dialog_popup_image);
                findViewById(R.id.btnTodayClose).setOnClickListener(onClickListener);
                findViewById(R.id.btnPopupClose).setOnClickListener(onClickListener);
                DotsIndicator popupIndicator = findViewById(R.id.indPopupCurrentImage);
                ViewPager2 popupImgView = findViewById(R.id.vPgrDialogPopupImages);
                popupImgView.setAdapter(new ListAdptDlgPopupImage(bitmapList));
                popupIndicator.setViewPager2(popupImgView);
                popupImgView.setPageTransformer(new ZoomOutPageTransformer());
                setPopupSlider(popupImgView);
                break;
            case GOODS_IMAGE:
                setContentView(R.layout.dialog_goods_image);
                findViewById(R.id.iBtnDialogGoodsImgClose).setOnClickListener(onClickListener);
                DotsIndicator viewPagerIndicator = findViewById(R.id.indCtrGoodsCurrentImage);
                ViewPager2 goodsImgView = findViewById(R.id.vPgrDialogGoodsImages);
                goodsImgView.setAdapter(new ListAdptDlgScalableGoodsImg(bitmapList));
//                goodsImgView.setOffscreenPageLimit(2);
                viewPagerIndicator.setViewPager2(goodsImgView);
                goodsImgView.setPageTransformer(new ZoomOutPageTransformer());
                break;
            case DELETE_REVIEW_CONFIRM:
                setContentView(R.layout.dialog_review_delete_confirm);
                findViewById(R.id.btnDlgReviewDeleteNo).setOnClickListener(onClickListener);
                findViewById(R.id.btnDlgReviewDeleteYes).setOnClickListener(onClickListener);
                break;
            case NONE_SELECT_CHILD:
                setContentView(R.layout.dialog_no_child_select);
                findViewById(R.id.btnNoChildSelectConfirm).setOnClickListener(onClickListener);
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
            case INSERT_CONFIRM:
                setContentView(R.layout.dialog_insert_confirm);
                findViewById(R.id.btnInsertComfirm).setOnClickListener(onClickListener);
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

    int currentPage = 0;
    public void setPopupSlider(ViewPager2 viewPager2) {
        long DELAY_MS = 500;
        long PERIOD_MS = 3000;

        Handler handler = new Handler();
        Runnable update = () -> {
            if (currentPage == Objects.requireNonNull(viewPager2.getAdapter()).getItemCount()) {
                currentPage = 0;
            }
            viewPager2.setCurrentItem(currentPage);
            currentPage++;
        };
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btnDlgLoginNo:
            case R.id.btnDlgLogoutNo:
            case R.id.btnDlgExitNo:
            case R.id.btnDlgEvaluateNo:
            case R.id.btnDlgReviewDeleteNo:
                dialogResponseListener.getResponse(false, null);
                dismiss();
                break;
            case R.id.btnDlgLoginYes:
            case R.id.btnDlgLogoutYes:
            case R.id.btnLoginErrorConfirm:
            case R.id.btnDlgTermsAgreeConfirm:
            case R.id.btnDlgEvaluateYes:
            case R.id.btnNoChildConfirm:
            case R.id.iBtnDialogGoodsImgClose:
            case R.id.btnNoChildSelectConfirm:
            case R.id.btnInsertComfirm:
            case R.id.btnDlgReviewDeleteYes:
                dialogResponseListener.getResponse(true, null);
                dismiss();
                break;
            case R.id.btnTodayClose:
                timer.cancel();
                dialogResponseListener.getResponse(true, "today");
                dismiss();
                break;
            case R.id.btnPopupClose:
                timer.cancel();
                dialogResponseListener.getResponse(true, "close");
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
            case POPUP_IMAGE:
            case LOGIN:
            case NONE_SELECT_CHILD:
            case FORM_INVALID:
            case SELECT_INVALID:
            case NO_CHILD_INFORM:
            case EVALUATE_CONFIRM:
            case JOIN_AGREE_CONFIRM:
            case EMAIL:
            case PASSWORD:
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
