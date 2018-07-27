package com.hly.popwindow;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

/**
 * ~~~~~~文件描述:~~~~~~
 * ~~~~~~作者:huleiyang~~~~~~
 * ~~~~~~创建时间:2018/5/22~~~~~~
 * ~~~~~~更改时间:2018/5/22~~~~~~
 * ~~~~~~版本号:1~~~~~~
 */
public class BottomPopWindow {
    static BottomSheetDialog bottomSheetDialog;
    public void dismiss() {
        bottomSheetDialog.dismiss();
    }
    public BottomPopWindow pop(Context context, View v) {
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(v);
        return this;
    }
    public void show() {
        bottomSheetDialog.show();
    }
    private BottomPopWindow() {}

    public static BottomPopWindow getInstance() {
        return InnerPop.bottomPopWindow;
    }
    private static class InnerPop {
        private static BottomPopWindow bottomPopWindow = new BottomPopWindow();
    }
    OnCallBack onBack;
    public void setOnBack(OnCallBack onBack) {
        this.onBack = onBack;
    }
    public interface OnCallBack {
        void back(String Content);
    }
    class Choose {
        private Boolean choose;
        public Boolean getChoose() {
            return choose;
        }
        public void setChoose(Boolean choose) {
            this.choose = choose;
        }
    }
}
