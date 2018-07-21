package com.next.easynavigition.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.next.easynavigition.R;
import com.next.easynavigition.adapter.ViewPagerAdapter;
import com.next.easynavigition.constant.Anim;
import com.next.easynavigition.utils.SmallUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jue on 2018/6/1.
 */

public class EasyNavigitionBar extends LinearLayout {


    //private OnDoubleClickListener mOnDoubleClickListener;
    private RelativeLayout containerLayout;

    //Tab数量
    private int tabCount = 0;

    private LinearLayout navigitionLayout;
    private RelativeLayout mLinearLayout;
    //分割线
    private View common_horizontal_line;

    private int doubleClickPositon = -1;

    //红点集合
    private List<View> hintPointList = new ArrayList<>();

    //消息数量集合
    private List<TextView> msgPointList = new ArrayList<>();

    //底部Image集合
    private List<ImageView> imageViewList = new ArrayList<>();

    //底部Text集合
    private List<TextView> textViewList = new ArrayList<>();

    //底部TabLayout（除中间加号）
    private List<View> tabList = new ArrayList<>();

    private CustomViewPager mViewPager;
    //private GestureDetector detector;

    private ViewGroup addViewLayout;


    //文字集合
    private String[] titleItems;
    //未选择 图片集合
    private int[] normalIconItems;
    //已选择 图片集合
    private int[] selectIconItems;
    //fragment集合
    private List<Fragment> fragmentList = new ArrayList<>();

    private FragmentManager fragmentManager;

    //Tab点击动画效果
    private Techniques anim = null;
    //ViewPager切换动画
    private boolean smoothScroll = false;
    //是否添加中间的按钮
    private boolean isAdd;
    //图标大小
    private int iconSize = 20;

    //提示红点大小
    private float hintPointSize = 6;
    //提示红点距Tab图标的距离
    private float hintPointLeft = 0;
    //提示红点距顶部的距离
    private float hintPointTop = 5;

    private EasyNavigitionBar.OnItemClickListener onItemClickListener;
    //消息红点字体大小
    private float msgPointTextSize = 9;
    //消息红点大小
    private float msgPointSize = 18;
    //消息红点距Tab图标的距离
    private float msgPointLeft = -5;
    //消息红点距顶部的距离
    private float msgPointTop = 3;
    //Tab文字距Tab图标的距离
    private float tabTextTop = 2;
    //Tab文字大小
    private float tabTextSize = 10;
    //未选中Tab字体颜色
    private int normalTextColor = Color.parseColor("#666666");
    //选中字体颜色
    private int selectTextColor = Color.parseColor("#333333");
    //分割线高度
    private float lineHeight = 1;
    //分割线颜色
    private int lineColor = Color.parseColor("#f7f7f7");

    private int navigitionBackground = Color.parseColor("#ffffff");
    private float navigitionHeight = 52;


    //Add
    private EasyNavigitionBar.OnAddClickListener onAddClickListener;
    private float addIconSize = 36;
    private int addIcon;
    private float addLayoutHeight = navigitionHeight;


    public EasyNavigitionBar(Context context) {
        super(context);

        initViews(context, null);
    }

    public EasyNavigitionBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();

