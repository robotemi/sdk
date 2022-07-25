package com.robotemi.sdk.activitystream;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.robotemi.sdk.MediaObject;

public final class ActivityStreamUtils {

    private static final String TAG = "ActivityStreamUtils";

    private static final String COMMON_DIRECTORY_NAME = "Teamy";

    private static final String FILENAME_PREFIX = "TEAMY_";

    private static final int NOT_FOUND = -1;

    private ActivityStreamUtils() {
        // private constructor to prevent instantiation
    }

    public static void handleActivityStreamObject(@NonNull final ActivityStreamObject activityStreamObject) {
        final MediaObject mediaObject = activityStreamObject.getMediaObject();
        if (mediaObject != null) {
            final String absolutePath = copyFileToExternalStorageDirectory(mediaObject.getFile());
            mediaObject.setMediaUri(absolutePath);
        }
        for (ActivityStreamListItem item : activityStreamObject.getItems()) {
            if (item.isFileProvided() && item.getFile() != null) {
                final String absolutePath = copyFileToExternalStorageDirectory(item.getFile());
                item.setMediaUri(absolutePath);
            }
        }
    }

    @Nullable
    private static String copyFileToExternalStorageDirectory(@NonNull final File srcFile) {
        Log.d(TAG, "srcFile=" + srcFile);
        final String extension = getExtension(srcFile.getName());
        if (extension == null) {
            Log.e(TAG, "Can't extract extension from src file: " + srcFile);
            Log.e(TAG, "Copy file to external storage directory... ERROR");
            return null;
        }
        final File destFile = createFileInExternalStorageDirectory(extension);
        Log.d(TAG, "destFile=" + destFile);
        try {
            copyFile(srcFile, destFile);
        } catch (IOException e) {
            Log.e(TAG, "Copy file to external storage directory... ERROR", e);
            if (!destFile.delete()) {
                Log.e(TAG, "File not deleted: " + destFile);
            }
            return null;
        } finally {
            deleteFile(srcFile);
        }
        Log.d(TAG, "Copy file to external storage directory... OK");
        return destFile.getAbsolutePath();
    }

    public static void doAfterActivityPublished(ActivityStreamObject activityStreamObject) {
        final MediaObject mediaObject = activityStreamObject.getMediaObject();
        if (mediaObject != null) {
            final File file = new File(mediaObject.getLocalPath());
            deleteFile(file);
        }
        for (ActivityStreamListItem item : activityStreamObject.getItems()) {
            if (item.isFileProvided()) {
                final File file = new File(item.getLocalPath());
                deleteFile(file);
            }
        }
    }

    @NonNull
    private static File getExternalStorageDirectory() {
        final File externalDirectory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), COMMON_DIRECTORY_NAME);
        if (!externalDirectory.mkdirs()) {
            Log.e(TAG, "Directory not created...");
        }
        return externalDirectory;
    }

    @NonNull
    private static File createFileInExternalStorageDirectory(@NonNull final String extension) {
        return new File(getExternalStorageDirectory(),
                FILENAME_PREFIX + UUID.randomUUID() + "." + extension);
    }

    @SuppressWarnings("IOStreamConstructor")
    @WorkerThread
    private static void copyFile(@NonNull final File srcFile, @NonNull final File dstFile)
            throws IOException {

        try (InputStream inputStream = new FileInputStream(srcFile);
             OutputStream outputStream = new FileOutputStream(dstFile)) {
            final byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
        }
    }

    private static void deleteFile(@NonNull final File file) {
        if (!file.delete()) {
            Log.e(TAG, "File not deleted: " + file);
        }
    }

    @Nullable
    private static String getExtension(final String filename) {
        if (filename == null) {
            return null;
        }
        final int index = filename.lastIndexOf(".");
        if (index == NOT_FOUND) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }
}
