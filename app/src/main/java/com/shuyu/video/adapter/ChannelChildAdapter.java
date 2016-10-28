package com.shuyu.video.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.core.adapter.BaseAdapterHelper;
import com.shuyu.core.adapter.QuickAdapter;
import com.shuyu.video.R;
import com.shuyu.video.activity.VideoDetailsActivity;
import com.shuyu.video.model.VideoPicDetails;
import com.shuyu.video.utils.Constants;
import com.shuyu.video.utils.PayUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by zhangleilei on 9/27/16.
 */

public class ChannelChildAdapter extends QuickAdapter<VideoPicDetails> {

    private Random random = new Random();

    private int[] tagIds = new int[]{
            R.id.tv_tag1, R.id.tv_tag2
    };
    private int[] tagColorResId = new int[]{
            R.drawable.shape_round_red,
            R.drawable.shape_round_orange,
            R.drawable.shape_round_yellow,
            R.drawable.shape_round_light_green,
            R.drawable.shape_round_light_blue,
            R.drawable.shape_round_pink,
            R.drawable.shape_round_deep_purple

    };

    private void addTagsColor(List<VideoPicDetails> items) {
        if (items == null) return;
        for (int j = 0; j < items.size(); j++) {
            int index1 = random.nextInt(tagColorResId.length);
            int index2 = random.nextInt(tagColorResId.length);
            if (index1 == index2) {
                index1++;
                if (index1 == tagColorResId.length)
                    index1 = 0;
            }
            items.get(j).setTagColor(new int[]{tagColorResId[index1], tagColorResId[index2]});
        }
    }

    public ChannelChildAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
        addTagsColor(data);
    }

    @Override
    protected void convert(BaseAdapterHelper holder, VideoPicDetails item) {
        ImageView imageView = holder.getView(R.id.iv_channel_content_url);
        if (imageView != null) {
            Glide.with(mContext).load(item.getImgUrl()).error(R.mipmap.ic_default_image).into(imageView);
            holder.setOnClickListener(R.id.iv_channel_content_url, new MyOnClickListener(item));
        }
        holder.setText(R.id.tv_content_title, item.getTitle());
        holder.setText(R.id.tv_subtitle, item.getSubTitle());
        if (item.getContentType() == Constants.BANNER_VIDEO) {
            holder.setVisible(R.id.tv_view_number, View.VISIBLE);
            holder.setText(R.id.tv_view_number, item.getViewNumber());
        } else if (item.getContentType() == Constants.BANNER_PICTURE) {
            holder.setVisible(R.id.tv_view_number, View.GONE);
        }

        String[] tags = item.getTags();
        for (int i = 0; i < tags.length; i++) {
            if (i == 2) break;
            holder.setVisible(tagIds[i], View.VISIBLE);
            holder.setText(tagIds[i], tags[i]);
            holder.setVisible(tagIds[i], item.getTagColor()[i]);
        }
    }

    private class MyOnClickListener implements View.OnClickListener {

        VideoPicDetails child;

        MyOnClickListener(VideoPicDetails child) {
            this.child = child;
        }

        @Override
        public void onClick(View view) {
            if (child.getContentType() == Constants.BANNER_VIDEO) {
                Intent intent = new Intent(mContext, VideoDetailsActivity.class);
                intent.putExtra(Constants.VIDEO_DETAIL_ID, child.getId());
                mContext.startActivity(intent);
            } else if (child.getContentType() == Constants.BANNER_PICTURE) {
                PayUtils.canShowPic((AppCompatActivity) mContext, child.getFeeRule(), child.getId());
            }
        }
    }
}
