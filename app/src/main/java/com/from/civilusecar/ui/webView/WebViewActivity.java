package com.from.civilusecar.ui.webView;


import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.from.civilusecar.R;
import com.from.civilusecar.base.BaseTitleActivity;
import com.from.civilusecar.mvp.contract.WebConstract;
import com.from.civilusecar.mvp.contract.WelcomeContract;
import com.from.civilusecar.mvp.presenter.WebPresenter;
import com.from.civilusecar.mvp.presenter.WelcomePresenter;
import com.xslong.xslonglib.base.BaseActivity;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;

import butterknife.BindView;


/**
 * Created by Administrator on 2017/4/13.
 */

public class WebViewActivity extends  BaseTitleActivity<WebPresenter> implements WebConstract.View {
    private final static String url="http://139.9.93.206:9000/statics/h5/Assets/images/userGuide3.jpg";

    @BindView(R.id.webView)
    WebView webView;

    @Override
    public void webData(BaseResponse data) {

    }

    @Override
    protected int getSubLayoutId() {
        return R.layout.layout_web;
    }


    @Override
    protected WebPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        mTvTitle.setText("关于我们");
        initWebView();
    }

    //加载webview的方法
    private void initWebView() {
        final WebSettings webSettings= webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);//是否支持JavaScrip
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webSettings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webSettings.setSupportZoom(true);//是否可以缩放，默认true
        webSettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webSettings.setAppCacheEnabled(true);//是否使用缓存
        webSettings.setDomStorageEnabled(true);//DOM Storage
// displayWebview.getSettings().setUserAgentString("User-Agent:Android");//设置用户代理，一般不用

        webSettings.setDefaultTextEncodingName("utf-8");//设置解码时默认编码
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); //在当前的webview中跳转到新的url
                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            @Override //页面启动的时候
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
        webView.loadUrl( url);
    }
}
