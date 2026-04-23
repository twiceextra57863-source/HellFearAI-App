package com.hellfear.ai;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    EditText codeInput;
    String githubToken = "YOUR_GITHUB_TOKEN"; // Token should be entered in app settings later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.ai_webview);
        codeInput = findViewById(R.id.code_input);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true); // For Login
        webView.setWebViewClient(new WebViewClient());

        findViewById(R.id.btn_chatgpt).setOnClickListener(v -> webView.loadUrl("https://chatgpt.com"));
        findViewById(R.id.btn_gemini).setOnClickListener(v -> webView.loadUrl("https://google.com"));
        findViewById(R.id.btn_deepseek).setOnClickListener(v -> webView.loadUrl("https://deepseek.com"));

        findViewById(R.id.btn_compile).setOnClickListener(v -> {
            String code = codeInput.getText().toString();
            if(!code.isEmpty()) {
                pushCodeToGithub(code);
            } else {
                Toast.makeText(this, "Please paste code first!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pushCodeToGithub(String code) {
        // This is where the app talks to your GitHub Repo
        // It will trigger the Workflow we made earlier
        Toast.makeText(this, "Pushing to GitHub...", Toast.LENGTH_SHORT).show();
        // GitHub API Implementation Logic...
    }
}
