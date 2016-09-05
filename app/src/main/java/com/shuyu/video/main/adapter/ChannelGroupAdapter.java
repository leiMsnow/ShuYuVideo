package com.shuyu.video.main.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.shuyu.video.R;
import com.shuyu.video.model.ChannelContent;

import java.util.List;

/**
 * Created by Azure on 2016/8/31.
 */
public class ChannelGroupAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<ChannelContent> mChannelContents;
    private ChannelContentAdapter mContentAdapter;

    public ChannelGroupAdapter(Context context) {
        mContext = context;
    }

    public void setChannelContents(List<ChannelContent> channelContents) {
        mChannelContents = channelContents;
    }

    @Override
    public int getGroupCount() {
        return mChannelContents.size();
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
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
        }
        ChannelContent content = (ChannelContent) getGroup(i);
        holder.tvGroupTitle.setText(content.getTitle());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder holder;
        if (view == null) {
            holder = new ChildHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_channel_child, null);
            holder.mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_container);
            mContentAdapter = new ChannelContentAdapter(mContext, null,
                    R.layout.item_channel_content);
            holder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
//            holder.mRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
            holder.mRecyclerView.setAdapter(mContentAdapter);
        }
        mContentAdapter.replaceAll(mChannelContents.get(i).getChannelContentList());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder {
        TextView tvGroupTitle;
    }

    private class ChildHolder {
        RecyclerView mRecyclerView;
    }

}
