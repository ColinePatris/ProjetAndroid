package epf.projetandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import epf.projetandroid.activities.DetailsActivity;
import epf.projetandroid.models.photo;
import epf.projetandroid.models.photoAdapter;

public class PhotoFragment extends ListFragment {

    public static final String PHOTO_ID = "photoid";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<photo> photos = new ArrayList<>();
        photo photo = null;
        photos.add(photo);
        ArrayAdapter<photo> adapter = new photoAdapter(getActivity(), photos);
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(PHOTO_ID, position);
        startActivity(intent);
    }
}
