package com.next.easynavigitiondemo.ui;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.next.easynavigition.constant.Anim;
import com.next.easynavigition.view.NavigitionAddBar;
import com.next.easynavigition.view.NavigitionBar;
import com.next.easynavigitiondemo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NavigitionAddBar navigitionBar;

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

      /*  navigitionBar.setData(tabText, normalIcon, selectIcon, fragments, getSupportFragmentManager(), Anim.ZoomIn, new NavigitionBar.OnItemClickListener() {
            @Override
            public void onItemClickEvent(View view, int position) {
                Toast.makeText(MainActivity.this,"您点击了第"+(position+1)+"个Tab",Toast.LENGTH_SHORT).show();
            }
        });*/

        ViewGroup view = (ViewGroup) View.inflate(MainActivity.this,R.layout.layout_add_view,null);
        final ViewGroup iconView = view.findViewById(R.id.icon_group);
        final ImageView cancelLayout = view.findViewById(R.id.cancel_iv);
        for(int i=0;i<4;i++){
            View itemView = View.inflate(MainActivity.this,R.layout.item_icon,null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            itemView.setLayoutParams(params);
            itemView.setVisibility(View.GONE);
             iconView.addView(itemView);
        }

      navigitionBar.setData(tabText,normalIcon,selectIcon,fragments,R.drawable.add_image,getSupportFragmentManager(),Anim.ZoomIn, false,new NavigitionAddBar.OnItemClickListener() {
          @Override
          public void onItemClickEvent(View view, int position) {
              Toast.makeText(MainActivity.this,"您点击了第"+(position+1)+"个Tab",Toast.LENGTH_SHORT).show();
          }
      }, new NavigitionAddBar.OnAddClickListener() {
          @Override
          public void OnAddClickEvent(View view) {
              Toast.makeText(MainActivity.this,"您点击了加号》》》》》》",Toast.LENGTH_SHORT).show();
              final TranslateAnimation animation = new TranslateAnimation(0, 0, -2000,
                      0);
              animation.setDuration(500);// 设置动画持续时间
             // animation.setRepeatMode(Animation.REVERSE);// 设置反方向执行

              //navigitionBar.getAddViewLayout().startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in));
              cancelLayout.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_rotate_right));
              navigitionBar.getAddViewLayout().setVisibility(View.VISIBLE);
              for(int i = 0;i<iconView.getChildCount();i++){
                  final int finalI = i;
                 /* YoYo.with(Techniques.SlideInUp).delay(100*i).duration(400).withListener(new Animator.AnimatorListener() {
                      @Override
                      public void onAnimationStart(Animator animator) {
                          iconView.getChildAt(finalI).setVisibility(View.VISIBLE);
                      }

                      @Override
                      public void onAnimationEnd(Animator animator) {

                      }

                      @Override
                      public void onAnimationCancel(Animator animator) {

                      }

                      @Override
                      public void onAnimationRepeat(Animator animator) {

                      }
                  }).playOn(iconView.getChildAt(i));*/

                 // iconView.getChildAt(i).startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_in));
                  iconView.getChildAt(finalI).setVisibility(View.VISIBLE);
                   iconView.getChildAt(finalI).startAnimation(animation);

              }
              YoYo.with(Techniques.FadeIn).duration(400)
                      .interpolate(new AccelerateDecelerateInterpolator())
                      .withListener(new Animator.AnimatorListener() {
                          @Override
                          public void onAnimationStart(Animator animation) {

                          }

                          @Override
                          public void onAnimationEnd(Animator animation) {
                          }

                          @Override
                          public void onAnimationCancel(Animator animation) {
                          }

                          @Override
                          public void onAnimationRepeat(Animator animation) {

                          }
                      }).playOn(navigitionBar.getAddViewLayout());
          }
      });


        navigitionBar.setAddViewLayout(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = iconView.getChildCount()-1;i>0;i--){
                   // iconView.getChildAt(i).startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_out));
                    final int finalI = i;
                    YoYo.with(Techniques.SlideOutDown).delay(100*i).duration(400).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            iconView.getChildAt(finalI).setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    }).playOn( iconView.getChildAt(i));
                    //iconView.getChildAt(i).setVisibility(View.GONE);
                }
              //  navigitionBar.getAddViewLayout().startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out));
                cancelLayout.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_rotate_left));
                YoYo.with(Techniques.FadeOut).duration(400)
                        .interpolate(new AccelerateDecelerateInterpolator())
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                navigitionBar.getAddViewLayout().setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).playOn(navigitionBar.getAddViewLayout());
                //navigitionBar.getAddViewLayout().setVisibility(View.GONE);
               // navigitionBar.getAddViewLayout().clearAnimation();
            }
        });

        navigitionBar.getmViewPager().setCanScroll(true);

    }

    public NavigitionAddBar getNavigitionBar() {
        return navigitionBar;
    }

}
