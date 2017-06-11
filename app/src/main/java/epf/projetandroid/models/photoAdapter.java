package epf.projetandroid.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import epf.projetandroid.R;

public class photoAdapter extends ArrayAdapter<photo> /*implements SharedPreferences.OnSharedPreferenceChangeListener*/{
    private static final String TAG = "";
    //private final SharedPreferences preferences;

    public photoAdapter(Context context, List<photo> objects) {
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