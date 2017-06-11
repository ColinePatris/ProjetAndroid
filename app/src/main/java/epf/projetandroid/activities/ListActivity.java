package epf.projetandroid.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import epf.projetandroid.R;

public class ListActivity extends AppCompatActivity {

    private static final int PHOTO_OK = 1;
    private StorageReference mStorageRef;
    private ImageView imageView;
    private TextView textView;
    File localFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        imageView = (ImageView) findViewById(R.id.photo_imageView);
        textView = (TextView) findViewById(R.id.photo_textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            case R.id.action_take_photo:
                dispatchTakePictureIntent();
                break;
            case R.id.action_refresh:
                refresh();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {

        try {
            localFile = File.createTempFile("images", "png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        StorageReference tempFile = mStorageRef.child("images/image.png");
        tempFile.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap BckGrnd = BitmapFactory.decodeFile(String.valueOf(localFile));
                        imageView.setImageBitmap(BckGrnd);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Échec de connexion", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PHOTO_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_OK) {
            if (resultCode == RESULT_OK) {
                final Bitmap bitmap = data.getParcelableExtra("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                StorageReference bitmapFile = mStorageRef.child("images/image.png");
                bitmapFile.putBytes(byteArray)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getBaseContext(), "Téléversement réussi", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getBaseContext(), "Échec de connexion", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
            }
        }
    }
}
