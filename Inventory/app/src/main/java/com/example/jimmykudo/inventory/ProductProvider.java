package com.example.jimmykudo.inventory;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Jimmy Kudo.
 */

public class ProductProvider extends ContentProvider {

    private static final int PRODUCTS = 1;
    private static final int PRODUCT_ID = 2;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private ProductDbHelper dbHelper;

    static {
        URI_MATCHER.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_PRODUCTS, PRODUCTS);
        URI_MATCHER.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_PRODUCTS + "/#", PRODUCT_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new ProductDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor;

        int match = URI_MATCHER.match(uri);
        switch (match) {
            case PRODUCTS:
                cursor = database.query(InventoryContract.InventoryEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);
                break;
            case PRODUCT_ID:
                selection = InventoryContract.InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(InventoryContract.InventoryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        String type;
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case PRODUCTS:
                type = InventoryContract.InventoryEntry.CONTENT_LIST_TYPE;
                break;
            case PRODUCT_ID:
                type = InventoryContract.InventoryEntry.CONTENT_ITEM_TYPE;
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return type;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int match = URI_MATCHER.match(uri);
        switch (match) {
            case PRODUCTS:
                return insertProduct(uri,values);
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
    }

    private Uri insertProduct(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(InventoryContract.InventoryEntry.TABLE_NAME,null,values);
        if (id == -1) {
            Log.e("Jimmy", "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int deletedRows;
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case PRODUCTS:
                 deletedRows = database.delete(InventoryContract.InventoryEntry.TABLE_NAME,selection,selectionArgs);
                if (deletedRows != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return deletedRows;
            case PRODUCT_ID:
                selection = InventoryContract.InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                deletedRows = database.delete(InventoryContract.InventoryEntry.TABLE_NAME,selection,selectionArgs);
                if (deletedRows != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return deletedRows;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int match = URI_MATCHER.match(uri);
        switch (match) {
            case PRODUCTS:
                return updateProduct(uri, values, selection, selectionArgs);
            case PRODUCT_ID:
                selection = InventoryContract.InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateProduct(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.update(InventoryContract.InventoryEntry.TABLE_NAME,values,selection,selectionArgs);
        if(rowsAffected != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsAffected;
    }
}
