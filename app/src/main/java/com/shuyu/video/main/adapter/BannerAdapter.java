package com.shuyu.video.main.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shuyu.video.model.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by zhangleilei on 8/31/16.
 */

public class BannerAdapter extends PagerAdapter {


    private List<Banner> mBanners;
    private List<SimpleDraweeView> mImageViews = new ArrayList<>();

    public BannerAdapter(Context context, List<Banner> banners) {
        mBanners = banners;

        for (int i = 0; i < mBanners.size(); i++) {
            SimpleDraweeView imageView = new SimpleDraweeView(context);
            imageView.setImageURI(banners.get(i).getImgUrl());
            mImageViews.add(imageView);
        }
    }

    @Override
    public int getCount() {
        return mBanners.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mImageViews.get(position));
        return mImageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageViews.get(position));
    }

}
