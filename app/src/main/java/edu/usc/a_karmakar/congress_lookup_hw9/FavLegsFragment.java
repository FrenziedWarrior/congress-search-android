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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavLegsFragment extends Fragment{
    private View rootView;
    private ListView listView;
    MyLegislatorListTag[] legsArray;
    ArrayList<String> namesList;
    LegislatorListAdapter adapter;
    SharedPreferences sharedPref;
    Set<String> listOfFavLegs;
    Gson gson;
//    private MyJsonTask myTask;

    public FavLegsFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_states_legislator, container, false);
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
        populateFavoriteList();
    }


    private void populateFavoriteList() {
        listOfFavLegs = sharedPref.getStringSet(getString(R.string.favoriteLegislators), new HashSet<String>());
        Set<String> newSet = new HashSet<>();
        newSet.addAll(listOfFavLegs);

        String[] stringArrayOfLegs = newSet.toArray(new String[newSet.size()]);
        legsArray = new MyLegislatorListTag[newSet.size()];
        namesList = new ArrayList<>(listOfFavLegs.size());
        for(int i=0; i<stringArrayOfLegs.length; ++i) {
            legsArray[i] = new MyLegislatorListTag();
            legsArray[i] = gson.fromJson(stringArrayOfLegs[i], MyLegislatorListTag.class);
            namesList.add(legsArray[i].getLast_name());
        }


        // sorting favorite legislators by last name
        ArrayList<MyLegislatorListTag> unsortedLegsList = new ArrayList<>(Arrays.asList(legsArray));
        Collections.sort(unsortedLegsList, new Comparator<MyLegislatorListTag>() {
            @Override
            public int compare(MyLegislatorListTag o1, MyLegislatorListTag o2) {
                return o1.getLast_name().compareTo(o2.getLast_name());
            }
        });
        legsArray = unsortedLegsList.toArray(new MyLegislatorListTag[unsortedLegsList.size()]);
        Collections.sort(namesList);

        adapter = new LegislatorListAdapter(getActivity(), R.layout.list_item_legs, legsArray);
        listView = (ListView) rootView.findViewById(R.id.legs_list_view);
        listView.setAdapter(adapter);

        String[] targetList = namesList.toArray(new String[namesList.size()]);
        SideMapIndex stateMapIndex = new SideMapIndex(targetList, rootView, getActivity());
        stateMapIndex.populateIndex();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getActivity(), LegislatorDetailActivity.class);
                String bidParam = legsArray[position].getBioguide_id();
                mIntent.putExtra("bioguide", bidParam);
                startActivity(mIntent);
            }
        });
    }
}
