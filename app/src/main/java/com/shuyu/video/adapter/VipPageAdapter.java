package com.shuyu.video.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.video.R;
import com.shuyu.video.model.LiveVideoData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangleilei on 9/10/16.
 */

public class VipPageAdapter extends PagerAdapter {

    private Context mContext;
    private List<LiveVideoData.NightVideoDetail> mLiveVideoDataList = new ArrayList<>();
    private LinkedList<View> mConvertViews = new LinkedList<>();
    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setLiveVideoDataList(List<LiveVideoData.NightVideoDetail> liveVideoDataList) {
        mLiveVideoDataList.clear();
        mLiveVideoDataList.addAll(liveVideoDataList);
        notifyDataSetChanged();
    }

    public VipPageAdapter(Context context) {
        mContext = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mLiveVideoDataList.get(position).getDescription();
    }

    @Override
    public int getCount() {
        return mLiveVideoDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ViewHolder holder;
        View convertView;
        if (mConvertViews.size() == 0) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_vip_page, null);
            holder.ivUrl = (ImageView) convertView.findViewById(R.id.iv_url);
            holder.btnVideo = (Button) convertView.findViewById(R.id.btn_vip_video);
            convertView.setTag(holder);
        } else {
            convertView = mConvertViews.removeFirst();
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mLiveVideoDataList.get(position).getImgUrl()).into(holder.ivUrl);
        holder.btnVideo.setTag(mLiveVideoDataList.get(position).getId());
        holder.btnVideo.setOnClickListener(mOnClickListener);
        container.addView(convertView);
        return convertView;
    }

    private class ViewHolder {
        ImageView ivUrl;
        Button btnVideo;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mConvertViews.add((View) object);
    }
}
