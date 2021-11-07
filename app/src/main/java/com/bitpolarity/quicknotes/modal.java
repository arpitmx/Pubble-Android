package com.bitpolarity.quicknotes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class modal extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.widgetdialog_activity);

        String dialogText = "This is the dialog text";
        TextView txt = (TextView) findViewById(R.id.w_dialog_txt);
        txt.setText(dialogText);

        Button dismissbutton = (Button) findViewById(R.id.w_dismiss_btn);
        dismissbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}