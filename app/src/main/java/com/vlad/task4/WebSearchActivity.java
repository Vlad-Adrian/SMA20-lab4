package com.vlad.task4;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WebSearchActivity extends AppCompatActivity {
    final static String EXTRA_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.websearch_layout);

        WebView mWebView = findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyCustomWebViewClient());
        mWebView.loadUrl("https://www.google.ro/imghp?hl=ro&tab=wi&ogbl");
    }

    public void loadImage(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData abc = clipboardManager.getPrimaryClip();
        ClipData.Item item = abc.getItemAt(0);
        String url = item.getText().toString();

        if (!url.contains("https://images.app.goo.gl/"))
            Toast.makeText(this, "Url not valid.", Toast.LENGTH_SHORT).show();
        else {
            if (view.getId() == R.id.bLoadBackground) {
                Intent intent = new Intent(this, ImageIntentService.class);
                intent.putExtra(EXTRA_URL, url);
                startService(intent);
            } else if (view.getId() == R.id.bLoadForeground) {
                Intent startIntent = new Intent(this, ForeGroundImageService.class);
                startIntent.setAction(ForeGroundImageService.STARTFOREGROUND_ACTION);
                startIntent.putExtra(EXTRA_URL, url);
                startService(startIntent);
            }
        }
    }

    private class MyCustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).toString().startsWith("https://www.google.ro/search?")
                    && Uri.parse(url).toString().contains("tbm=isch")) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

}