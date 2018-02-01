package com.example.jimmykudo.inventory;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Jimmy Kudo.
 */

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EXISTING_PET_LOADER = 1;
    private static final int GALLERY = 0;
    private EditText mNameEditText;
    private EditText mQuantityEditText;
    private EditText mPriceEditText;
    private TextView mTitle;
    private Button mSaveButton;
    private Button mDeleteButton;
    private Button mIncButton;
    private Button mDecButton;
    private Button mSelectImageButton;
    private Button mOrderButton;
    private ImageView mImage;
    private String mImageData;
    private Uri mCurrentUri;
    private boolean mDataChange = false;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mDataChange = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mTitle = (TextView) findViewById(R.id.titleText);
        mNameEditText = (EditText) findViewById(R.id.proEditName);
        mNameEditText.setOnTouchListener(mTouchListener);
        mQuantityEditText = (EditText) findViewById(R.id.proEditQuantity);
        mQuantityEditText.setOnTouchListener(mTouchListener);
        mPriceEditText = (EditText) findViewById(R.id.proEditPrice);
        mPriceEditText.setOnTouchListener(mTouchListener);
        mSaveButton = (Button) findViewById(R.id.saveButton);
        mDeleteButton = (Button) findViewById(R.id.deleteButton);
        mDecButton = (Button) findViewById(R.id.decrementButton);
        mDecButton.setOnTouchListener(mTouchListener);
        mIncButton = (Button) findViewById(R.id.incrementButton);
        mIncButton.setOnTouchListener(mTouchListener);
        mOrderButton = (Button) findViewById(R.id.orderButton);
        mOrderButton.setOnTouchListener(mTouchListener);
        mSelectImageButton = (Button) findViewById(R.id.selectImageButton);
        mSelectImageButton.setOnTouchListener(mTouchListener);
        mImage = (ImageView)findViewById(R.id.proImageView);
        mImageData = "";
        Intent intent = getIntent();
        mCurrentUri = intent.getData();
        if (mCurrentUri == null){
            setTitle("Add Product");
            mTitle.setText("Add New Product");
        }else{
            getLoaderManager().initLoader(EXISTING_PET_LOADER, null, this);
            setTitle("Edit Product");
            mTitle.setText("Edit Existing Product");
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
        mIncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuantityEditText.getText().toString().isEmpty()){
                    mQuantityEditText.setText("1");
                }else {
                    int quantity = Integer.parseInt(mQuantityEditText.getText().toString());
                    mQuantityEditText.setText(""+(quantity+1));
                }
            }
        });
        mDecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuantityEditText.getText().toString().isEmpty()){
                    mQuantityEditText.setText("0");
                }else {
                    int quantity = Integer.parseInt(mQuantityEditText.getText().toString());
                    if (quantity <= 0){
                        mQuantityEditText.setText("0");
                    }else {
                        mQuantityEditText.setText(""+(quantity-1));
                    }
                }
            }
        });
        mOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEditText.getText().toString().trim();
                String message = "";
                message += getString(R.string.hi) + "\n";
                message += getString(R.string.message) + "\n";
                message += getString(R.string.product_name) + " " + name + "\n";
                message += getString(R.string.thank);
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email) + " " + name);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        mSelectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Cancel"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                dialog.dismiss();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    ByteArrayOutputStream mBytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, mBytes);
                    mImageData = BitMapToString(bitmap);
                    mImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    @Override
    public void onBackPressed() {
        if (!mDataChange) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void deleteProduct() {

        if(mCurrentUri!=null){

            int rowsAffected = getContentResolver().delete(mCurrentUri,null,null);
            if (rowsAffected == 0){
                Toast.makeText(this,"Ops ... Deletion Failed !", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"Deletion Success", Toast.LENGTH_SHORT).show();
            }
            finish();
        }

    }

    private void addProduct() {
        if (mCurrentUri == null) {
            ContentValues values = new ContentValues();
            if (mNameEditText.getText().toString().isEmpty() || mImageData.isEmpty() || mPriceEditText.getText().toString().isEmpty() || mQuantityEditText.getText().toString().isEmpty()) {
                Toast.makeText(this, "Make sure that you fill all product's data !", Toast.LENGTH_LONG).show();
            } else {
                values.put(InventoryContract.InventoryEntry.PRODUCT_NAME, mNameEditText.getText().toString());
                values.put(InventoryContract.InventoryEntry.PRODUCT_PRICE, Integer.parseInt(mPriceEditText.getText().toString()));
                values.put(InventoryContract.InventoryEntry.PRODUCT_QUANTITY, Integer.parseInt(mQuantityEditText.getText().toString()));
                values.put(InventoryContract.InventoryEntry.PRODUCT_IMAGE, mImageData);
                Uri productUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);
                Toast.makeText(getApplicationContext(), "Product ID is " + ContentUris.parseId(productUri), Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            ContentValues values = new ContentValues();
            if (mNameEditText.getText().toString().isEmpty() || mPriceEditText.getText().toString().isEmpty() || mQuantityEditText.getText().toString().isEmpty()) {
                Toast.makeText(this, "Make sure that you fill all product's data !", Toast.LENGTH_LONG).show();
            } else {
                values.put(InventoryContract.InventoryEntry.PRODUCT_NAME, mNameEditText.getText().toString());
                values.put(InventoryContract.InventoryEntry.PRODUCT_PRICE, Integer.parseInt(mPriceEditText.getText().toString()));
                values.put(InventoryContract.InventoryEntry.PRODUCT_QUANTITY, Integer.parseInt(mQuantityEditText.getText().toString()));
                values.put(InventoryContract.InventoryEntry.PRODUCT_IMAGE, mImageData);
                int rowsAffected = getContentResolver().update(mCurrentUri, values,null,null);
                if (rowsAffected == 0) {
                    // If no rows were affected, then there was an error with the update.
                    Toast.makeText(this,"Ops .... Update Failed !",Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the update was successful and we can display a toast.
                    Toast.makeText(this, "Update Successful",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{
                InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.PRODUCT_NAME,
                InventoryContract.InventoryEntry.PRODUCT_QUANTITY,
                InventoryContract.InventoryEntry.PRODUCT_PRICE,
                InventoryContract.InventoryEntry.PRODUCT_IMAGE
        };
        return new CursorLoader(this,mCurrentUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(data.moveToFirst()) {
            mNameEditText.setText(data.getString(data.getColumnIndex(InventoryContract.InventoryEntry.PRODUCT_NAME)));
            mQuantityEditText.setText("" + data.getInt(data.getColumnIndex(InventoryContract.InventoryEntry.PRODUCT_QUANTITY)));
            mPriceEditText.setText("" + data.getInt(data.getColumnIndex(InventoryContract.InventoryEntry.PRODUCT_PRICE)));
            mImageData = data.getString(data.getColumnIndex(InventoryContract.InventoryEntry.PRODUCT_IMAGE));
            mImage.setImageBitmap(StringToBitMap(mImageData));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
