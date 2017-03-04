package edu.usc.a_karmakar.congress_lookup_hw9;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class BillDetailActivity extends AppCompatActivity{
    private String currBillBundle;

    private class ViewHolder {
        TextView billId;
        TextView billTitle;
        TextView billType;
        TextView billSponsor;
        TextView billChamber;
        TextView billStatus;
        TextView billIntroOn;
        TextView billCongressUrl;
        TextView billVersionStatus;
        TextView billBillUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBill);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Bill Info");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Gson gson = new Gson();
        currBillBundle = getIntent().getExtras().getString("billBundle");
        MyBillTag bdBundle = gson.fromJson(currBillBundle, MyBillTag.class);

        String extraBid = bdBundle.getBill_id();
        String extraTitle = bdBundle.getOfficial_title();
        String extraType = bdBundle.getBill_type();
        String extraChamber = bdBundle.getChamber().equals("house") ? "House" : "Senate";
        String extraIon = bdBundle.getIntroduced_on();
        String extraSponsor = bdBundle.getSponsor().getTitle() + ". " +
                bdBundle.getSponsor().getLast_name() + ", " +
                bdBundle.getSponsor().getFirst_name();
        String extraStatus = bdBundle.getHistory().isActive() ? "Active" : "New";
        String extraCurl = bdBundle.getUrls().getCongress();
        String extraVS = bdBundle.getLast_version().getVersion_name();
        String extraUrl = bdBundle.getLast_version().getUrl().getHtml();

        // load favorite star or regular star
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Set<String> bidKeys = sharedPref.getStringSet(getString(R.string.favoriteBills), new HashSet<String>());
        ImageView favStar = (ImageView) findViewById(R.id.fav_button_bill);
        if (bidKeys.contains(currBillBundle)) {
            favStar.setImageResource(R.drawable.ic_star);
        }
        else {
            favStar.setImageResource(R.drawable.ic_inactive_star);
        }

        ViewHolder myBillHolder = new ViewHolder();

        myBillHolder.billId = (TextView) findViewById(R.id.bill_id);
        myBillHolder.billId.setText(extraBid);

        myBillHolder.billTitle = (TextView) findViewById(R.id.bill_title);
        myBillHolder.billTitle.setText(extraTitle);

        myBillHolder.billType = (TextView) findViewById(R.id.bill_type);
        myBillHolder.billType.setText(extraType);

        myBillHolder.billStatus = (TextView) findViewById(R.id.bill_status);
        myBillHolder.billStatus.setText(extraStatus);

        myBillHolder.billChamber = (TextView) findViewById(R.id.bill_chamber);
        myBillHolder.billChamber.setText(extraChamber);

        try {
            SimpleDateFormat formatOld = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat formatNew = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
            extraIon = formatNew.format(formatOld.parse(extraIon));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myBillHolder.billIntroOn = (TextView) findViewById(R.id.bill_ion);
        myBillHolder.billIntroOn.setText(extraIon);

        myBillHolder.billSponsor = (TextView) findViewById(R.id.bill_sponsor);
        myBillHolder.billSponsor.setText(extraSponsor);

        myBillHolder.billBillUrl = (TextView) findViewById(R.id.bill_url);
        myBillHolder.billBillUrl.setText(extraUrl);

        myBillHolder.billCongressUrl = (TextView) findViewById(R.id.bill_curl);
        myBillHolder.billCongressUrl.setText(extraCurl);

        myBillHolder.billVersionStatus = (TextView) findViewById(R.id.bill_vs);
        myBillHolder.billVersionStatus.setText(extraVS);
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
        ImageView favStar = (ImageView) findViewById(R.id.fav_button_bill);
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> billBundleSet = sharedPref.getStringSet(getString(R.string.favoriteBills), new HashSet<String>());

        Set<String> newSet = new HashSet<>();
        newSet.addAll(billBundleSet);
        if (newSet.contains(currBillBundle)) {
            newSet.remove(currBillBundle);
            favStar.setImageResource(R.drawable.ic_inactive_star);
        }
        else {
            newSet.add(currBillBundle);
            favStar.setImageResource(R.drawable.ic_star);
        }
        editor.putStringSet(getString(R.string.favoriteBills), newSet).apply();
    }
}

