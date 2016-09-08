package com.shuyu.video.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.video.R;
import com.shuyu.video.model.VideoComment;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Azure on 2016/9/8.
 */
public class VideoCommentAdapter extends SuperAdapter<VideoComment> {

    public VideoCommentAdapter(Context context, List<VideoComment> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, VideoComment item) {
        holder.setText(R.id.tv_comment,item.getContent());
        ImageView ivHeader = holder.findViewById(R.id.iv_header_url);
        Glide.with(mContext).load(item.getHeadImgUrl()).error(R.mipmap.ic_contact_picture).into(ivHeader);
    }
}
