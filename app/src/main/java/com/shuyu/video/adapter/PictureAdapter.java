package com.shuyu.video.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.video.R;
import com.shuyu.video.model.PictureDetails;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Azure on 2016/9/11.
 */

public class PictureAdapter extends SuperAdapter<PictureDetails> {

    public PictureAdapter(Context context, List<PictureDetails> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, PictureDetails item) {
        holder.setText(R.id.tv_picture_desc,item.getTitle());
        ImageView imageView = holder.findViewById(R.id.iv_picture_url);
        Glide.with(mContext).load(item.getImgurlthumbnail()).error(R.mipmap.ic_default_image).into(imageView);
    }

}
