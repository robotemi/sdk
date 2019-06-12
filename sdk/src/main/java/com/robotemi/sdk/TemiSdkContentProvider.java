package com.robotemi.sdk;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public class TemiSdkContentProvider extends ContentProvider {

    @SuppressLint("StaticFieldLeak")
    @Nullable
    static Context context;

    @Override
    public boolean onCreate() {
        context = getContext();
        if (context == null) {
            throw new NullPointerException("context=null");
        }
        new TemiSdkServiceConnection().startConnection(context);
        return true;
    }

    @Override
    public int delete(@SuppressWarnings("NullableProblems") Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(@SuppressWarnings("NullableProblems") Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@SuppressWarnings("NullableProblems") Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public Cursor query(@SuppressWarnings("NullableProblems") Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public int update(@SuppressWarnings("NullableProblems") Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
