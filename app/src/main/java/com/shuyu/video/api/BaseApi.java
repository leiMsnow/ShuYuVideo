package com.shuyu.video.api;

import com.shuyu.core.CoreApplication;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.NetUtils;
import com.shuyu.core.uils.SPUtils;
import com.shuyu.core.uils.ToastUtils;
import com.shuyu.video.MyApplication;
import com.shuyu.video.converter.MyConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Azure on 2016/9/5.
 */
public class BaseApi {

    public static final String KEY_BASE_URL = "KEY_BASE_URL";
    private static final String LOCAL_SERVER_URL = "http://118.178.87.139";
    private static final String PAY_SERVER_URL = "http://pay-cps.isycdn.com";
//    private static final String PAY_SERVER_URL = LOCAL_SERVER_URL + ":8009";
    public static final String BASE_URL = LOCAL_SERVER_URL + ":7008/";
    public static final String PAY_URL = PAY_SERVER_URL + "/pay/";
    public static final String ORDER_URL = PAY_SERVER_URL + "/order/";
    public static final String NOTICE_URL = PAY_SERVER_URL + "/notice/";
    public static final String LOG_URL = PAY_SERVER_URL + "/log/";

    public static <T> T createApi(Class<T> service) {
        final String url = SPUtils.get(CoreApplication.getApplication()
                , KEY_BASE_URL, BASE_URL).toString() + "%20/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(MyApplication.getApplication().genericClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MyConverterFactory.create())
                .build();
        return retrofit.create(service);
    }

    public static <T> void request(Observable<T> observable,
                                   final IResponseListener<T> listener) {

        if (!NetUtils.isConnected(MyApplication.getApplication())) {
            ToastUtils.getInstance().showToast("网络不可用,请连接网络后重启APP");
            if (listener != null) {
                listener.onFail();
            }
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                                   LogUtils.d("onError", e.getMessage());
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
