package com.shuyu.video.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuyu.video.R;
import com.shuyu.video.model.SubChannel;
import com.shuyu.video.utils.Constants;

import java.util.List;

/**
 * Created by Azure on 2016/8/31.
 */
public class ChannelGroupAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<SubChannel> mChannelContents;

    public ChannelGroupAdapter(Context context) {
        mContext = context;
    }

    public void setChannelContents(List<SubChannel> channelContents) {
        mChannelContents = channelContents;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mChannelContents == null ? 0 : mChannelContents.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return mChannelContents.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mChannelContents.get(i).getChannelContentList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return mChannelContents.get(i).getId();
    }

    @Override
    public long getChildId(int i, int i1) {
        return mChannelContents.get(i).getChannelContentList().get(i1).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupHolder holder;
        if (view == null) {
            holder = new GroupHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_channel_title, null);
            holder.tvGroupTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.ivTags = (ImageView) view.findViewById(R.id.iv_channel_tags);
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
        }
        SubChannel content = (SubChannel) getGroup(i);
        if (i == 0) {
            holder.ivTags.setImageResource(R.mipmap.ic_channel_hot_tags);
        } else {
            if (content.getChannelContentList().get(0).getContentType() == Constants.BANNER_VIDEO) {
                holder.ivTags.setImageResource(R.mipmap.ic_channel_tv_tags);
            } else if (content.getChannelContentList().get(0).getContentType() == Constants.BANNER_PICTURE) {
                holder.ivTags.setImageResource(R.mipmap.ic_channel_img_tags);
            }
        }
        holder.tvGroupTitle.setText(content.getTitle());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder holder;
        ChannelChildAdapter mContentAdapter;
        if (view == null) {
            holder = new ChildHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_channel_child, null);
            holder.mRecyclerView = (GridView) view.findViewById(R.id.rv_container);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }

        mContentAdapter = new ChannelChildAdapter(mContext,
                R.layout.item_channel_content,
                mChannelContents.get(i).getChannelContentList());

        holder.mRecyclerView.setAdapter(mContentAdapter);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return mChannelContents.get(i).getChannelContentList().get(i1) != null;
    }

    private class GroupHolder {
        ImageView ivTags;
        TextView tvGroupTitle;
    }

    private class ChildHolder {
        GridView mRecyclerView;
    }

}
