package com.shuyu.video.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuyu.video.R;
import com.shuyu.video.model.VideoPicDetails;
import com.shuyu.video.utils.PayUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangleilei on 9/10/16.
 */

public class VipPageAdapter extends PagerAdapter {

    private Context mContext;
    private List<VideoPicDetails> mLiveVideoDataList = new ArrayList<>();
    private LinkedList<View> mConvertViews = new LinkedList<>();
    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setLiveVideoDataList(List<VideoPicDetails> liveVideoDataList) {
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
            holder.ivVip = (ImageView) convertView.findViewById(R.id.iv_vip);
            holder.ivUrl = (ImageView) convertView.findViewById(R.id.iv_url);
            holder.ivPlay = (ImageView) convertView.findViewById(R.id.iv_play);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_video_title);
            convertView.setTag(holder);
        } else {
            convertView = mConvertViews.removeFirst();
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mLiveVideoDataList.get(position).getImgUrl()).into(holder.ivUrl);
        holder.ivVip.setImageResource(PayUtils.getVipIcon(mLiveVideoDataList.get(position).getFeeRule()));
        holder.tvTitle.setText(mLiveVideoDataList.get(position).getTitle());
        holder.ivPlay.setTag(mLiveVideoDataList.get(position));
        holder.ivPlay.setOnClickListener(mOnClickListener);
        container.addView(convertView);
        return convertView;
    }

    private class ViewHolder {
        ImageView ivVip;
        ImageView ivUrl;
        ImageView ivPlay;
        TextView tvTitle;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mConvertViews.add((View) object);
    }
}
