package epf.projetandroid.models;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Coline on 11/06/2017.
 */

public class FirebaseHelper {
	
	DatabaseReference db;
	Boolean saved = null;
	ArrayList <String> photos = new ArrayList <> ();
	
	public FirebaseHelper (DatabaseReference db) {
		this.db = db;
	}
	
	public Boolean save (Photo p) {
		if (p == null) {
			saved = false;
		} else {
			try {
				db.child ("Photo").push ().setValue (p);
				saved = true;
				
			} catch (DatabaseException e) {
				e.printStackTrace ();
				saved = false;
			}
		}
		return saved;
	}
	
	public ArrayList <String> retrieve () {
		db.addChildEventListener (new ChildEventListener () {
			@Override
			public void onChildAdded (DataSnapshot dataSnapshot, String s) {
				fetchData (dataSnapshot);
			}
			
			@Override
			public void onChildChanged (DataSnapshot dataSnapshot, String s) {
				fetchData (dataSnapshot);
				
			}
			
			@Override
			public void onChildRemoved (DataSnapshot dataSnapshot) {
				
			}
			
			@Override
			public void onChildMoved (DataSnapshot dataSnapshot, String s) {
				
			}
			
			@Override
			public void onCancelled (DatabaseError databaseError) {
				
			}
		});
		
		return photos;
	}
	
	private void fetchData (DataSnapshot dataSnapshot) {
		photos.clear ();
		
		for (DataSnapshot ds : dataSnapshot.getChildren ()) {
			String name = ds.getValue (Photo.class).getName ();
			photos.add (name);
		}
	}
}