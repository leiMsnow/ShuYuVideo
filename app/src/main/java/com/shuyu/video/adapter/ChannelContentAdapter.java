package com.shuyu.video.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.video.R;
import com.shuyu.video.activity.PictureDetailsActivity;
import com.shuyu.video.activity.VideoDetailsActivity;
import com.shuyu.video.model.VideoPicDetails;
import com.shuyu.video.utils.Constants;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Azure on 2016/8/31.
 */
public class ChannelContentAdapter extends SuperAdapter<VideoPicDetails> {

    ChannelContentAdapter(Context context, List<VideoPicDetails> items,
                          int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition,
                       VideoPicDetails item) {
        ImageView imageView = holder.findViewById(R.id.iv_channel_content_url);
        if (imageView != null) {
            Glide.with(mContext).load(item.getImgUrl()).error(R.mipmap.ic_default_image).into(imageView);
            holder.setOnClickListener(R.id.iv_channel_content_url, new MyOnClickListener(item));
        }
        holder.setText(R.id.tv_content_title, item.getTitle());
        holder.setText(R.id.tv_content_desc, item.getDescription());
        if (item.getContentType()==Constants.BANNEL_VIDEO) {
            holder.setVisibility(R.id.iv_play, View.VISIBLE);
        }else if (item.getContentType()==Constants.BANNEL_PICTURE){
            holder.setVisibility(R.id.iv_play, View.GONE);
        }
    }

    private class MyOnClickListener implements View.OnClickListener {

        VideoPicDetails child;

        MyOnClickListener(VideoPicDetails child) {
            this.child = child;
        }

        @Override
        public void onClick(View view) {
            if (child.getContentType()==Constants.BANNEL_VIDEO){
                Intent intent = new Intent(mContext, VideoDetailsActivity.class);
                intent.putExtra(Constants.VIDEO_DETAIL_ID, child.getId());
                mContext.startActivity(intent);
            }else if(child.getContentType()==Constants.BANNEL_PICTURE){
                Intent intent = new Intent(mContext, PictureDetailsActivity.class);
                intent.putExtra(Constants.PICTURE_DETAIL_ID, child.getId());
                mContext.startActivity(intent);
            }
        }
    }
}
