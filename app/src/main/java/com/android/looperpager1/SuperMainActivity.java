package com.android.looperpager1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.looperpager1.views.PagerItem;
import com.android.looperpager1.views.SobLooperPager;

import java.util.ArrayList;
import java.util.List;

public class SuperMainActivity extends AppCompatActivity {

    private List<PagerItem> mData = new ArrayList<>();
    private SobLooperPager mSobLooperPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_main);
        initData();
        initView();
    }

    private void initData() {
        //因为要加载图片和title  所以可以定义一个bean
        mData.add(new PagerItem("第一张图片",R.mipmap.pic0));
        mData.add(new PagerItem("第2张图片",R.mipmap.pic1));
        mData.add(new PagerItem("第3张图片",R.mipmap.pic2));
        mData.add(new PagerItem("第4张图片",R.mipmap.pic3));

    }

    private void initView() {
        mSobLooperPager = findViewById(R.id.looper_pager);
        mSobLooperPager.setData(
                new SobLooperPager.InnerAdapter() {
                    @Override
                    protected View getSubView(ViewGroup container, int position) {
                       ImageView iv = new ImageView(container.getContext());
                       iv.setImageResource(mData.get(position).getResourceId());
                       iv.setScaleType(ImageView.ScaleType.FIT_XY);
                       return iv;
                    }

                    @Override
                    protected int getDataSize() {
                        return mData.size();
                    }
                }, new SobLooperPager.BinderTitleListener() {
                    @Override
                    public String getTitle(int position) {
                        return mData.get(position).getTitle();
                    }

                });
    }


}
