package com.next.easynavigitiondemo.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.next.easynavigition.constant.Anim;
import com.next.easynavigition.utils.SmallUtil;
import com.next.easynavigition.view.EasyNavigitionBar;
import com.next.easynavigition.view.NavigitionBuilder;
import com.next.easynavigitiondemo.R;
import com.next.easynavigitiondemo.view.KickBackAnimator;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private EasyNavigitionBar navigitionBar;

    private String[] tabText = {"首页", "发现", "消息", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};

    private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();


    //仿微博图片和文字集合
    private int[] menuIconItems = {R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4};
    private String[] menuTextItems = {"文字", "拍摄", "相册", "直播"};

    private LinearLayout menuLayout;
    private View cancelImageView;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        navigitionBar = findViewById(R.id.navigitionBar);

        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());

        navigitionBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .onItemListener(new EasyNavigitionBar.OnItemClickListener() {
                    @Override
                    public boolean onItemClickEvent(View view, int position) {
                        if (position == 3) {
                            Toast.makeText(AddActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                        // Toast.makeText(AddActivity.this, "您点击了第" + (position + 1) + "个Tab", Toast.LENGTH_SHORT).show();
                    }
                })
                .isAdd(true)
                .Anim(Anim.ZoomIn)
                .addIcon(R.drawable.add_image)
                .onAddClickListener(new EasyNavigitionBar.OnAddClickListener() {
                    @Override
                    public void OnAddClickEvent(View view) {
                        //跳转页面（全民K歌）   或者   弹出菜单（微博）
                        showMunu();
                    }
                }).build();

        navigitionBar.setAddViewLayout(createWeiboView());

        navigitionBar.getmViewPager().setCanScroll(true);

    }

    //仿微博弹出菜单
    private View createWeiboView() {
        ViewGroup view = (ViewGroup) View.inflate(AddActivity.this, R.layout.layout_add_view, null);
        menuLayout = view.findViewById(R.id.icon_group);
        cancelImageView = view.findViewById(R.id.cancel_iv);
        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAnimation();
            }
        });
        for (int i = 0; i < 4; i++) {
            View itemView = View.inflate(AddActivity.this, R.layout.item_icon, null);
            ImageView menuImage = itemView.findViewById(R.id.menu_icon_iv);
            TextView menuText = itemView.findViewById(R.id.menu_text_tv);

            menuImage.setImageResource(menuIconItems[i]);
            menuText.setText(menuTextItems[i]);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            itemView.setLayoutParams(params);
            itemView.setVisibility(View.GONE);
            menuLayout.addView(itemView);
        }
        return view;
    }

    //
    private void showMunu() {
        startAnimation();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //＋ 旋转动画
                cancelImageView.animate().rotation(90).setDuration(400);
            }
        });
        //菜单项弹出动画
        for (int i = 0; i < menuLayout.getChildCount(); i++) {
            final View child = menuLayout.getChildAt(i);
            child.setVisibility(View.INVISIBLE);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                    fadeAnim.setDuration(500);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(500);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                }
            }, i * 50 + 100);
        }
    }


    private void startAnimation() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //圆形扩展的动画
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        int x = SmallUtil.getScreenWidth(AddActivity.this) / 2;
                        int y = (int) (SmallUtil.getScreenHeith(AddActivity.this) - SmallUtil.dip2px(AddActivity.this, 25));
                        Animator animator = ViewAnimationUtils.createCircularReveal(navigitionBar.getAddViewLayout(), x,
                                y, 0, navigitionBar.getAddViewLayout().getHeight());
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                navigitionBar.getAddViewLayout().setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                //							layout.setVisibility(View.VISIBLE);
                            }
                        });
                        animator.setDuration(300);
                        animator.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 关闭window动画
     */
    private void closeAnimation() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                cancelImageView.animate().rotation(-90).setDuration(400);
            }
        });

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                int x = SmallUtil.getScreenWidth(this) / 2;
                int y = (SmallUtil.getScreenHeith(this) - SmallUtil.dip2px(this, 25));
                Animator animator = ViewAnimationUtils.createCircularReveal(navigitionBar.getAddViewLayout(), x,
                        y, navigitionBar.getAddViewLayout().getHeight(), 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        //							layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        navigitionBar.getAddViewLayout().setVisibility(View.GONE);
                        //dismiss();
                    }
                });
                animator.setDuration(300);
                animator.start();
            }
        } catch (Exception e) {
        }
    }

    public EasyNavigitionBar getNavigitionBar() {
        return navigitionBar;
    }

}
