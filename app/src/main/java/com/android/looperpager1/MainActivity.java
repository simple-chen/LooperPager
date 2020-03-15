package com.android.looperpager1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Integer> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        mData.add(R.mipmap.pic0);
        mData.add(R.mipmap.pic1);
        mData.add(R.mipmap.pic2);
        mData.add(R.mipmap.pic3);
        mPagerAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mPagerAdapter);
    }

    private PagerAdapter mPagerAdapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View item = LayoutInflater.from(MainActivity.this).inflate(R.layout.pager_item, container, false);
            ImageView image = item.findViewById(R.id.image);
            image.setImageResource(mData.get(position));
            if (image.getParent() instanceof ViewGroup) {
                ((ViewGroup) image.getParent()).removeView(image);
            }
            container.addView(image);
            return image;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

    };
}

