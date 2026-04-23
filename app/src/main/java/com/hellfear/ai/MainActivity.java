package com.hellfear.ai;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    WebView webView;
    EditText codeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Setup UI
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        webView = findViewById(R.id.ai_webview);
        codeInput = findViewById(R.id.code_input);

        // 2. Navigation Drawer Toggle (Menu Button)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // 3. WebView Settings
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://chatgpt.com"); // Default

        // 4. Button Actions
        findViewById(R.id.btn_chatgpt).setOnClickListener(v -> webView.loadUrl("https://chatgpt.com"));
        findViewById(R.id.btn_gemini).setOnClickListener(v -> webView.loadUrl("https://google.com"));
        findViewById(R.id.btn_deepseek).setOnClickListener(v -> webView.loadUrl("https://deepseek.com"));

        findViewById(R.id.btn_compile).setOnClickListener(v -> {
            String myCode = codeInput.getText().toString();
            if(!myCode.isEmpty()) {
                // Yahan hum GitHub API call karenge (Step 3 mein)
                Toast.makeText(this, "HellFearAI: Committing to GitHub...", Toast.LENGTH_SHORT).show();
            }
        });

        // 5. Menu Clicks
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_settings) {
                Toast.makeText(this, "Settings: Enter Token Here", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawers();
            return true;
        });
    }
}
