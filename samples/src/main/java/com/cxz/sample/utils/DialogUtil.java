package com.cxz.sample.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * 对话框辅助类,需要自己调用show方法
 */
public class DialogUtil {

    /**
     * 获取一个Dialog
     *
     * @param context
     * @return
     */
    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    /**
     * 获取一个耗时的对话框 ProgressDialog
     *
     * @param context
     * @param message
     * @return
     */
    public static ProgressDialog getWaitDialog(Context context, String message) {
        ProgressDialog waitDialog = new ProgressDialog(context);
        if (!TextUtils.isEmpty(message)) {
            waitDialog.setMessage(message);
        }
        return waitDialog;
    }

    /**
     * 获取一个信息对话框,注意需要自己手动调用show方法
     *
     * @param context
     * @param message
     * @param onClickListener
     * @return
     */
    public static AlertDialog.Builder getMessageDialog(Context context, String message,
                                                       DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onClickListener);
        return builder;
    }

    /**
     * 获取一个信息对话框,注意需要自己手动调用show方法
     *
     * @param context
     * @param message
     * @return
     */
    public static AlertDialog.Builder getMessageDialog(Context context, String message) {
        return getMessageDialog(context, message, null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message,
                                                       DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message,
                                                       DialogInterface.OnClickListener onOKClickListener, DialogInterface.OnClickListener onCancleClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onOKClickListener);
        builder.setNegativeButton("取消", onCancleClickListener);
        return builder;
    }

    public static AlertDialog.Builder getSelectDialog(Context context, String title, String[] arrays,
                                                      DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setItems(arrays, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setNegativeButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getSelectDialog(Context context, String[] arrays,
                                                      DialogInterface.OnClickListener onClickListener) {
        return getSelectDialog(context, "", arrays, onClickListener);
    }

    /**
     * 获取一个单选的对话框
     *
     * @param context
     * @param title
     * @param arrays
     * @param selectIndex
     * @param onClickListener
     * @param onOKClickListener     点击确定的回调接口
     * @param onCancleClickListener 点击取消的回调接口
     * @return
     */
    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String title, String[] arrays,
                                                            int selectIndex, DialogInterface.OnClickListener onClickListener,
                                                            DialogInterface.OnClickListener onOKClickListener, DialogInterface.OnClickListener onCancleClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setPositiveButton("确定", onOKClickListener);
        builder.setNegativeButton("取消", onCancleClickListener);
        return builder;
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String title, String[] arrays,
                                                            int selectIndex, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setPositiveButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String title, String[] arrays,
                                                            int selectIndex, DialogInterface.OnClickListener onClickListener,
                                                            DialogInterface.OnClickListener onOKClickListener) {
        return getSingleChoiceDialog(context, title, arrays, selectIndex, onClickListener, onOKClickListener, null);
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String[] arrays, int selectIndex,
                                                            DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onOKClickListener,
                                                            DialogInterface.OnClickListener onCancleClickListener) {
        return getSingleChoiceDialog(context, "", arrays, selectIndex, onClickListener, onOKClickListener,
                onCancleClickListener);
    }

    /**
     * 获取一个多选的对话框
     *
     * @param context
     * @param title
     * @param arrays
     * @param checkedItems
     * @param onMultiChoiceClickListener
     * @param onOKClickListener          点击确定的回调接口
     * @param onCancleListener           点击取消的回调接口
     * @return
     */
    public static AlertDialog.Builder getMultiChoiceDialog(Context context, String title, String[] arrays,
                                                           boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener,
                                                           DialogInterface.OnClickListener onOKClickListener, DialogInterface.OnClickListener onCancleListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMultiChoiceItems(arrays, checkedItems, onMultiChoiceClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setPositiveButton("确定", onOKClickListener);
        builder.setNegativeButton("取消", onCancleListener);
        return builder;
    }

}
