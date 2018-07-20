package com.next.easynavigitiondemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.next.easynavigition.constant.Anim;
import com.next.easynavigition.view.EasyNavigitionBar;
import com.next.easynavigition.view.NavigitionBuilder;
import com.next.easynavigitiondemo.R;

import java.util.ArrayList;
import java.util.List;

public class NoAddActivity extends AppCompatActivity {

    private EasyNavigitionBar navigitionBar;

    private String[] tabText = {"首页", "发现", "消息", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};

    private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noadd);

        navigitionBar = findViewById(R.id.navigitionBar);

        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());

        navigitionBar
                .titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .onItemListener(new EasyNavigitionBar.OnItemClickListener() {
                    @Override
                    public void onItemClickEvent(View view, int position) {

                    }
                })
                .Anim(Anim.ZoomIn)
                .build();

        // navigitionBar.setBuilder(builder);


    }

    public EasyNavigitionBar getNavigitionBar() {
        return navigitionBar;
    }

}
