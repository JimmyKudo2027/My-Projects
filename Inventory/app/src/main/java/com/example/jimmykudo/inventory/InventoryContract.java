package com.example.jimmykudo.inventory;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Jimmy Kudo.
 */

public final class InventoryContract {

    public static final String CONTENT_AUTHORITY  = "com.example.jimmykudo.inventory";
    public static final Uri BASE_CONTENT_URI  = Uri.parse("content://"+CONTENT_AUTHORITY) ;
    public static final String PATH_PRODUCTS = "products";

    public static abstract class  InventoryEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_PRODUCTS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;


        public static final String TABLE_NAME = "products";
        public static final String _ID = BaseColumns._ID;
        public static final String PRODUCT_NAME = "name";
        public static final String PRODUCT_PRICE = "price";
        public static final String PRODUCT_QUANTITY = "quantity";
        public static final String PRODUCT_IMAGE = "image";


    }
}
