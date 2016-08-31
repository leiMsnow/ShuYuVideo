package com.shuyu.video.main.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.video.R;
import com.shuyu.video.model.ChannelContent;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Azure on 2016/8/31.
 */

public class ChannelContentAdapter extends SuperAdapter<ChannelContent.ChannelContentListBean> {

    public ChannelContentAdapter(Context context, List<ChannelContent.ChannelContentListBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ChannelContent.ChannelContentListBean item) {
        ImageView imageView = holder.findViewById(R.id.iv_url);
        Glide.with(mContext).load(item.getImgUrl()).error(R.mipmap.ic_default_image).into(imageView);
    }
}
