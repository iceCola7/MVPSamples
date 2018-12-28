package com.cxz.baselibs.utils;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    private static int BUFFERD = 1024;

    private FileUtil() {
    }

    /*
     * <!-- 在SDCard中创建与删除文件权限 --> <uses-permission
     * android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/> <!--
     * 往SDCard写入数据权限 --> <uses-permission
     * android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     */

    // =================get SDCard information===================
    public static boolean isSdcardAvailable() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static long getSDAllSizeKB() {
        // get path of sdcard
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // get single block size(Byte)
        long blockSize = sf.getBlockSize();
        // 获取所有数据块数
        long allBlocks = sf.getBlockCount();
        // 返回SD卡大小
        return (allBlocks * blockSize) / 1024; // KB
    }

    /**
     * free size for normal application
     *
     * @return
     */
    public static long getSDAvalibleSizeKB() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize();
        long avaliableSize = sf.getAvailableBlocks();
        return (avaliableSize * blockSize) / 1024;// KB
    }

    // =====================File Operation==========================
    public static boolean isFileExist(String director) {
        File file = new File(Environment.getExternalStorageDirectory()
                + File.separator + director);
        return file.exists();
    }

    /**
     * create multiple director
     *
     * @param director
     * @return
     */
    public static boolean createFile(String director) {
        if (isFileExist(director)) {
            return true;
        } else {
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + director);
            if (!file.mkdirs()) {
                return false;
            }
            return true;
        }
    }

    public static File writeToSDCardFile(String directory, String fileName,
                                         String content, boolean isAppend) {
        return writeToSDCardFile(directory, fileName, content, "", isAppend);
    }

    /**
     * @param directory (you don't need to begin with
     *                  Environment.getExternalStorageDirectory()+File.separator)
     * @param fileName
     * @param content
     * @param encoding  (UTF-8...)
     * @param isAppend  : Context.MODE_APPEND
     * @return
     */
    public static File writeToSDCardFile(String directory, String fileName,
                                         String content, String encoding, boolean isAppend) {
        // mobile SD card path +path
        File file = null;
        OutputStream os = null;
        try {
            if (!createFile(directory)) {
                return file;
            }
            file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + directory + File.separator + fileName);
            os = new FileOutputStream(file, isAppend);
            if (encoding.equals("")) {
                os.write(content.getBytes());
            } else {
                os.write(content.getBytes(encoding));
            }
            os.flush();
        } catch (IOException e) {
            Log.e("FileUtil", "writeToSDCardFile:" + e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * write data from inputstream to SDCard
     */
    public File writeToSDCardFromInput(String directory, String fileName,
                                       InputStream input) {
        File file = null;
        OutputStream os = null;
        try {
            if (createFile(directory)) {
                return file;
            }
            file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + directory + fileName);
            os = new FileOutputStream(file);
            byte[] data = new byte[BUFFERD];
            int length = -1;
            while ((length = input.read(data)) != -1) {
                os.write(data, 0, length);
            }
            // clear cache
            os.flush();
        } catch (Exception e) {
            Log.e("FileUtil", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * this url point to image(jpg)
     *
     * @param url
     * @return image name
     */
    public static String getUrlLastString(String url) {
        String[] str = url.split("/");
        int size = str.length;
        return str[size - 1];
    }

    /**
     * Create a file if it doesn't exist, otherwise do nothing.
     *
     * @param filePath The path of file.
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * Create a file if it doesn't exist, otherwise do nothing.
     *
     * @param file The file.
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Create a directory if it doesn't exist, otherwise do nothing.
     *
     * @param file The file.
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * Return the file by path.
     *
     * @param filePath The path of file.
     * @return the file
     */
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
