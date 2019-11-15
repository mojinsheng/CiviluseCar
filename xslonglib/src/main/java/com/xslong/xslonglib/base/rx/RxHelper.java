package com.xslong.xslonglib.base.rx;


/**
 * des:对服务器返回数据成功和失败处理
 * Created by xsf
 * on 2016.09.9:59
 */

/**************使用例子******************/
/*_apiService.login(mobile, verifyCode)
        .compose(RxSchedulersHelper.io_main())
        .compose(RxResultHelper.handleResult())
        .//省略*/

public class RxHelper {
//    /**
//     * 对服务器返回数据进行预处理
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<BaseResponse<T>, T> handleResult() {
//        return new Observable.Transformer<BaseResponse<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<BaseResponse<T>> tObservable) {
//                return tObservable.flatMap(new Func1<BaseResponse<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(BaseResponse<T> result) {
//                        LogUtils.d("result from api : " + result);
//                        if (result.success()) {
//                            return createData(result.data);
//                        } else {
//                            return Observable.error(new ServerException(result.msg));
//                        }
//                    }
//                });
//            }
//        };
//
//    }
//
//    /**
//     * 创建成功的数据
//     *
//     * @param data
//     * @param <T>
//     * @return
//     */
//    private static <T> Observable<T> createData(final T data) {
//        return Observable.create(new Observable.OnSubscribe<T>() {
//            @Override
//            public void call(Subscriber<? super T> subscriber) {
//                try {
//                    subscriber.onNext(data);
//                    subscriber.onCompleted();
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                }
//            }
//        });
//
//    }
}
