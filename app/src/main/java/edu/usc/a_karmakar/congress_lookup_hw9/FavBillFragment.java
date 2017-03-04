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
public class FavBillFragment extends Fragment {
    private View rootView;
    MyBillTag[] billArray;
    BillListAdapter adapter;
    SharedPreferences sharedPref;
    Set<String> listFavBills;
    Gson gson;
    public FavBillFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bill_act, container, false);
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
        populateFavBillList();
    }


    private void populateFavBillList() {
        listFavBills = sharedPref.getStringSet(getString(R.string.favoriteBills), new HashSet<String>());
        Set<String> newSet = new HashSet<>();
        newSet.addAll(listFavBills);

        String[] strArrBills = newSet.toArray(new String[newSet.size()]);
        billArray = new MyBillTag[newSet.size()];
        for(int i=0; i<strArrBills.length; ++i) {
            billArray[i] = new MyBillTag();
            billArray[i] = gson.fromJson(strArrBills[i], MyBillTag.class);
        }

        adapter = new BillListAdapter(getActivity(), R.layout.list_item_bill, billArray);
        ListView listView = (ListView) rootView.findViewById(R.id.bill_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getActivity(), BillDetailActivity.class);
                mIntent.putExtra("billBundle", billArray[position].toString());
                startActivity(mIntent);
            }
        });
    }


}
