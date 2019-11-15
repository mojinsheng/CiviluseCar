package com.xslong.xslonglib.retrofit.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.xslong.xslonglib.base.bean.BaseResponse;
import com.xslong.xslonglib.base.rx.exception.ApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Desc : 自定义了一个GsonConverterFactory
 * Created by huangyue on 2017/10/11.
 */

public class GsonConverterFactory extends Converter.Factory {
    private Gson gson;
    private final Charset UTF_8 = Charset.forName("UTF-8");

    private GsonConverterFactory(Gson gson) {
        if (gson == null)
            throw new NullPointerException("gson is null");
        this.gson = gson;
    }

    public static GsonConverterFactory create() {
        return create(new Gson());
    }

    private static GsonConverterFactory create(Gson gson) {
        return new GsonConverterFactory(gson);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequextBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(gson, adapter);
    }

    final class GsonRequextBodyConverter<T> implements Converter<T, RequestBody> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

        private final Gson gson;
        private final TypeAdapter<T> adapter;
        GsonRequextBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }

    final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String response = value.string();
            BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
            if(!baseResponse.success()) {
                value.close();
                throw new ApiException(baseResponse.getState(), baseResponse.getMsg());
            }

            MediaType contentType = value.contentType();
            Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
            InputStream inputStream =  new ByteArrayInputStream(response.getBytes());
            Reader reader = new InputStreamReader(inputStream, charset);
            JsonReader jsonReader = gson.newJsonReader(reader);
            try{
                return adapter.read(jsonReader);
            } finally {
                value.close();
            }
        }
    }
}
