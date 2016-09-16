package com.shuyu.core.api;

import com.shuyu.core.CoreApplication;
import com.shuyu.core.uils.SPUtils;
import com.shuyu.core.uils.ToastUtils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Azure on 2016/9/5.
 */

public class BaseApi {

    public static final String BASE_URL = "BASE_URL";
    //    public static final String LOCAL_SERVER_URL = "http://www.51shuyu.com:8008/";
    public static final String LOCAL_SERVER_URL = "http://101.201.233.134:8008/";

    public static <T> T createApi(Class<T> service) {
        final String url = SPUtils.get(CoreApplication.getApplication()
                , BASE_URL, LOCAL_SERVER_URL).toString() + "%20/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(CoreApplication.getApplication().genericClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(service);
    }

    public static <T> void request(Observable<T> observable, final IResponseListener<T> listener) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   ToastUtils.getInstance().showToast("网络异常,请稍后重试");
                                   if (listener != null) {
                                       listener.onFail();
                                   }
                               }

                               @Override
                               public void onNext(T data) {
                                   if (listener != null) {
                                       listener.onSuccess(data);
                                   }
                               }
                           }
                );
    }

    public interface IResponseListener<T> {

        void onSuccess(T data);

        void onFail();
    }

}
