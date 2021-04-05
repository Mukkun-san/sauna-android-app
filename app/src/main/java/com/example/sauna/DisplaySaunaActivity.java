package com.example.sauna;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class DisplaySaunaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_display_sauna);
        Intent it = getIntent();
        String code = it.getStringExtra("code");
        Sauna S = SaunaList.getSauna(code);
        Uri uri = Uri.parse(S.getImageURL());
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.display_img);
        draweeView.setImageURI(uri);

        ((TextView) findViewById(R.id.display_make)).setText(S.getMake());
        ((TextView) findViewById(R.id.display_capacity)).setText(String.valueOf(S.getCapacity()) + " persons");
        ((TextView) findViewById(R.id.display_woodType)).setText(S.getWoodType());
        ((TextView) findViewById(R.id.display_make)).setText(S.getMake());
        ((TextView) findViewById(R.id.display_code)).setText(S.getProductCode());
        ((TextView) findViewById(R.id.display_heating)).setText(S.getKindOfHeating());
        ((TextView) findViewById(R.id.display_price)).setText("$" + String.valueOf(S.getPrice()));

    }

}
