package com.xslong.xslonglib.retrofit;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.xslong.xslonglib.retrofit.converter.GsonConverterFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Desc :
 *
 * @author huangyue
 * @date 2017/10/10
 */
public class RxRetrofit {
    private static final int READ_TIME_OUT = 30;
    private static final int CONNECT_TIME_OUT = 30;
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(String baseUrl) {
        //初始化HttpLoggingInterceptor

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        //打印日志
                        FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                                .tag("HTTP_LOG")
                                .build();
                        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
                        Logger.w("mm"+message);

                    }
                });
        //日志级别
//        BASEIC:请求/响应行
//        HEADER:请求/响应行 + 头
//        BODY:请求/响应航 + 头 + 体
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile = new File("", "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);  //100Mb

        //配置okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                //                .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)//设置写入超时时间
               // .addInterceptor(loggingInterceptor)   //添加日志拦截器
        .addInterceptor(new LoggingInterceptor())
                .cache(cache)
                .build();

        //配置retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) //支持把请求的json转换bean
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }
   static class LoggingInterceptor implements Interceptor {
        String TAG = "mmm";

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                return response;
            }
            okhttp3.MediaType mediaType = responseBody.contentType();
            String content = response.body().string();
            Log.e(TAG, "请求地址:  " + request.toString());
            Log.e(TAG, "请求状态:  " + response.code() + " : " + response.message());
            String method = request.method();
            if ("POST".equals(method)) {
                Buffer buffer = new Buffer();
                try {
                    request.body().writeTo(buffer);
                    Charset charset = Charset.forName("UTF-8");
                    MediaType contentType = request.body().contentType();
                    if (contentType != null) {
                        charset = contentType.charset(UTF_8);
                    }
                    String params = buffer.readString(charset);
                    Log.e(TAG, "请求参数:  " + params);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.e(TAG, "请求结果:  " + content);
            Log.e(TAG, "==================================请求耗时:  " + duration + "毫秒===================================");
            return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, content)).build();

        }
    }
}
