package epf.projetandroid.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import epf.projetandroid.R;
import epf.projetandroid.models.FirebaseHelper;
import epf.projetandroid.models.Photo;

public class ListActivity extends AppCompatActivity {
	
	private static final int PHOTO_OK = 1;
	private static final String TAG = "here";
	
	private ArrayList <String> names;
	private ArrayList <Bitmap> bitmaps;
	private ArrayList <Photo> photos;
	
	private StorageReference mStorageRef;
	private ImageView imageView;
	private TextView textView;
	private Calendar calendar;
	private String imageName;
	
	DatabaseReference mDatabaseRef;
	FirebaseHelper helper;
	
	File localFile = null;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_list);
		
		mStorageRef = FirebaseStorage.getInstance ().getReference ();
		mDatabaseRef = FirebaseDatabase.getInstance ().getReference ();
		helper = new FirebaseHelper (mDatabaseRef);
		
		imageView = (ImageView) findViewById (R.id.photo_imageView);
		textView = (TextView) findViewById (R.id.photo_textView);
		
		calendar = Calendar.getInstance ();
		names = new ArrayList <> ();
		bitmaps = new ArrayList <> ();
		photos = new ArrayList <> ();
		
		mDatabaseRef.addValueEventListener (new ValueEventListener () {
			@Override
			public void onDataChange (DataSnapshot dataSnapshot) {
				// This method is called once with the initial value and again
				// whenever data at this location is updated.
				Photo value = dataSnapshot.getValue (Photo.class);
				refresh ();
			}
			
			@Override
			public void onCancelled (DatabaseError error) {
				// Failed to read value
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		getMenuInflater ().inflate (R.menu.list_menu, menu);
		return super.onCreateOptionsMenu (menu);
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		int itemID = item.getItemId ();
		switch (itemID) {
			case R.id.action_take_photo:
				dispatchTakePictureIntent ();
				break;
			case R.id.action_refresh:
				refresh ();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected (item);
	}
	
	private void refresh () {
		final ImageView imageView = (ImageView) findViewById (R.id.photo_imageView);
		final TextView textView = (TextView) findViewById (R.id.photo_textView);
		
		names = helper.retrieve ();
		for (int i = 0 ; i < names.size () ; i++) {
			try {
				localFile = File.createTempFile ("images", "png");
			} catch (IOException e) {
				e.printStackTrace ();
			}
			
			StorageReference tempFile = mStorageRef.child ("images/" + names.get (i));
			tempFile.getFile (localFile)
					.addOnSuccessListener (new OnSuccessListener <FileDownloadTask.TaskSnapshot> () {
						@Override
						public void onSuccess (FileDownloadTask.TaskSnapshot taskSnapshot) {
							Bitmap image = BitmapFactory.decodeFile (String.valueOf (localFile));
							imageView.setImageBitmap (image);
						}
					}).addOnFailureListener (new OnFailureListener () {
				@Override
				public void onFailure (@NonNull Exception exception) {
					runOnUiThread (new Runnable () {
						@Override
						public void run () {
							Toast.makeText (getBaseContext (), getString (R.string.connection_fail), Toast.LENGTH_SHORT).show ();
						}
					});
				}
			});
			textView.setText (names.get (i));
			
		}
		
		/*StorageReference tempFile = mStorageRef.child ("images/image.png");
		tempFile.getFile (localFile)
				.addOnSuccessListener (new OnSuccessListener <FileDownloadTask.TaskSnapshot> () {
					@Override
					public void onSuccess (FileDownloadTask.TaskSnapshot taskSnapshot) {
						Bitmap image = BitmapFactory.decodeFile (String.valueOf (localFile));
						imageView.setImageBitmap (image);
					}
				}).addOnFailureListener (new OnFailureListener () {
			@Override
			public void onFailure (@NonNull Exception exception) {
				runOnUiThread (new Runnable () {
					@Override
					public void run () {
						Toast.makeText (getBaseContext (), getString (R.string.connection_fail), Toast.LENGTH_SHORT).show ();
					}
				});
			}
		});*/
	}
	
	private void dispatchTakePictureIntent () {
		Intent cameraIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult (cameraIntent, PHOTO_OK);
	}
	
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		super.onActivityResult (requestCode, resultCode, data);
		if (requestCode == PHOTO_OK) {
			if (resultCode == RESULT_OK) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("dd-MM-yyyy_HH-mm-ss-SSS");
				String date = simpleDateFormat.format (calendar.getTime ());
				imageName = "Image_" + date + "png";
				
				final Bitmap bitmap = data.getParcelableExtra ("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream ();
				bitmap.compress (Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray ();
				final StorageReference bitmapFile = mStorageRef.child ("images/" + imageName);
				bitmapFile.putBytes (byteArray)
						.addOnSuccessListener (new OnSuccessListener <UploadTask.TaskSnapshot> () {
							@Override
							public void onSuccess (UploadTask.TaskSnapshot taskSnapshot) {
								runOnUiThread (new Runnable () {
									@Override
									public void run () {
										Toast.makeText (getBaseContext (), getString (R.string.upload_success), Toast.LENGTH_SHORT).show ();
										Photo p = new Photo ();
										p.setName (imageName);
										p.setBitmap (bitmap);
									}
								});
							}
						})
						.addOnFailureListener (new OnFailureListener () {
							@Override
							public void onFailure (@NonNull Exception exception) {
								runOnUiThread (new Runnable () {
									@Override
									public void run () {
										Toast.makeText (getBaseContext (), R.string.connection_fail, Toast.LENGTH_SHORT).show ();
									}
								});
							}
						});
			}
		}
	}
}
