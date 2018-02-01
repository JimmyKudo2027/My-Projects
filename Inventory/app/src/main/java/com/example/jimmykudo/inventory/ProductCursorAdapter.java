package com.example.jimmykudo.inventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jimmy Kudo.
 */

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custom_product_item, parent,false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.productName);
        final String jName = cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.PRODUCT_NAME));
        name.setText(jName);
        TextView quantity = (TextView) view.findViewById(R.id.productQuantity);
        final int jQuantity = cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.PRODUCT_QUANTITY));
        quantity.setText(""+jQuantity);
        TextView price = (TextView) view.findViewById(R.id.productPrice);
        final int jPrice = cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.PRODUCT_PRICE));
        price.setText(""+jPrice);

        final String imgData = cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.PRODUCT_IMAGE));
        ImageView proImage = (ImageView) view.findViewById(R.id.productImage);
        proImage.setImageBitmap(StringToBitMap(imgData));

        Button saleBtn = (Button) view.findViewById(R.id.saleButton);
        saleBtn.setTag(cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry._ID)));
        saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v!=null){
                    String _ID = v.getTag().toString();
                    ContentValues values = new ContentValues();
                    values.put(InventoryContract.InventoryEntry.PRODUCT_NAME,jName);
                    if (jQuantity >= 1){
                        values.put(InventoryContract.InventoryEntry.PRODUCT_QUANTITY,jQuantity-1);
                    }else {
                        values.put(InventoryContract.InventoryEntry.PRODUCT_QUANTITY,0);
                    }
                    values.put(InventoryContract.InventoryEntry.PRODUCT_PRICE,jPrice);
                    Uri jUri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI,Integer.parseInt(_ID));
                    int rowsAffected = context.getContentResolver().update(jUri,values,null,null);
                    if (rowsAffected == 0 || jQuantity == 0) {
                        if (rowsAffected==0){
                            Toast.makeText(context,"Update Failed !", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,"Negative Quantity Denied !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