        initViews(context, attrs);
    }

    private void init() {

       /* detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (mOnDoubleClickListener != null)
                    mOnDoubleClickListener.onDoubleClickEvent(doubleClickPositon);
                return super.onDoubleTap(e);
            }
        });*/
    }

    private void initViews(Context context, AttributeSet attrs) {

        mLinearLayout = (RelativeLayout) View.inflate(context, R.layout.container_layout, null);
        addViewLayout = mLinearLayout.findViewById(R.id.add_view_ll);
        containerLayout = mLinearLayout.findViewById(R.id.container_rl);
        navigitionLayout = mLinearLayout.findViewById(R.id.navigition_ll);
        mViewPager = mLinearLayout.findViewById(R.id.mViewPager);
        common_horizontal_line = mLinearLayout.findViewById(R.id.common_horizontal_line);


        toDp();


        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.EasyNavigitionBar);
        parseStyle(attributes);

        addView(mLinearLayout);
    }

    private void parseStyle(TypedArray attributes) {
        if (attributes != null) {
            navigitionHeight = attributes.getDimension(R.styleable.EasyNavigitionBar_navigitionHeight, navigitionHeight);
            navigitionBackground = attributes.getColor(R.styleable.EasyNavigitionBar_navigitionBackground, navigitionBackground);
            navigitionLayout.setBackgroundColor(navigitionBackground);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) navigitionLayout.getLayoutParams();
            params.height = (int) navigitionHeight;
            navigitionLayout.setLayoutParams(params);

            tabTextSize = attributes.getDimension(R.styleable.EasyNavigitionBar_tabTextSize, tabTextSize);
            tabTextTop = attributes.getDimension(R.styleable.EasyNavigitionBar_tabTextTop, tabTextTop);
            iconSize = (int) attributes.getDimension(R.styleable.EasyNavigitionBar_tabIconSize, iconSize);
            hintPointSize = attributes.getDimension(R.styleable.EasyNavigitionBar_hintPointSize, hintPointSize);
            msgPointSize = attributes.getDimension(R.styleable.EasyNavigitionBar_msgPointSize, msgPointSize);
            hintPointLeft = attributes.getDimension(R.styleable.EasyNavigitionBar_hintPointLeft, hintPointLeft);
            msgPointTop = attributes.getDimension(R.styleable.EasyNavigitionBar_msgPointTop, msgPointTop);
            hintPointTop = attributes.getDimension(R.styleable.EasyNavigitionBar_hintPointTop, hintPointTop);

            msgPointLeft = attributes.getDimension(R.styleable.EasyNavigitionBar_msgPointLeft, msgPointLeft);
            msgPointTextSize = attributes.getDimension(R.styleable.EasyNavigitionBar_msgPointTextSize, msgPointTextSize);
            addIconSize = attributes.getDimension(R.styleable.EasyNavigitionBar_addImageSize, addIconSize);
            addIcon = attributes.getInteger(R.styleable.EasyNavigitionBar_addIconRes, addIcon);


            lineHeight = attributes.getDimension(R.styleable.EasyNavigitionBar_lineHeight, lineHeight);
            lineColor = attributes.getColor(R.styleable.EasyNavigitionBar_lineColor, lineColor);
            common_horizontal_line.setBackgroundColor(lineColor);

            addLayoutHeight = attributes.getDimension(R.styleable.EasyNavigitionBar_addLayoutHeight, navigitionHeight + lineHeight);
            LayoutParams addLayoutParams = (LayoutParams) containerLayout.getLayoutParams();
            addLayoutParams.height = (int) addLayoutHeight;
            containerLayout.setLayoutParams(addLayoutParams);

            RelativeLayout.LayoutParams lineParams = (RelativeLayout.LayoutParams) common_horizontal_line.getLayoutParams();
            lineParams.height = (int) lineHeight;
            common_horizontal_line.setLayoutParams(lineParams);

            normalTextColor = attributes.getColor(R.styleable.EasyNavigitionBar_tabNormalColor, normalTextColor);
            selectTextColor = attributes.getColor(R.styleable.EasyNavigitionBar_tabSelectColor, selectTextColor);

            attributes.recycle();
        }
    }

    //将dp、sp转换成px
    private void toDp() {
        navigitionHeight = SmallUtil.dip2px(getContext(), navigitionHeight);
        iconSize = SmallUtil.dip2px(getContext(), iconSize);
        hintPointSize = SmallUtil.dip2px(getContext(), hintPointSize);
        hintPointTop = SmallUtil.dip2px(getContext(), hintPointTop);
        hintPointLeft = SmallUtil.dip2px(getContext(), hintPointLeft);

        msgPointLeft = SmallUtil.dip2px(getContext(), msgPointLeft);
        msgPointTop = SmallUtil.dip2px(getContext(), msgPointTop);
        msgPointSize = SmallUtil.dip2px(getContext(), msgPointSize);
        msgPointTextSize = SmallUtil.sp2px(getContext(), msgPointTextSize);

        tabTextTop = SmallUtil.dip2px(getContext(), tabTextTop);
        tabTextSize = SmallUtil.sp2px(getContext(), tabTextSize);


        //Add
        addIconSize = SmallUtil.dip2px(getContext(), addIconSize);
        addLayoutHeight = SmallUtil.dip2px(getContext(), addLayoutHeight);
    }


    public void build() {
        if (isAdd) {
            buildAddNavigition();
        } else {
            buildNavigition();
        }
    }

    public void buildNavigition() {
        if ((titleItems.length != normalIconItems.length) || (titleItems.length != selectIconItems.length) || (normalIconItems.length != selectIconItems.length))
            return;

        tabCount = titleItems.length;

        hintPointList.clear();
        imageViewList.clear();
        textViewList.clear();

        tabList.clear();

        navigitionLayout.removeAllViews();

        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager, fragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < tabCount; i++) {
            View itemView = View.inflate(getContext(), R.layout.navigition_tab_layout, null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.width = SmallUtil.getScreenWidth(getContext()) / tabCount;

            itemView.setLayoutParams(params);
            itemView.setId(i);

            TextView text = itemView.findViewById(R.id.tab_text_tv);
            ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
            LayoutParams iconParams = (LayoutParams) icon.getLayoutParams();
            iconParams.width = (int) iconSize;
            iconParams.height = (int) iconSize;
            icon.setLayoutParams(iconParams);

            View hintPoint = itemView.findViewById(R.id.red_point);

            //提示红点
            RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
            hintPointParams.topMargin = (int) hintPointTop;
            hintPointParams.width = (int) hintPointSize;
            hintPointParams.height = (int) hintPointSize;
            hintPointParams.leftMargin = (int) hintPointLeft;
            hintPoint.setLayoutParams(hintPointParams);

            //消息红点
            TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
            msgPoint.setTextSize(SmallUtil.px2sp(getContext(), msgPointTextSize));
            RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
            msgPointParams.topMargin = (int) msgPointTop;
            msgPointParams.width = (int) msgPointSize;
            msgPointParams.height = (int) msgPointSize;
            msgPointParams.leftMargin = (int) msgPointLeft;
            msgPoint.setLayoutParams(msgPointParams);


            hintPointList.add(hintPoint);
            msgPointList.add(msgPoint);

            imageViewList.add(icon);
            textViewList.add(text);

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        if (!onItemClickListener.onItemClickEvent(view, view.getId())) {
                            mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                    } else {
                        mViewPager.setCurrentItem(view.getId(), smoothScroll);
                    }
                }
            });

            LayoutParams textParams = (LayoutParams) text.getLayoutParams();
            textParams.topMargin = (int) tabTextTop;
            text.setLayoutParams(textParams);
            text.setText(titleItems[i]);
            text.setTextSize(SmallUtil.px2sp(getContext(), tabTextSize));


            tabList.add(itemView);
            navigitionLayout.addView(itemView);
        }

        selectPosition(0);
    }

    public void buildAddNavigition() {
        if ((titleItems.length != normalIconItems.length) || (titleItems.length != selectIconItems.length) || (normalIconItems.length != selectIconItems.length))
            return;
        tabCount = titleItems.length + 1;
        int index = 0;


        hintPointList.clear();
        hintPointList.clear();
        imageViewList.clear();
        textViewList.clear();
        tabList.clear();

        navigitionLayout.removeAllViews();

        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager, fragmentList);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < tabCount; i++) {

            if (i == tabCount / 2) {
                RelativeLayout addLayout = new RelativeLayout(getContext());
                RelativeLayout.LayoutParams addParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                addParams.width = SmallUtil.getScreenWidth(getContext()) / tabCount;
                //addParams.bottomMargin = SmallUtil.dip2px(getContext(),100);
                addLayout.setLayoutParams(addParams);
                navigitionLayout.addView(addLayout);
            } else {

                if (i > 1) {
                    index = i - 1;
                } else {
                    index = i;
                }

                View itemView = View.inflate(getContext(), R.layout.navigition_tab_layout, null);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.width = SmallUtil.getScreenWidth(getContext()) / tabCount;

                itemView.setLayoutParams(params);
                itemView.setId(index);

                TextView text = itemView.findViewById(R.id.tab_text_tv);
                ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
                LayoutParams iconParams = (LayoutParams) icon.getLayoutParams();
                iconParams.width = (int) iconSize;
                iconParams.height = (int) iconSize;
                icon.setLayoutParams(iconParams);

                imageViewList.add(icon);
                textViewList.add(text);

                itemView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (onItemClickListener != null) {
                            if (!onItemClickListener.onItemClickEvent(view, view.getId())) {
                                mViewPager.setCurrentItem(view.getId(), smoothScroll);
                            }
                        } else {
                            mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                    }
                });

                LayoutParams textParams = (LayoutParams) text.getLayoutParams();
                textParams.topMargin = (int) tabTextTop;
                text.setLayoutParams(textParams);
                text.setText(titleItems[index]);
                text.setTextSize(SmallUtil.px2sp(getContext(), tabTextSize));


                View hintPoint = itemView.findViewById(R.id.red_point);

                //提示红点
                RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
                hintPointParams.topMargin = (int) hintPointTop;
                hintPointParams.width = (int) hintPointSize;
                hintPointParams.height = (int) hintPointSize;
                hintPointParams.leftMargin = (int) hintPointLeft;
                hintPoint.setLayoutParams(hintPointParams);

                //消息红点
                TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
                msgPoint.setTextSize(SmallUtil.px2sp(getContext(), msgPointTextSize));
                RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
                msgPointParams.topMargin = (int) msgPointTop;
                msgPointParams.width = (int) msgPointSize;
                msgPointParams.height = (int) msgPointSize;
                msgPointParams.leftMargin = (int) msgPointLeft;
                msgPoint.setLayoutParams(msgPointParams);


                hintPointList.add(hintPoint);
                msgPointList.add(msgPoint);


                tabList.add(itemView);
                navigitionLayout.addView(itemView);
            }
        }


        RelativeLayout addLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams addParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        addParams.width = SmallUtil.getScreenWidth(getContext()) / tabCount;
        //addParams.height = (int) addContainerHeight;
        addParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        // addLayout.setBackgroundResource(R.drawable.oval_white);
        // addLayout.setPadding(SmallUtil.dip2px(getContext(),5),SmallUtil.dip2px(getContext(),5),SmallUtil.dip2px(getContext(),5),SmallUtil.dip2px(getContext(),5));
        addLayout.setLayoutParams(addParams);

        ImageView addImage = new ImageView(getContext());
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.width = (int) addIconSize;
        imageParams.height = (int) addIconSize;
        //imageParams.bottomMargin = SmallUtil.dip2px(getContext(),100);
        imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addImage.setLayoutParams(imageParams);

        addImage.setImageResource(addIcon);
        addImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddClickListener.OnAddClickEvent(view);
            }
        });

        addLayout.addView(addImage, imageParams);
        containerLayout.addView(addLayout, addParams);

        for (int i = 0; i < imageViewList.size(); i++) {
            if (i == 0) {
                imageViewList.get(i).setImageResource(selectIconItems[i]);
                textViewList.get(i).setTextColor(selectTextColor);
            } else {
                imageViewList.get(i).setImageResource(normalIconItems[i]);
                textViewList.get(i).setTextColor(normalTextColor);
            }
        }
    }


    public CustomViewPager getmViewPager() {
        return mViewPager;
    }


    public void setAddViewLayout(View addViewLayout) {
        FrameLayout.LayoutParams addParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addViewLayout.addView(addViewLayout, addParams);
    }

    public ViewGroup getAddViewLayout() {
        return addViewLayout;
    }
    /* public void setOnDoubleClickListener(OnDoubleClickListener mOnDoubleClickListener) {
         this.mOnDoubleClickListener = mOnDoubleClickListener;
         if (tabCount < 1) {
             return;
         }
         for (int i = 0; i < tabCount; i++) {
             final int finalI = i;
             navigitionLayout.getChildAt(i).setOnTouchListener(new OnTouchListener() {
                 @Override
                 public boolean onTouch(View v, MotionEvent event) {
                     doubleClickPositon = finalI;
                     return detector.onTouchEvent(event);
                 }
             });
         }
     }
 */

    /**
     * 选中第几项
     *
     * @param position
     */
    public void selectPosition(int position) {
        for (int i = 0; i < imageViewList.size(); i++) {
            if (i == position) {
                if (anim != null)
                    YoYo.with(anim).duration(300).playOn(tabList.get(i));
                imageViewList.get(i).setImageResource(selectIconItems[i]);
                textViewList.get(i).setTextColor(selectTextColor);
            } else {
                imageViewList.get(i).setImageResource(normalIconItems[i]);
                textViewList.get(i).setTextColor(normalTextColor);
            }
        }
    }


    /**
     * 设置是否显示小红点
     *
     * @param position 第几个tab
     * @param isShow   是否显示
     */
    public void setHintPoint(int position, boolean isShow) {
        if (hintPointList == null || hintPointList.size() < (position + 1))
            return;
        if (isShow) {
            hintPointList.get(position).setVisibility(VISIBLE);
        } else {
            hintPointList.get(position).setVisibility(GONE);
        }
    }


    /**
     * 设置消息数量
     *
     * @param position 第几个tab
     * @param count    显示的数量  99个以上显示99+  少于1则不显示
     */
    public void setMsgPointCount(int position, int count) {
        if (msgPointList == null || msgPointList.size() < (position + 1))
            return;
        if (count > 99) {
            msgPointList.get(position).setText("99+");
            msgPointList.get(position).setVisibility(VISIBLE);
        } else if (count < 1) {
            msgPointList.get(position).setVisibility(GONE);
        } else {
            msgPointList.get(position).setText(count + "");
            msgPointList.get(position).setVisibility(VISIBLE);
        }
    }

    /**
     * 清空所有提示红点
     */
    public void clearAllHintPoint() {
        for (int i = 0; i < hintPointList.size(); i++) {
            hintPointList.get(i).setVisibility(GONE);
        }
    }

    /**
     * 清空所有消息红点
     */
    public void clearAllMsgPoint() {
        for (int i = 0; i < msgPointList.size(); i++) {
            msgPointList.get(i).setVisibility(GONE);
        }
    }


    public interface OnItemClickListener {
        boolean onItemClickEvent(View view, int position);
    }


    public interface OnAddClickListener {
        boolean OnAddClickEvent(View view);
    }


    public EasyNavigitionBar addLayoutHeight(int addLayoutHeight) {
        this.addLayoutHeight = addLayoutHeight;
        return this;
    }

    public EasyNavigitionBar addIcon(int addIcon) {
        this.addIcon = addIcon;
        return this;
    }

    public EasyNavigitionBar addIconSize(int addIconSize) {
        this.addIconSize = addIconSize;
        return this;
    }

    public EasyNavigitionBar onAddClickListener(EasyNavigitionBar.OnAddClickListener onAddClickListener) {
        this.onAddClickListener = onAddClickListener;
        return this;
    }


    public EasyNavigitionBar navigitionBackground(int navigitionBackground) {
        this.navigitionBackground = navigitionBackground;
        return this;
    }

    public EasyNavigitionBar navigitionHeight(int navigitionHeight) {
        this.navigitionHeight = navigitionHeight;
        return this;
    }

    public EasyNavigitionBar normalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
        return this;
    }

    public EasyNavigitionBar selectTextColor(int selectTextColor) {
        this.selectTextColor = selectTextColor;
        return this;
    }

    public EasyNavigitionBar lineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public EasyNavigitionBar lineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public EasyNavigitionBar tabTextSize(int tabTextSize) {
        this.tabTextSize = tabTextSize;
        return this;
    }

    public EasyNavigitionBar tabTextTop(int tabTextTop) {
        this.tabTextTop = tabTextTop;
        return this;
    }

    public EasyNavigitionBar msgPointTextSize(int msgPointTextSize) {
        this.msgPointTextSize = msgPointTextSize;
        return this;
    }

    public EasyNavigitionBar msgPointSize(int msgPointSize) {
        this.msgPointSize = msgPointSize;
        return this;
    }

    public EasyNavigitionBar msgPointLeft(int msgPointLeft) {
        this.msgPointLeft = msgPointLeft;
        return this;
    }

    public EasyNavigitionBar msgPointTop(int msgPointTop) {
        this.msgPointTop = msgPointTop;
        return this;
    }


    public EasyNavigitionBar hintPointSize(int hintPointSize) {
        this.hintPointSize = hintPointSize;
        return this;
    }

    public EasyNavigitionBar hintPointLeft(int hintPointLeft) {
        this.hintPointLeft = hintPointLeft;
        return this;
    }

    public EasyNavigitionBar hintPointTop(int hintPointTop) {
        this.hintPointTop = hintPointTop;
        return this;
    }


    public EasyNavigitionBar titleItems(String[] titleItems) {
        this.titleItems = titleItems;
        return this;
    }

    public EasyNavigitionBar normalIconItems(int[] normalIconItems) {
        this.normalIconItems = normalIconItems;
        return this;
    }

    public EasyNavigitionBar selectIconItems(int[] selectIconItems) {
        this.selectIconItems = selectIconItems;
        return this;
    }

    public EasyNavigitionBar fragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
        return this;
    }

    public EasyNavigitionBar fragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    public EasyNavigitionBar Anim(Anim anim) {
        this.anim = anim.getYoyo();
        return this;
    }

    public EasyNavigitionBar smoothScroll(boolean smoothScroll) {
        this.smoothScroll = smoothScroll;
        return this;
    }

    public EasyNavigitionBar isAdd(boolean isAdd) {
        this.isAdd = isAdd;
        return this;
    }

    public EasyNavigitionBar onItemListener(EasyNavigitionBar.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }


    public EasyNavigitionBar iconSize(int iconSize) {
        this.iconSize = iconSize;
        return this;
    }


    public String[] getTitleItems() {
        return titleItems;
    }

    public int[] getNormalIconItems() {
        return normalIconItems;
    }

    public int[] getSelectIconItems() {
        return selectIconItems;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public Techniques getAnim() {
        return anim;
    }

    public boolean isSmoothScroll() {
        return smoothScroll;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public EasyNavigitionBar.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public int getIconSize() {
        return iconSize;
    }


    public float getHintPointSize() {
        return hintPointSize;
    }

    public float getHintPointLeft() {
        return hintPointLeft;
    }

    public float getHintPointTop() {
        return hintPointTop;
    }


    public float getMsgPointTextSize() {
        return msgPointTextSize;
    }

    public float getMsgPointSize() {
        return msgPointSize;
    }

    public float getMsgPointLeft() {
        return msgPointLeft;
    }

    public float getMsgPointTop() {
        return msgPointTop;
    }

    public float getTabTextTop() {
        return tabTextTop;
    }

    public float getTabTextSize() {
        return tabTextSize;
    }

    public int getNormalTextColor() {
        return normalTextColor;
    }

    public int getSelectTextColor() {
        return selectTextColor;
    }

    public float getLineHeight() {
        return lineHeight;
    }

    public int getLineColor() {
        return lineColor;
    }

    public EasyNavigitionBar.OnAddClickListener getOnAddClickListener() {
        return onAddClickListener;
    }

    public float getAddIconSize() {
        return addIconSize;
    }

    public int getAddIcon() {
        return addIcon;
    }

    public float getAddLayoutHeight() {
        return addLayoutHeight;
    }

    public int getNavigitionBackground() {
        return navigitionBackground;
    }

    public float getNavigitionHeight() {
        return navigitionHeight;
    }

}
