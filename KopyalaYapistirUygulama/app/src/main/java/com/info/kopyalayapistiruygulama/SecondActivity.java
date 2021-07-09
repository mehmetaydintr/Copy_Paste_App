package com.info.kopyalayapistiruygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private Button buttonPaste;

    private ClipboardManager kopyalamaPano;
    private ClipData clipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textView);
        buttonPaste = findViewById(R.id.buttonYapistir);

        kopyalamaPano = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        buttonPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yapistir();
            }
        });
    }

    public void yapistir(){
        clipData = kopyalamaPano.getPrimaryClip();
        ClipData.Item item = clipData.getItemAt(0);

        String kopyalananYazi = item.getText().toString();

        textView.setText(kopyalananYazi);

        Snackbar.make(buttonPaste, "Yazı yapıştırıldı...", Snackbar.LENGTH_LONG).show();
    }
}