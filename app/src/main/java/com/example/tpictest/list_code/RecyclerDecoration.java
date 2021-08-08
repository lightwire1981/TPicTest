package com.example.tpictest.list_code;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class RecyclerDecoration extends RecyclerView.ItemDecoration {
    private final int divWidth;
    private final int divHeight;

    public RecyclerDecoration(int divWidth, int divHeight) {
        this.divWidth = divWidth;
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(@NonNull @NotNull Rect outRect, @NonNull @NotNull View view, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = divWidth;
        outRect.right = divWidth;
        outRect.top = divHeight;
        outRect.bottom = divHeight;
    }
}
