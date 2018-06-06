package com.next.easynavigitiondemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.next.easynavigition.constant.Anim;
import com.next.easynavigition.view.NavigitionBar;
import com.next.easynavigitiondemo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NavigitionBar navigitionBar;

    private String[] tabText = {"首页", "发现", "消息", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};

    private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigitionBar = findViewById(R.id.navigitionBar);

        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());

        navigitionBar.setData(tabText, normalIcon, selectIcon, fragments, getSupportFragmentManager(), Anim.ZoomIn, new NavigitionBar.OnItemClickListener() {
            @Override
            public void onItemClickEvent(View view, int position) {
                Toast.makeText(MainActivity.this,"您点击了第"+(position+1)+"个Tab",Toast.LENGTH_SHORT).show();
            }
        });

        /*navigitionBar.setOnItemClickListener(new NavigitionBar.OnItemClickListener() {
            @Override
            public void onItemClickEvent(int position) {
                Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });*/

      /*  navigitionBar.setOnDoubleClickListener(new NavigitionBar.OnDoubleClickListener() {
            @Override
            public void onDoubleClickEvent(int position) {
                Toast.makeText(MainActivity.this,"双击"+position,Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    public void click01(View view) {
        navigitionBar.setMsgPointCount(2, 109);
        navigitionBar.setMsgPointCount(0, 5);
        navigitionBar.setHintPoint(3, true);
    }

    public void clear(View view) {
        navigitionBar.clearAllMsgPoint();
        navigitionBar.clearAllHintPoint();
    }
}
