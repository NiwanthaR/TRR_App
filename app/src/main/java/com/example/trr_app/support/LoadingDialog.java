package com.example.trr_app.support;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.NonNull;

import com.example.trr_app.R;

public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        super(context);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        setCancelable(false);
        setTitle(null);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog,null);
        setContentView(view);

    }

}
