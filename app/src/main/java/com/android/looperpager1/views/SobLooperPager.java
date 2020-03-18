package com.android.looperpager1.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.looperpager1.R;

public class SobLooperPager extends LinearLayout {

    private ViewPager mViewPager;
    private TextView mLooper_title;
    private BinderTitleListener binderTitleListener = null;
    private InnerAdapter innerAdapter = null;
    private LinearLayout mPointContainer;
    private int mRealPosition;


    public SobLooperPager(Context context) {
        this(context,null);
    }

    public SobLooperPager(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SobLooperPager(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.looper_pager_layout,this,true);
        //等价于以下两行代码
        /*View item = LayoutInflater.from(context).inflate(R.layout.looper_pager_layout,this,false);
        addView(item);*/
        init();
    }

    private void init() {
        initView();
        initEvent();

    }

    private void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //切换停下来的一个回调
                //停下来以后设置标题
                int realPosition = position%innerAdapter.getDataSize();
                mLooper_title.setText(binderTitleListener.getTitle(realPosition));
                updateIndicatorPointer();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void setData(InnerAdapter innerAdapter, BinderTitleListener binderTitleListener){
        this.binderTitleListener = binderTitleListener;
        this.innerAdapter = innerAdapter;
        updateIndicatorPointer();
        mViewPager.setAdapter(innerAdapter);
        mViewPager.setCurrentItem(Integer.MAX_VALUE/2+1);
    }

    public static abstract class InnerAdapter extends PagerAdapter{
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
                 int realPosition = position%getDataSize();
                 View itemView = getSubView(container,realPosition);
                 container.addView(itemView);
                 return itemView;
        }



        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        protected abstract View getSubView(ViewGroup container, int position);
        protected abstract int getDataSize();
    }



    private void updateIndicatorPointer() {
        if (innerAdapter!=null&&binderTitleListener!=null) {
            int count =innerAdapter.getDataSize();
            mPointContainer.removeAllViews();
            for (int i = 0; i < count; i++) {
                View point = new View(getContext());
                if (mViewPager.getCurrentItem()%count==i){
                    point.setBackgroundColor(Color.parseColor("#ff0000"));
                }else{
                    point.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(SizeUtils.dip2px(getContext(), 5),
                        SizeUtils.dip2px(getContext(), 5));
                layoutParams.setMargins(SizeUtils.dip2px(getContext(), 5), 0, SizeUtils.dip2px(getContext(), 5), 0);
                point.setLayoutParams(layoutParams);
                mPointContainer.addView(point);
            }
        }
    }




    public interface BinderTitleListener {
        //让外部实现这个接口的时候必须要实现这个方法
        String getTitle(int position);

    }


    private void initView() {
        mViewPager = findViewById(R.id.looper_view_pager);//需要外面setAdapter
        mLooper_title = findViewById(R.id.looper_title);//title的数据需要别人给
        mPointContainer = findViewById(R.id.pointer_container);
    }
}
