package com.patris.duong.materiel_mobile_duong_patris.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.patris.duong.materiel_mobile_duong_patris.activities.DetailsActivity;
import com.patris.duong.materiel_mobile_duong_patris.models.PhotoAdapter;
import com.patris.duong.materiel_mobile_duong_patris.models.Photos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ListPhotoFragment extends ListFragment {

    public static final String PHOTO_ID = "photoid";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Photos> photos = new ArrayList<>();
        Photos photo = null;
        try {
            photo = new Photos("potittatou.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        photos.add(photo);
        ArrayAdapter<Photos> adapter = new PhotoAdapter(getActivity(), photos);
        setListAdapter(adapter);
    }

    public void onListItemClick (ListView l, View v, int position, long id){
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(PHOTO_ID, position);
        startActivity(intent);
    }

}
