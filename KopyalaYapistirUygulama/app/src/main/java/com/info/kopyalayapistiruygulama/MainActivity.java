package com.info.kopyalayapistiruygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button buttonCopy, buttonGo;

    private ClipboardManager kopyalamaPano;
    private ClipData clipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        buttonCopy = findViewById(R.id.buttonKopyala);
        buttonGo = findViewById(R.id.buttonGit);

        kopyalamaPano = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String metin = editText.getText().toString().trim();

                if (!metin.isEmpty()) {
                    kopyala(metin);
                } else {
                    Snackbar.make(buttonCopy, "Lütfen bir şeyler yazınız...", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    public void kopyala(String metin) {
        clipData = ClipData.newPlainText("text", metin);
        kopyalamaPano.setPrimaryClip(clipData);

        Snackbar.make(buttonCopy, "Panoya kopyalandı...", Snackbar.LENGTH_LONG).show();
    }
}