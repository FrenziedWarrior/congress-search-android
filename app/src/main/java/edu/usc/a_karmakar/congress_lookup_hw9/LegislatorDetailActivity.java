package edu.usc.a_karmakar.congress_lookup_hw9;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class LegislatorDetailActivity extends AppCompatActivity implements MyJsonTask.AsyncResponse{
    private MyLegsDetailTag infoObj;
    private ViewHolder myLegHolder;

    private class ViewHolder {
        ImageView legsDP;
        ImageView legsFbBtn;
        ImageView legsTwBtn;
        ImageView legsWebBtn;
        TextView legsParty;
        ImageView legsPartyLogo;
        TextView legsFullname;
        TextView legsEmail;
        TextView legsChamber;
        TextView legsContact;
        TextView legsStartTerm;
        TextView legsEndTerm;
        ProgressBar legsTerm;
        TextView legsTermLabel;
        TextView legsOffice;
        TextView legsFax;
        TextView legsBirthday;
        TextView legsState;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislator_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Legislator Info");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String bidParam = getIntent().getExtras().getString("bioguide");
        try {
            CustomUriBuilder targetUri = new CustomUriBuilder("legs", "bid:"+bidParam);
            new MyJsonTask(this).execute(new URL(targetUri.buildUri()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processFinish(String output) {
        GsonFilter gfObject = new GsonFilter(output);
        gfObject.legsDetailsParse();
        infoObj = gfObject.getLegDetailBundle();

        initViewHolder();

        // load favorite star or regular star
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Set<String> allFavLegs = sharedPref.getStringSet(getString(R.string.favoriteLegislators), new HashSet<String>());
        ImageView favStar = (ImageView) findViewById(R.id.fav_button_legs);
        if (allFavLegs.contains(infoObj.toString())) {
            favStar.setImageResource(R.drawable.ic_star);
        }
        else {
            favStar.setImageResource(R.drawable.ic_inactive_star);
        }

        String imageUrl = "http://theunitedstates.io/images/congress/225x275/" + infoObj.getBioguide_id() + ".jpg";
        Picasso.with(this).load(imageUrl).into(myLegHolder.legsDP);

        String party = infoObj.getParty();
        imageUrl = "@drawable/" + party.toLowerCase();
        int imageResource = getResources().getIdentifier(imageUrl, "drawable", getPackageName());
        Picasso.with(this).load(imageResource).into(myLegHolder.legsPartyLogo);

        int labelResource = getResources().getIdentifier(party.toLowerCase(), "string", getPackageName());
        myLegHolder.legsParty.setText(labelResource);

        String fullName = infoObj.getTitle() + "." + infoObj.getLast_name() + ", " + infoObj.getFirst_name();
        myLegHolder.legsFullname.setText(fullName);

        String email = infoObj.getOc_email();
        email = email==null ? "N.A." : email;
        myLegHolder.legsEmail.setText(email);

        String chamber = infoObj.getChamber().substring(0,1).toUpperCase() + infoObj.getChamber().substring(1).toLowerCase();
        myLegHolder.legsChamber.setText(chamber);

        String contact = infoObj.getPhone();
        contact = contact.equals("null") ? "N.A." : contact;
        myLegHolder.legsContact.setText(contact);

        try {
            Date today = new Date();
            SimpleDateFormat formatOld = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat formatNew = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

            String termStart = infoObj.getTerm_start();
            Date ts_date = formatOld.parse(termStart);
            termStart = formatNew.format(formatOld.parse(termStart));
            myLegHolder.legsStartTerm.setText(termStart);

            String termEnd = infoObj.getTerm_end();
            Date te_date = formatOld.parse(termEnd);
            termEnd = formatNew.format(formatOld.parse(termEnd));
            myLegHolder.legsEndTerm.setText(termEnd);

            long NdiffinMs = today.getTime() - ts_date.getTime();
            long DdiffinMs = te_date.getTime() - ts_date.getTime();
            double termPerc = (double) NdiffinMs/DdiffinMs * 100;
            int termPercInt = (int) termPerc;
            String progressLabel = termPercInt + "%";
            myLegHolder.legsTermLabel.setText(progressLabel);
            myLegHolder.legsTerm.setProgress(termPercInt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String office = infoObj.getOffice();
        office = office==null ? "N.A." : office;
        myLegHolder.legsOffice.setText(office);

        String state = infoObj.getState();
        myLegHolder.legsState.setText(state);

        String fax = infoObj.getFax();
        fax = fax==null ? "N.A." : fax;
        myLegHolder.legsFax.setText(fax);

        String birthday = infoObj.getBirthday();
        try {
            SimpleDateFormat formatOld = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat formatNew = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
            birthday = formatNew.format(formatOld.parse(birthday));
            myLegHolder.legsBirthday.setText(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

    public void openWebsite(View v) {
        String webLink = infoObj.getWebsite();
        if (webLink == null) {
            Toast.makeText(this, "No website found!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webLink));
            startActivity(browserIntent);
        }
    }

    public void openFacebook(View v) {
        String fbLink = "http://www.facebook.com/"+infoObj.getFacebook_id();
        if(fbLink.equals("http://www.facebook.com/null")) {
            Toast.makeText(this, "No FB ID found!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fbLink));
            startActivity(browserIntent);
        }
    }

    public void openTwitter(View v) {
        String twLink = "http://www.twitter.com/" + infoObj.getTwitter_id();
        if (twLink.equals("http://www.twitter.com/null")) {
            Toast.makeText(this, "No Twitter ID found!", Toast.LENGTH_SHORT).show();
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twLink));
            startActivity(browserIntent);
        }
    }

    public void addFavorite(View v) {
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> listOfFavLegs = sharedPref.getStringSet(getString(R.string.favoriteLegislators), new HashSet<String>());
        Set<String> newSet = new HashSet<>();
        ImageView favStar = (ImageView) findViewById(R.id.fav_button_legs);
        String infoObjJsonString = infoObj.toString();

        newSet.addAll(listOfFavLegs);
        if (newSet.contains(infoObjJsonString)) {
            newSet.remove(infoObjJsonString);
            favStar.setImageResource(R.drawable.ic_inactive_star);
        }
        else {
            newSet.add(infoObjJsonString);
            favStar.setImageResource(R.drawable.ic_star);
        }
        editor.putStringSet(getString(R.string.favoriteLegislators), newSet);
        editor.apply();
    }

    private void initViewHolder() {
        myLegHolder = new ViewHolder();
        myLegHolder.legsDP = (ImageView) findViewById(R.id.legs_face);
        myLegHolder.legsFbBtn = (ImageView) findViewById(R.id.tw_button);
        myLegHolder.legsTwBtn = (ImageView) findViewById(R.id.fb_button);
        myLegHolder.legsWebBtn = (ImageView) findViewById(R.id.web_button);
        myLegHolder.legsPartyLogo = (ImageView) findViewById(R.id.legs_party_logo);
        myLegHolder.legsParty = (TextView) findViewById(R.id.legs_party_label);
        myLegHolder.legsFullname = (TextView) findViewById(R.id.legs_name);
        myLegHolder.legsEmail = (TextView) findViewById(R.id.legs_email);
        myLegHolder.legsChamber = (TextView) findViewById(R.id.legs_chamber);
        myLegHolder.legsContact = (TextView) findViewById(R.id.legs_contact);
        myLegHolder.legsStartTerm = (TextView) findViewById(R.id.legs_start_term);
        myLegHolder.legsEndTerm = (TextView) findViewById(R.id.legs_end_term);
        myLegHolder.legsTerm = (ProgressBar) findViewById(R.id.legs_term_progress);
        myLegHolder.legsTermLabel = (TextView) findViewById(R.id.legs_progress_label);
        myLegHolder.legsState = (TextView) findViewById(R.id.legs_state);
        myLegHolder.legsOffice = (TextView) findViewById(R.id.legs_office);
        myLegHolder.legsFax = (TextView) findViewById(R.id.legs_fax);
        myLegHolder.legsBirthday = (TextView) findViewById(R.id.legs_birthday);
    }
}