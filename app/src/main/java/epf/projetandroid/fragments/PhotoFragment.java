package epf.projetandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import epf.projetandroid.activities.DetailsActivity;
import epf.projetandroid.models.Photo;
import epf.projetandroid.models.PhotoAdapter;

public class PhotoFragment extends ListFragment {
	
	public static final String PHOTO_ID = "photoid";
	private ArrayAdapter <Photo> adapter;
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		
		ArrayList <Photo> photos = new ArrayList <> ();
		Photo photo = null;
		photos.add (photo);
		setAdapter (photos);
		setListAdapter (adapter);
	}
	
	public void onListItemClick (ListView l, View v, int position, long id) {
		Intent intent = new Intent (getActivity (), DetailsActivity.class);
		intent.putExtra (PHOTO_ID, position);
		startActivity (intent);
	}
	
	public void setAdapter (ArrayList<Photo> al){
		adapter = new PhotoAdapter (getActivity (), al);
	}
}
