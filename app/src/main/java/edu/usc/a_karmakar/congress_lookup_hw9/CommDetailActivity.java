package edu.usc.a_karmakar.congress_lookup_hw9;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class CommDetailActivity extends AppCompatActivity {
    private MyBillTag infoObj;
    private ViewHolder myCommHolder;
    private String currCommId;

    private class ViewHolder {
        TextView commId;
        TextView commName;
        TextView commChamber;
        TextView commPC;
        TextView commContact;
        TextView commOffice;
        ImageView commChamberImg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarComm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Committee Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String extraId = getIntent().getExtras().getString("commID");
        String extraName = getIntent().getExtras().getString("commName");
        String extraChamber = getIntent().getExtras().getString("commChamber");

        String extraOffice = getIntent().getExtras().getString("commOffice");
        extraOffice = extraOffice == null ? "N.A." : extraOffice;

        String extraPC = getIntent().getExtras().getString("commPC");
        extraPC = extraPC == null ? "N.A." : extraPC;

        String extraContact = getIntent().getExtras().getString("commContact");
        extraContact = extraContact == null ? "N.A." : extraContact;

        currCommId = extraId;

        // load favorite star or regular star
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Set<String> Keys = sharedPref.getStringSet(getString(R.string.favoriteCommittees), new HashSet<String>());
        ImageView favStar = (ImageView) findViewById(R.id.fav_button_comm);
        if (Keys.contains(currCommId)) {
            favStar.setImageResource(R.drawable.ic_star);
        }
        else {
            favStar.setImageResource(R.drawable.ic_inactive_star);
        }

        myCommHolder = new ViewHolder();

        myCommHolder.commId = (TextView) findViewById(R.id.comm_id);
        myCommHolder.commId.setText(extraId);

        myCommHolder.commName = (TextView) findViewById(R.id.comm_name);
        myCommHolder.commName.setText(extraName);

        myCommHolder.commChamber = (TextView) findViewById(R.id.comm_chamb);
        myCommHolder.commChamberImg = (ImageView) findViewById(R.id.comm_chamberImg);
        myCommHolder.commChamber.setText(extraChamber);
        if (extraChamber.equals("House")) {
            myCommHolder.commChamberImg.setImageResource(R.drawable.h);
        }
        else {
            myCommHolder.commChamberImg.setImageResource(R.drawable.ic_s);
        }

        myCommHolder.commOffice = (TextView) findViewById(R.id.comm_office);
        myCommHolder.commOffice.setText(extraOffice);

        myCommHolder.commContact = (TextView) findViewById(R.id.comm_contact);
        myCommHolder.commContact.setText(extraContact);

        myCommHolder.commPC = (TextView) findViewById(R.id.comm_pc);
        myCommHolder.commPC.setText(extraPC);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void addFavorite(View v) {
        ImageView favStar = (ImageView) findViewById(R.id.fav_button_comm);
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Set<String> cidKeys = sharedPref.getStringSet(getString(R.string.favoriteCommittees), new HashSet<String>());
        Set<String> newSet = new HashSet<String>();
        newSet.addAll(cidKeys);
        if (newSet.contains(currCommId)) {
            newSet.remove(currCommId);
            favStar.setImageResource(R.drawable.ic_inactive_star);
        }
        else {
            newSet.add(currCommId);
            favStar.setImageResource(R.drawable.ic_star);
        }
        editor.putStringSet(getString(R.string.favoriteCommittees), newSet);
        editor.apply();
    }

}
