package com.next.easynavigitiondemo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.next.easynavigition.view.EasyNavigitionBar;
import com.next.easynavigitiondemo.R;
import com.next.easynavigitiondemo.ui.normal.AFragment;
import com.next.easynavigitiondemo.ui.normal.BFragment;
import com.next.easynavigitiondemo.ui.normal.CFragment;
import com.next.easynavigitiondemo.ui.normal.DFragment;
import com.next.easynavigitiondemo.ui.normal.EFragment;

import java.util.ArrayList;
import java.util.List;

public class AddViewActivity extends AppCompatActivity {

    private EasyNavigitionBar navigitionBar;

    private String[] tabText = {"首页", "发现", "消息", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message,R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};

    private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
    private Handler mHandler = new Handler();
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_view);

        navigitionBar = findViewById(R.id.navigitionBar);

        fragments.add(new AFragment());
        fragments.add(new BFragment());
        fragments.add(new CFragment());
        fragments.add(new DFragment());


        View view = LayoutInflater.from(this).inflate(R.layout.custom_add_view, null);

        navigitionBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .canScroll(true)
                .addAsFragment(false)
                .mode(EasyNavigitionBar.MODE_ADD_VIEW)
                .addCustomView(view)
                .fragmentManager(getSupportFragmentManager())
                .onTabClickListener(new EasyNavigitionBar.OnTabClickListener() {
                    @Override
                    public boolean onTabClickEvent(View view, int position) {
                        Log.e("Tap->Position", position + "");
                        if (position == 2) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //＋ 旋转动画
                                    if (flag) {
                                        navigitionBar.getCustomAddView().animate().rotation(180).setDuration(400);
                                    } else {
                                        navigitionBar.getCustomAddView().animate().rotation(0).setDuration(400);
                                    }
                                    flag = !flag;
                                }
                            });
                        }
                        return false;
                    }
                })
                .build();

    }

    public EasyNavigitionBar getNavigitionBar() {
        return navigitionBar;
    }

}
