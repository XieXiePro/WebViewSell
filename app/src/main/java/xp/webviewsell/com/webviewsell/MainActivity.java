package xp.webviewsell.com.webviewsell;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView webAddressWv;
    EditText webAddressEt;
    Button webAddressBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webAddressWv = (WebView) findViewById(R.id.web_address_wv);
        webAddressEt = (EditText) findViewById(R.id.web_address_et);
        webAddressBt = (Button) findViewById(R.id.web_address_bt);

        WebSettings webSettings = webAddressWv.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        String url = "https://www.baidu.com";
        webAddressWv.loadUrl(url);
        Log.d("MainActivity", "url: " + url);
        //设置Web视图
        webAddressWv.setWebViewClient(new webViewClient());
        webAddressBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载需要显示的网页
                String url = webAddressEt.getText().toString();
                if (checkUrl(url)) {
                    webAddressWv.loadUrl(url);
                    Log.d("MainActivity", "url: " + url);
                }
            }
        });
    }

    @NonNull
    private boolean checkUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(this, "URL 不能为空~", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.WEB_URL.matcher(url).matches()) {
            Toast.makeText(this, "URL 非法，请输入有效的URL链接:" + url, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webAddressWv.canGoBack()) {
            webAddressWv.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        finish();//结束退出程序
        return false;
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}