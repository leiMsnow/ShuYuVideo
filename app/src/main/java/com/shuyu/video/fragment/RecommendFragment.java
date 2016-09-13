package com.shuyu.video.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.api.BaseApi;
import com.shuyu.video.R;
import com.shuyu.video.adapter.AppSoreAdapter;
import com.shuyu.video.api.IMainApi;
import com.shuyu.video.model.AppStoreEntity;

import butterknife.Bind;

import static com.shuyu.core.api.BaseApi.createApi;

public class RecommendFragment extends BaseFragment {

    @Bind(R.id.rv_container)
    RecyclerView mRvContainer;

    private AppSoreAdapter mAppSoreAdapter;

    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initData() {
        mAppSoreAdapter = new AppSoreAdapter(mContext, null, R.layout.item_recommend);
        mRvContainer.setLayoutManager(new LinearLayoutManager(mContext));
        mRvContainer.setAdapter(mAppSoreAdapter);
        mAppSoreAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        getAppStoreInfo();
    }


    private void getAppStoreInfo() {
        BaseApi.request(createApi(IMainApi.class).getAppStoreList(1),
                new BaseApi.IResponseListener<AppStoreEntity>() {
                    @Override
                    public void onSuccess(AppStoreEntity data) {
                        mAppSoreAdapter.replaceAll(data.getAppInfoList());
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    private void download(String url) {


//        Call<ResponseBody> call = BaseApi.createApi(IDownload.class).downloadFile(url);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    InputStream is = response.body().byteStream();
//                    File file = new File(Environment.getExternalStorageDirectory(), "12345.apk");
//                    FileOutputStream fos = new FileOutputStream(file);
//                    BufferedInputStream bis = new BufferedInputStream(is);
//                    byte[] buffer = new byte[1024];
//                    int len;
//                    while ((len = bis.read(buffer)) != -1) {
//                        fos.write(buffer, 0, len);
//                        fos.flush();
//                    }
//                    fos.close();
//                    bis.close();
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
    }
}
