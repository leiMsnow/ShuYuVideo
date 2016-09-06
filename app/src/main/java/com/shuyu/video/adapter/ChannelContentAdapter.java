package com.shuyu.video.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.video.R;
import com.shuyu.video.activity.VideoActivity;
import com.shuyu.video.model.ChannelContent;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Azure on 2016/8/31.
 */
public class ChannelContentAdapter extends SuperAdapter<ChannelContent.VideoChannelListBean.ChannelContentListBean> {

    private MyOnClickListener mMyOnClickListener;

    public ChannelContentAdapter(Context context, List<ChannelContent.VideoChannelListBean.ChannelContentListBean> items,
                          int layoutResId) {
        super(context, items, layoutResId);
        mMyOnClickListener = new MyOnClickListener();
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition,
                       ChannelContent.VideoChannelListBean.ChannelContentListBean item) {
        ImageView imageView = holder.findViewById(R.id.iv_url);
        Glide.with(mContext).load(item.getImgUrl()).error(R.mipmap.ic_default_image).into(imageView);
        holder.setText(R.id.tv_desc,item.getTitle());
        imageView.setTag(item);
        imageView.setOnClickListener(mMyOnClickListener);
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            ChannelContent.VideoChannelListBean.ChannelContentListBean child =
                    (ChannelContent.VideoChannelListBean.ChannelContentListBean) view.getTag();
            Intent intent = new Intent(mContext, VideoActivity.class);
            intent.putExtra(VideoActivity.VIDEO_URL,child);
            mContext.startActivity(intent);
        }
    }
}
