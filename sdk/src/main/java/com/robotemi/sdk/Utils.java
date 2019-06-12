package com.robotemi.sdk;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;

final class Utils {

    private Utils() {
        // private constructor to prevent instantiation
    }

    static byte[] convertBitmapToByteArray(@NonNull final Bitmap bitmap) {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
