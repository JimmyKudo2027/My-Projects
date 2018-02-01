package com.example.jimmykudo.inventory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jimmy Kudo.
 */

public class ProductDbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DATA_BASE_NAME= "inventory";
    private static final String DATA_BASE_CREATION_STATEMENT=
            "CREATE TABLE "+InventoryContract.InventoryEntry.TABLE_NAME + " (" +
                    InventoryContract.InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    InventoryContract.InventoryEntry.PRODUCT_NAME + " TEXT NOT NULL," +
                    InventoryContract.InventoryEntry.PRODUCT_PRICE + " INTEGER NOT NULL," +
                    InventoryContract.InventoryEntry.PRODUCT_QUANTITY + " INTEGER NOT NULL DEFAULT 0," +
                    InventoryContract.InventoryEntry.PRODUCT_IMAGE + " TEXT NOT NULL)";
    private static final String DATA_BASE_DROP_STATEMENT= "DROP TABLE IF EXISTS "+InventoryContract.InventoryEntry.TABLE_NAME;

    public ProductDbHelper(Context context) {
        super(context, DATA_BASE_NAME, null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATA_BASE_CREATION_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATA_BASE_DROP_STATEMENT);
        onCreate(db);
    }
}
