package com.cxz.baselibs.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cxz.baselibs.R;

/**
 * @author chenxz
 * @date 2018/11/21
 * @desc CustomToast
 */
public class CustomToast {

    private Toast toast;
    private TextView textView;

    public CustomToast(Context context, String message) {
        this(context, message, Toast.LENGTH_LONG);
    }

    public CustomToast(Context context, String message, int duration) {
        toast = new Toast(context);
        toast.setDuration(duration);
        View view = View.inflate(context, R.layout.toast_custom, null);
        textView = view.findViewById(R.id.tv_prompt);
        textView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    public void show() {
        if (toast != null) {
            toast.show();
        }
    }

}
