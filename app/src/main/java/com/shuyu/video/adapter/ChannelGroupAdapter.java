package com.shuyu.video.adapter;

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
    private List<ChannelContent.VideoChannelListBean> mChannelContents;
//    private MyOnClickListener mMyOnClickListener;

    public ChannelGroupAdapter(Context context) {
        mContext = context;
//        mMyOnClickListener = new MyOnClickListener();
    }

    public void setChannelContents(List<ChannelContent.VideoChannelListBean> channelContents) {
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
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
        }
        ChannelContent.VideoChannelListBean content = (ChannelContent.VideoChannelListBean) getGroup(i);
        holder.tvGroupTitle.setText(content.getTitle());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder holder;
        ChannelContentAdapter mContentAdapter;
        if (view == null) {
            holder = new ChildHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_channel_child, null);
            holder.mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_container);
            holder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        mContentAdapter = new ChannelContentAdapter(mContext,
                mChannelContents.get(i).getChannelContentList(),
                R.layout.item_channel_content);
        holder.mRecyclerView.setAdapter(mContentAdapter);
//        mMyOnClickListener.setChild(mChannelContents.get(i).getChannelContentList().get(i1));
//        mContentAdapter.setOnItemClickListener(mMyOnClickListener);
        return view;
    }

//    private class MyOnClickListener implements OnItemClickListener {
//
//        ChannelContent.VideoChannelListBean.ChannelContentListBean child;
//
//        void setChild(ChannelContent.VideoChannelListBean.ChannelContentListBean child) {
//            this.child = child;
//        }
//
//        @Override
//        public void onItemClick(View itemView, int viewType, int position) {
//            Intent intent = new Intent(mContext, VideoDetailsActivity.class);
//            intent.putExtra(Constants.VIDEO_DETAIL_ID, child.getId());
//            mContext.startActivity(intent);
//        }
//    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return mChannelContents.get(i).getChannelContentList().get(i1) != null;
    }

    private class GroupHolder {
        TextView tvGroupTitle;
    }

    private class ChildHolder {
        RecyclerView mRecyclerView;
    }

}
