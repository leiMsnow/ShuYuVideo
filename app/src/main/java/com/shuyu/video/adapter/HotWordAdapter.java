package com.shuyu.video.adapter;

import android.content.Context;
import android.view.View;

import com.shuyu.video.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by zhangleilei on 9/10/16.
 */

public class HotWordAdapter extends SuperAdapter<String> {

    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public HotWordAdapter(Context context, List<String> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
        holder.setText(R.id.tv_hot_word, item);
        holder.setTag(R.id.tv_hot_word, item);
        holder.setOnClickListener(R.id.tv_hot_word, mOnClickListener);
    }

}
