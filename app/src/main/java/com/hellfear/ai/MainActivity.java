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

    // GitHub API call using OkHttp
private void createPullRequest(String code) {
    String repoOwner = "your_username";
    String repoName = "HellFearAI-Mods";
    String fileName = "src/main/java/com/example/MyMod.java";
    String branchName = "ai-build-" + System.currentTimeMillis();

    OkHttpClient client = new OkHttpClient();

    // 1. Create Branch
    // 2. Push File
    // 3. Create PR
    // (We will use separate methods for all of these)
    
    Toast.makeText(this, "PR Created! Checking GitHub Actions...", Toast.LENGTH_LONG).show();
}
)
