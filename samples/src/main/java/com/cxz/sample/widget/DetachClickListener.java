package com.cxz.sample.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.ViewTreeObserver;

/**
 * 解决 AlertDialog 引起的内存泄漏问题
 *
 * DetachClickListener clickListener = DetachClickListener.wrap(
 *                 new DialogInterface.OnClickListener() {
 *                     @Override
 *                     public void onClick(DialogInterface dialog, int which) {
 *                     }
 *                 });
 * AlertDialog alertDialog = new AlertDialog.Builder(this)
 *         .setPositiveButton("confirm", clickListener).create();
 * alertDialog.show();
 * clickListener.clearOnDetach(alertDialog);
 */
public class DetachClickListener implements DialogInterface.OnClickListener {

    public static DetachClickListener wrap(DialogInterface.OnClickListener delegate) {
        return new DetachClickListener(delegate);
    }

    private DialogInterface.OnClickListener mDelegate;

    private DetachClickListener(DialogInterface.OnClickListener delegate) {
        this.mDelegate = delegate;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mDelegate != null) {
            mDelegate.onClick(dialog, which);
        }
    }

    public void clearOnDetach(Dialog dialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            dialog.getWindow()
                    .getDecorView()
                    .getViewTreeObserver()
                    .addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
                        @Override
                        public void onWindowAttached() {

                        }

                        @Override
                        public void onWindowDetached() {
                            mDelegate = null;
                        }
                    });
        }
    }

}
