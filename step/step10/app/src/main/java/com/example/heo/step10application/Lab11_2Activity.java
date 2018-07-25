package com.example.heo.step10application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class Lab11_2Activity extends AppCompatActivity implements View.OnClickListener {

    Button lineBtn;
    Button barBtn;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab11_2);

        webView = (WebView) findViewById(R.id.webview);
        lineBtn = (Button) findViewById(R.id.btn_chart_line);
        barBtn = (Button) findViewById(R.id.btn_chart_bar);

        lineBtn.setOnClickListener(this);
        barBtn.setOnClickListener(this);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/test.html");

        // var data=window.android.getChartData(); android 라는 이름으로 자바스크립트 객체에 등록됨
        webView.addJavascriptInterface(new JavascriptTest(), "android");

        //
        webView.setWebViewClient(new MyWebClient());
        webView.setWebChromeClient(new MyWebChrome());
    }

    //  자바(안드로이드)에서 자바스크립트 함수 호출
    @Override
    public void onClick(View v) {
        if (v == lineBtn) {
            webView.loadUrl("javascript:lineChart()");
        } else if (v == barBtn) {
            webView.loadUrl("javascript:barChart()");
        }
    }

    // 자바스크립트와 자바의 연동
    // var data=window.android.getChartData(); 자바스크립트에서 자바 함수 호출
    class JavascriptTest {
        @JavascriptInterface // 자바스크립트에서 호출할 함수에 다음 어노테이션을 선언해야 한다.
        public String getChartData() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("[");
            for (int i = 0; i < 14; i++) {
                buffer.append("[" + i + "," + Math.sin(i) + "]");
                if (i < 13) buffer.append(",");
            }
            buffer.append("]");
            return buffer.toString();
        }
    }

    // 예를들어, 링크를 클릭하는 순간, 자바에서 인지하여 로딩할려는 URL 분석해야 한다.

    class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Toast t = Toast.makeText(Lab11_2Activity.this, url, Toast.LENGTH_SHORT);
            t.show();
            return true;
        }
    }

    // 브라우저 이벤트를 처리할 때도 WebChromeClient 를 상속받는 이벤트 핸들러를 준비해야 한다.
    class MyWebChrome extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Toast t = Toast.makeText(Lab11_2Activity.this, message, Toast.LENGTH_SHORT);
            t.show();
            result.confirm();
            return true;
        }
    }
}
