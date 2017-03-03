package edu.usc.a_karmakar.congress_lookup_hw9;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavBillFragment extends Fragment implements MyJsonTask.AsyncResponse {
    private View rootView;
    private ListView listView;
    MyBillTag[] billArray;
    BillListAdapter adapter;
    private MyJsonTask myTask;

    public FavBillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bill_act, container, false);
        try {
            myTask = new MyJsonTask(this);
            myTask.execute(new URL("http://congress-lookup.appspot.com/congress8.php?method=bill"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        ActionBar mActionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setTitle("Favorites");

        if (billArray==null) {
            return;
        }

        ArrayList<MyBillTag> newBillList = new ArrayList<>(Arrays.asList(billArray));
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Set<String> bidKeys = sharedPref.getStringSet(getString(R.string.favoriteBills), new HashSet<String>());
        for(int i=0; i<newBillList.size(); ++i) {
            if(!bidKeys.contains(newBillList.get(i).getBill_id())) {
                newBillList.remove(newBillList.get(i));
            }
        }

        billArray = newBillList.toArray(new MyBillTag[newBillList.size()]);
        adapter.update(billArray);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void processFinish(String output) {
        GsonFilter gfObject = new GsonFilter(output);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        boolean exists = sharedPref.contains(getString(R.string.favoriteBills));
        Set<String> bidKeys = sharedPref.getStringSet(getString(R.string.favoriteBills), new HashSet<String>());

        gfObject.filterFavBills(bidKeys);
        billArray = gfObject.getFavBillArray();
        adapter = new BillListAdapter(getActivity(), R.layout.list_item_bill, billArray);
        listView = (ListView) rootView.findViewById(R.id.bill_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getActivity(), BillDetailActivity.class);

                String thisBillId = billArray[position].getBill_id();
                String thisBillTitle = billArray[position].getOfficial_title();
                String thisBillType = billArray[position].getBill_type();
                String thisBillChamber = billArray[position].getChamber().equals("house") ? "House" : "Senate";
                String thisBillIntroOn = billArray[position].getIntroduced_on();
                String thisBillSponsor = billArray[position].getSponsor().getTitle() + ". " +
                                        billArray[position].getSponsor().getLast_name() + ", " +
                                        billArray[position].getSponsor().getFirst_name();

                String thisBillStatus = billArray[position].getHistory().isActive() ? "Active" : "New";
                String thisBillCongressUrl = billArray[position].getUrls().getCongress();
                String thisBillVS = billArray[position].getLast_version().getVersion_name();
                String thisBillUrl = billArray[position].getLast_version().getUrl().getHtml();

                mIntent.putExtra("billID", thisBillId);
                mIntent.putExtra("billTitle", thisBillTitle);
                mIntent.putExtra("billType", thisBillType);
                mIntent.putExtra("billSponsor", thisBillSponsor);
                mIntent.putExtra("billChamber", thisBillChamber);
                mIntent.putExtra("billStatus", thisBillStatus);
                mIntent.putExtra("billIntroOn", thisBillIntroOn);
                mIntent.putExtra("billCurl", thisBillCongressUrl);
                mIntent.putExtra("billVS", thisBillVS);
                mIntent.putExtra("billUrl", thisBillUrl);

                startActivity(mIntent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        myTask.cancel(true);
    }
}
