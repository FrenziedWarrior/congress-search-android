package edu.usc.a_karmakar.congress_lookup_hw9;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class LegislatorHouseFragment extends Fragment implements MyJsonTask.AsyncResponse{
    private View rootView;
    MyLegislatorListTag[] legsArray;
    ArrayList<String> namesList;
    LegislatorListAdapter adapter;
    private MyJsonTask myTask;

    public LegislatorHouseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_states_legislator, container, false);
        try {
            myTask = new MyJsonTask(this);
            CustomUriBuilder targetUri = new CustomUriBuilder("legs", "house");
            myTask.execute(new URL(targetUri.buildUri()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void processFinish(String output) {
        GsonFilter gfObject = new GsonFilter(output);
        gfObject.legsParse("name");

        legsArray = gfObject.getLegsInfoArray();
        adapter = new LegislatorListAdapter(getActivity(), R.layout.list_item_legs, legsArray);
        ListView listView = (ListView) rootView.findViewById(R.id.legs_list_view);
        listView.setAdapter(adapter);

        namesList = gfObject.getIndexTargetList();
        String[] nameListArray = namesList.toArray(new String[namesList.size()]);
        SideMapIndex stateMapIndex = new SideMapIndex(nameListArray, rootView, getActivity());
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

    @Override
    public void onPause() {
            super.onPause();
            myTask.cancel(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar mActionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setTitle("Legislators");
    }

}