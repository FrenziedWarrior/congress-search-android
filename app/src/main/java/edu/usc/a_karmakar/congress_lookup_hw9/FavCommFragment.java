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

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavCommFragment extends Fragment{
    private View rootView;
    MyCommTag[] commArray;
    CommListAdapter adapter;
    Gson gson;
    SharedPreferences sharedPref;
    Set<String> listFavComms;

    public FavCommFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comm_tab, container, false);
        gson = new Gson();
        sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar mActionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setTitle("Favorites");
        populateFavComms();
    }

    public void populateFavComms() {
        listFavComms = sharedPref.getStringSet(getString(R.string.favoriteCommittees), new HashSet<String>());
        Set<String> newSet = new HashSet<>();
        newSet.addAll(listFavComms);

        String[] arrComms = newSet.toArray(new String[newSet.size()]);
        commArray = new MyCommTag[newSet.size()];
        for(int i=0; i<arrComms.length; ++i) {
            commArray[i] = new MyCommTag();
            commArray[i] = gson.fromJson(arrComms[i], MyCommTag.class);
        }

        adapter = new CommListAdapter(getActivity(), R.layout.list_item_comm, commArray);
        ListView listView = (ListView) rootView.findViewById(R.id.comm_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getActivity(), CommDetailActivity.class);
                mIntent.putExtra("commBundle", commArray[position].toString());
                startActivity(mIntent);
            }
        });

    }

}
