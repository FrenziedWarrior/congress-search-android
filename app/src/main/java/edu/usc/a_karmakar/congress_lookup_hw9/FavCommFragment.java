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
public class FavCommFragment extends Fragment implements MyJsonTask.AsyncResponse {
    MyCommTag[] commArray;
    CommListAdapter adapter;
    private ListView listView;
    private View rootView;
    private MyJsonTask myTask;

    public FavCommFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comm_tab, container, false);
        try {
            myTask = new MyJsonTask(this);
            myTask.execute(new URL("http://congress-lookup.appspot.com/congress8.php?method=com"));
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

        if (commArray==null) {
            return;
        }

        ArrayList<MyCommTag> newCommList = new ArrayList<>(Arrays.asList(commArray));
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Set<String> cidKeys = sharedPref.getStringSet(getString(R.string.favoriteCommittees), new HashSet<String>());
        for(int i=0; i<newCommList.size(); ++i) {
            if(!cidKeys.contains(newCommList.get(i).getCommittee_id())) {
                newCommList.remove(newCommList.get(i));
            }
        }

        commArray = newCommList.toArray(new MyCommTag[newCommList.size()]);
        adapter.update(commArray);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void processFinish(String output) {
        GsonFilter gfObject = new GsonFilter(output);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Set<String> cidKeys = sharedPref.getStringSet(getString(R.string.favoriteCommittees), new HashSet<String>());
        gfObject.filterFavComms(cidKeys);

        commArray = gfObject.getFavCommArray();
        adapter = new CommListAdapter(getActivity(), R.layout.list_item_comm, commArray);
        listView = (ListView) rootView.findViewById(R.id.comm_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getActivity(), CommDetailActivity.class);

                String thisCommId = commArray[position].getCommittee_id();
                String thisCommName = commArray[position].getName();
                String thisCommPC = commArray[position].getParent_committee_id();
                String thisCommChamber = commArray[position].getChamber().equals("house") ? "House" : "Senate";
                String thisCommContact = commArray[position].getPhone();
                String thisCommOffice = commArray[position].getOffice();

                mIntent.putExtra("commID", thisCommId);
                mIntent.putExtra("commName", thisCommName);
                mIntent.putExtra("commChamber", thisCommChamber);
                mIntent.putExtra("commPC", thisCommPC);
                mIntent.putExtra("commContact", thisCommContact);
                mIntent.putExtra("commOffice", thisCommOffice);

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
