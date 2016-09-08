package com.shuyu.video.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.video.R;
import com.shuyu.video.activity.VideoDetailsActivity;
import com.shuyu.video.model.ChannelContent;
import com.shuyu.video.utils.Constants;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Azure on 2016/8/31.
 */
public class ChannelContentAdapter extends SuperAdapter<ChannelContent.VideoChannelListBean.ChannelContentListBean> {


    private MyOnClickListener mOnClickListener;

    ChannelContentAdapter(Context context, List<ChannelContent.VideoChannelListBean.ChannelContentListBean> items,
                          int layoutResId) {
        super(context, items, layoutResId);
        mOnClickListener = new MyOnClickListener();
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition,
                       ChannelContent.VideoChannelListBean.ChannelContentListBean item) {
        ImageView imageView = holder.findViewById(R.id.iv_url);
        if (imageView!=null)
            Glide.with(mContext).load(item.getImgUrl()).error(R.mipmap.ic_default_image).into(imageView);
        holder.setText(R.id.tv_content_title,item.getTitle());
        holder.setText(R.id.tv_content_desc,item.getDescription());
        mOnClickListener.setChild(item);
        holder.setOnClickListener(R.id.iv_url,mOnClickListener);
    }
    private class MyOnClickListener implements View.OnClickListener {

        ChannelContent.VideoChannelListBean.ChannelContentListBean child;

        void setChild(ChannelContent.VideoChannelListBean.ChannelContentListBean child) {
            this.child = child;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, VideoDetailsActivity.class);
            intent.putExtra(Constants.VIDEO_DETAIL_ID, child.getId());
            mContext.startActivity(intent);
        }
    }
}
