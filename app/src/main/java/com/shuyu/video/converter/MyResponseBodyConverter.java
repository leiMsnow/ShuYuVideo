package com.shuyu.video.converter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.video.utils.DataSignUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by zhangleilei on 10/27/16.
 */

final class MyResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private final Gson mGson;
    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */
    public MyResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.mGson = gson;
        this.adapter = adapter;
    }

    /**
     * 转换
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {

        String response = responseBody.string();

        String result = DataSignUtils.decryptData(response);//解密
        if (!TextUtils.isEmpty(result)) {
            LogUtils.d(MyResponseBodyConverter.class.getName(), "解密的服务器数据：" + result);
        } else {
            result = response;
        }
        responseBody = ResponseBody.create(MEDIA_TYPE, result);
        JsonReader jsonReader = mGson.newJsonReader(responseBody.charStream());
        try {
            return adapter.read(jsonReader);
        } finally {
            responseBody.close();
        }
    }
}
