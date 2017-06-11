package com.patris.duong.materiel_mobile_duong_patris.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.patris.duong.materiel_mobile_duong_patris.R;

import java.util.List;

public class PhotoAdapter extends ArrayAdapter<Photos> /*implements SharedPreferences.OnSharedPreferenceChangeListener*/{
    private static final String TAG = "";
    //private final SharedPreferences preferences;

    public PhotoAdapter(Context context, List<Photos> objects) {
        super(context, 0, objects);
        /*preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.registerOnSharedPreferenceChangeListener(this);*/
    }

    /*@Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
        notifyDataSetChanged();
    }*/

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView" + convertView);

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.photo_adapter, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.photo_imageView);
        TextView textView = (TextView) convertView.findViewById(R.id.photo_textView);
        //Photos photo = getItem(position);

//        imageView.setImageResource(R.drawable.potittatou);
//        textView.setText("Potit tatou");

        return convertView;
    }
}