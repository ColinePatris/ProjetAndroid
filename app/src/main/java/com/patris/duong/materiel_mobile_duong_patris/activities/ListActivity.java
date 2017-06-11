package com.patris.duong.materiel_mobile_duong_patris.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.patris.duong.materiel_mobile_duong_patris.R;
import com.patris.duong.materiel_mobile_duong_patris.models.RefreshTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListActivity extends AppCompatActivity {

    private static final int PHOTO_OK = 1;
    private static final String TAG="here";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.list_photos_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID){
            case R.id.action_take_photo :
                dispatchTakePictureIntent();
                break;
            case R.id.action_refresh :
                RefreshTask task = new RefreshTask(this);
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dispatchTakePictureIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,PHOTO_OK);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PHOTO_OK){
            if (resultCode==RESULT_OK){
                Bitmap bitmap = data.getParcelableExtra("data");
                ImageView imageView = (ImageView) findViewById(R.id.photo_imageView);
                imageView.setImageBitmap(bitmap);
                TextView textView = (TextView) findViewById(R.id.photo_textView);
                textView.setText("Test Réussi");
                //FileOutputStream output = getActivity().openFileOutput("picture_" + client.getId(), Context.MODE_PRIVATE);
                //bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            }
        }
    }
}
