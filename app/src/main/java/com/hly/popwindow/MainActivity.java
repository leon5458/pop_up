package com.hly.popwindow;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CommonPopupWindow commonPopupWindow;
    private CommonPopupWindow.LayoutGravity layoutGravity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commonPopupWindow = new CommonPopupWindow(this, R.layout.infro_activity_photo_item, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view = getContentView();
            }

            @Override
            protected void initEvent() {

            }
        };
//        layoutGravity=new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.ALIGN_LEFT| CommonPopupWindow.LayoutGravity.TO_BOTTOM);
//        layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.TO_BOTTOM);

        final BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
        /**
         * 3中不同的插入方法
         */
//      View v = getLayoutInflater().inflate(R.layout.infro_activity_photo_item, null);
//      View v = View.inflate(MainActivity.this,R.layout.infro_activity_photo_item,null);
        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.infro_activity_photo_item, null, false);
        dialog.setContentView(v);
        /**
         * 监听事件
         */
        TextView camera_txt = v.findViewById(R.id.camera);

        findViewById(R.id.bottom_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        camera_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });


        findViewById(R.id.bottomdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View phView = LayoutInflater.from(MainActivity.this).inflate(R.layout.infro_activity_photo_item, null, false);
//                phView.getBackground().setAlpha(0);
                initPhoto(phView);
                BottomPopWindow.getInstance().pop(MainActivity.this, phView).show();
            }
        });

        //底部
        findViewById(R.id.popwindow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopwindow();
            }
        });

//        //相对某个位置的某一个底部
//        findViewById(R.id.pop_bottom).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showBottomPop();
//            }
//        });

        //封装的popwindow
        findViewById(R.id.pop_window).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                commonPopupWindow.showBashOfAnchor(view,layoutGravity,0,0);
//                commonPopupWindow.showAsDropDown(view,0,0);
                commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            }
        });

        /**
         * Dialog 类是对话框的基类，但应该避免直接实例化 Dialog，而应使用其子类
         */

        //dialog
        findViewById(R.id.dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                //2.填充布局
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View dialogView = inflater.inflate(R.layout.infro_activity_photo_item, null);
                dialog.setContentView(dialogView);
                //3.设置指定的宽高,如果不设置的话，弹出的对话框可能不会显示全整个布局，当然在布局中写死宽高也可以
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                lp.copyFrom(window.getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                //注意要在Dialog show之后，再将宽高属性设置进去，才有效果
                dialog.show();
                window.setAttributes(lp);
            }
        });

        findViewById(R.id.custom_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog BottomDialog = new Dialog(MainActivity.this);
                View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.infro_activity_photo_item, null);
                BottomDialog.setContentView(contentView);
                BottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                BottomDialog.show();
            }
        });

        //原生的alertdialog
        findViewById(R.id.alertdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                dialog.setTitle("温馨提示");
                dialog.setMessage("信息已修改是否保存");

                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.show();
            }
        });

        findViewById(R.id.pop_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.nav_add_item_layout, null);
                PopupWindow popupWindow = new PopupWindow(contentView);
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setAnimationStyle(R.style.pop_center);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(contentView,Gravity.CENTER,0,0);
                contentView.findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
//    private void showBottomPop() {
//        // 利用layoutInflater获得View
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.infor_item, null);
//        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setFocusable(true);// 设置popWindow弹出窗体可点击
//        // 实例化一个ColorDrawable颜色为半透明
////        ColorDrawable dw = new ColorDrawable(0xb0000000);
////        window.setBackgroundDrawable(dw);
//        window.setOutsideTouchable(true);
//
////        window.showAtLocation(findViewById(R.id.popwindow), Gravity.NO_GRAVITY, 0, 0);
//
//
////        window.showAtLocation(MainActivity.this.findViewById(R.id.popwindow), Gravity.NO_GRAVITY, 0, 0);
////        window.showAsDropDown(findViewById(R.id.popwindow));
//
//        int[] location = new int[2];
//        Log.d("Taonce", "location:" + location.toString());
//        view.getLocationOnScreen(location);
//
//    }

    private void initPhoto(View phView) {
        TextView camera_txt = phView.findViewById(R.id.camera);
        camera_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "点击了", Toast.LENGTH_LONG).show();
                BottomPopWindow.getInstance().dismiss();
            }
        });
    }

    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.infro_activity_photo_item, null);
        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);// 设置popWindow弹出窗体可点击
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.pop_anim_style);
        // 在底部显示
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        TextView txt = view.findViewById(R.id.camera);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_LONG).show();
                window.dismiss();
            }
        });
    }

}
