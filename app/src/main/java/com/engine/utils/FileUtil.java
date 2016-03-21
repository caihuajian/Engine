package com.engine.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * 文件处理
 *
 * @author lanyj
 */
public class FileUtil {

    private static final String TAG = "FileUtil";


    private FileUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 根据文件路径 递归创建文件
     *
     * @param file
     */
    public static void createDipPath(String file) {
        String parentFile = file.substring(0, file.lastIndexOf("/"));
        File file1 = new File(file);
        File parent = new File(parentFile);
        if (!file1.exists()) {
            parent.mkdirs();
            try {
                file1.createNewFile();
                AppLog.i(TAG, "Create new file :" + file);
            } catch (IOException e) {
                AppLog.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public static boolean deleteFile(String path) {
        boolean bl;
        File file = new File(path);
        if (file.exists()) {
            bl = file.delete();
        } else {
            bl = false;
        }
        return bl;
    }

    /**
     * 将bitmap保存到本地
     *
     * @param bitmap
     * @param imagePath
     */
    @SuppressLint("NewApi")
    public static void saveBitmap(Bitmap bitmap, String imagePath, int s) {
        File file = new File(imagePath);
        createDipPath(imagePath);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (imagePath.toLowerCase().endsWith(".png")) {
            bitmap.compress(Bitmap.CompressFormat.PNG, s, fOut);
        } else if (imagePath.toLowerCase().endsWith(".jpg")) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, s, fOut);
        } else {
            bitmap.compress(Bitmap.CompressFormat.WEBP, s, fOut);
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件
     *
     * @param sourcePath
     * @param toPath
     */
    public static void copyFile(String sourcePath, String toPath) {
        File sourceFile = new File(sourcePath);
        File targetFile = new File(toPath);
        createDipPath(toPath);
        try {
            BufferedInputStream inBuff = null;
            BufferedOutputStream outBuff = null;
            try {
                // 新建文件输入流并对它进行缓冲
                inBuff = new BufferedInputStream(
                        new FileInputStream(sourceFile));

                // 新建文件输出流并对它进行缓冲
                outBuff = new BufferedOutputStream(new FileOutputStream(
                        targetFile));

                // 缓冲数组
                byte[] b = new byte[1024 * 5];
                int len;
                while ((len = inBuff.read(b)) != -1) {
                    outBuff.write(b, 0, len);
                }
                // 刷新此缓冲的输出流
                outBuff.flush();
            } finally {
                // 关闭流
                if (inBuff != null)
                    inBuff.close();
                if (outBuff != null)
                    outBuff.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 复制文件
     *
     * @param sourceFile
     * @param targetFile
     */
    public static void copyFile(File sourceFile, File targetFile) {

        try {
            BufferedInputStream inBuff = null;
            BufferedOutputStream outBuff = null;
            try {
                // 新建文件输入流并对它进行缓冲
                inBuff = new BufferedInputStream(
                        new FileInputStream(sourceFile));

                // 新建文件输出流并对它进行缓冲
                outBuff = new BufferedOutputStream(new FileOutputStream(
                        targetFile));

                // 缓冲数组
                byte[] b = new byte[1024 * 5];
                int len;
                while ((len = inBuff.read(b)) != -1) {
                    outBuff.write(b, 0, len);
                }
                // 刷新此缓冲的输出流
                outBuff.flush();
            } finally {
                // 关闭流
                if (inBuff != null)
                    inBuff.close();
                if (outBuff != null)
                    outBuff.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取存储目录的根目录
     *
     * @return /sdcard/ or /data/data/
     */
    public static String getRootFilePath() {
        if (LPhone.hasSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";// filePath:/sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath: /data/data/
        }
    }

    /**
     * 获取私有缓存路径
     *
     * @param context 上下文
     * @return 缓存路径
     */
    public static String getCacheFilePath(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * 获取私有文件存储目录
     *
     * @param context 上下文
     * @return 存储目录
     */
    public static String getPrivateFilePath(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 获取文件目录下文件的大小返回以兆M为单位
     */
    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                BigDecimal b = new BigDecimal(size);
                size = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            return 0;
        }
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void deleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
            file.renameTo(to);
            if (to.isFile()) {
                to.delete();
                return;
            }
            if (to.isDirectory()) {
                File[] childFile = to.listFiles();
                if (childFile == null || childFile.length == 0) {
                    to.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteFile(f);
                }
                to.delete();
            }
        }
    }


    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
