package com.shuyu.video.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.video.R;
import com.shuyu.video.model.ChannelBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangleilei on 8/31/16.
 */
public class ChannelBannerAdapter extends PagerAdapter {


    private Context mContext;
    private List<ChannelBanner> mBanners = new ArrayList<>();
    private List<ImageView> mImageViews = new ArrayList<>();

    public ChannelBannerAdapter(Context context) {
        mContext = context;
    }

    public void setBanners(List<ChannelBanner> banners) {
        mBanners = banners;
        for (int i = 0; i < mBanners.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(mContext)
                    .load(banners.get(i).getImgUrl())
                    .error(R.mipmap.ic_default_image)
                    .into(imageView);
            mImageViews.add(imageView);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mBanners == null ? 0 : mBanners.size();
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
