package com.from.civilusecar.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {

    public static String getSaveFile(Context context, String fileName) {
//        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "liaocar");
        File root = new File(context.getFilesDir(),"liaocar");
        if (!root.exists()) {
            root.mkdirs();
        }
        File file = new File(root.getAbsoluteFile(), fileName);
        return file.getAbsolutePath();
    }
    public static String getSaveFile(Context context, String fileName,String typemane) {
//        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "liaocar");
        File root = new File(Environment.getExternalStorageDirectory(),"liaocar");
        if (!root.exists()) {
            root.mkdirs();
        }
        File file = new File(root.getAbsoluteFile(), fileName);
        return file.getAbsolutePath();
    }

    // 递归方式 计算文件的大小
    public static long getCacheSize(Context context) {
        File file = new File(context.getFilesDir(), "liaocar");
        return getTotalSizeOfFilesInDir(file);
    }

    // 递归方式 计算文件的大小
    public static long getTotalSizeOfFilesInDir(final File file) {
        if (!file.exists()) return 0;
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }

    public static void deleteCacheFile(Context context) {
        File file = new File(context.getFilesDir(), "liaocar");
        deleteFile(file);
    }

    public static boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }

        return dirFile.delete();
    }

}

