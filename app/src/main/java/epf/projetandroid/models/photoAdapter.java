package epf.projetandroid.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import epf.projetandroid.R;

public class photoAdapter extends ArrayAdapter<photo>{
    private static final String TAG = "";

    public photoAdapter(Context context, List<photo> objects) {
        super(context, 0, objects);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView" + convertView);

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.photo_adapter, parent, false);
        }
        return convertView;
    }
}