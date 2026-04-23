package com.hellfear.ai;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private WebView webView;
    private EditText codeInput, repoInput;
    private String githubToken;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. UI Initialization
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        webView = findViewById(R.id.ai_webview);
        codeInput = findViewById(R.id.code_input);
        repoInput = findViewById(R.id.repo_input); // Ensure this ID is in your XML

        // 2. Navigation Drawer Toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // 3. WebView Settings
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://chatgpt.com");

        // 4. AI Selector Buttons
        findViewById(R.id.btn_chatgpt).setOnClickListener(v -> webView.loadUrl("https://chatgpt.com"));
        findViewById(R.id.btn_gemini).setOnClickListener(v -> webView.loadUrl("https://google.com"));
        findViewById(R.id.btn_deepseek).setOnClickListener(v -> webView.loadUrl("https://deepseek.com"));

        // 5. Compile & Pull Request Button
        findViewById(R.id.btn_compile).setOnClickListener(v -> {
            String code = codeInput.getText().toString();
            String repo = repoInput.getText().toString(); // format: username/reponame
            
            // Get Token from SharedPreferences (Add a settings page later to save this)
            SharedPreferences prefs = getSharedPreferences("HellFearPrefs", Context.MODE_PRIVATE);
            githubToken = prefs.getString("gh_token", "");

            if (code.isEmpty() || repo.isEmpty() || githubToken.isEmpty()) {
                Toast.makeText(this, "Check Code, Repo Name, or Token!", Toast.LENGTH_SHORT).show();
            } else {
                startDeployment(repo, code);
            }
        });
    }

    private void startDeployment(String repo, String code) {
        String branchName = "hellfear-ai-" + System.currentTimeMillis();
        String path = "src/main/java/com/hellfear/mod/Mod.java";

        Toast.makeText(this, "Deployment Started...", Toast.LENGTH_SHORT).show();

        // Step 1: Create Branch -> Step 2: Push File -> Step 3: Create PR
        // For brevity, this example shows direct file push to main for simple testing
        // You can expand this with Branch/PR logic as needed.
        pushFileToGithub(repo, path, code, "main");
    }

    private void pushFileToGithub(String repo, String path, String content, String branch) {
        String url = "https://github.com" + repo + "/contents/" + path;
        String encodedContent = Base64.getEncoder().encodeToString(content.getBytes());

        JSONObject json = new JSONObject();
        try {
            json.put("message", "AI Generated Mod by HellFearAI");
            json.put("content", encodedContent);
            json.put("branch", branch);
        } catch (Exception e) { e.printStackTrace(); }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "token " + githubToken)
                .put(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to connect!", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Success! Workflow Active.", Toast.LENGTH_LONG).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
