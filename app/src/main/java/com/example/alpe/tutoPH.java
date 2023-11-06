package com.example.alpe;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class tutoPH extends Activity {
    WebView v1;
    WebView v2;
    WebView v3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoph);
        v1 = findViewById(R.id.webView);
        String video1 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/M3T7aEoA8BI?si=UCXfDnwUvgrzfIQ0\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        v1.loadData(video1, "text/html", "utf-8");
        v1.getSettings().setJavaScriptEnabled(true);
        v1.setWebChromeClient(new WebChromeClient());

        v2 = findViewById(R.id.video2);
        String video2 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/I36MUa_7HgA?si=Etum8lyQWwKc7Num\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        v2.loadData(video2, "text/html", "utf-8");
        v2.getSettings().setJavaScriptEnabled(true);
        v2.setWebChromeClient(new WebChromeClient());

        v3 = findViewById(R.id.video3);
        String video3 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WGdedqmMIk8?si=aOK2KSimTAZ2-14S\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        v3.loadData(video3, "text/html", "utf-8");
        v3.getSettings().setJavaScriptEnabled(true);
        v3.setWebChromeClient(new WebChromeClient());

    }


}
