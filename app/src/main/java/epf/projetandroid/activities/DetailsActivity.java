package epf.projetandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import epf.projetandroid.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView displayed = (ImageView) findViewById(R.id.displayed_ImageView);

        TextView title = (TextView) findViewById(R.id.title_textView);
        TextView date = (TextView) findViewById(R.id.date_textView);
        TextView height = (TextView) findViewById(R.id.heigth_textView);
        TextView width = (TextView) findViewById(R.id.width_textView);
        TextView gps = (TextView) findViewById(R.id.gps_textView);

        //displayed.setImageResource(R.drawable.potittatou);

        title.setText(" Pôtit Tatou");
        date.setText("         22 mai 2017");
        height.setText("             100 px");
        width.setText("             100px");
        gps.setText("          Canapé");
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_delete :
                finish();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }
}
