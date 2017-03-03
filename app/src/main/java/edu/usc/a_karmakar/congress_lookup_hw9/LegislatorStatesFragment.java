package edu.usc.a_karmakar.congress_lookup_hw9;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class LegislatorStatesFragment extends Fragment implements MyJsonTask.AsyncResponse {
    private View rootView;
    MyLegislatorListTag[] legsArray;
    ArrayList<String> stateList;
    LegislatorListAdapter adapter;
    private MyJsonTask myTask;
    public LegislatorStatesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_states_legislator, container, false);
        try {
            myTask = new MyJsonTask(this);
            CustomUriBuilder targetUri = new CustomUriBuilder("legs", "states");
            myTask.execute(new URL(targetUri.buildUri()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        stateList = new ArrayList<>();
    }


    @Override
    public void processFinish(String output) {
        ListView listView;
        GsonFilter gfObject = new GsonFilter(output);
        gfObject.legsParse("state");

        legsArray = gfObject.getLegsInfoArray();
        adapter = new LegislatorListAdapter(getActivity(), R.layout.list_item_legs, legsArray);
        listView = (ListView) rootView.findViewById(R.id.legs_list_view);
        listView.setAdapter(adapter);

        stateList = gfObject.getIndexTargetList();
        String[] stateListArray = stateList.toArray(new String[stateList.size()]);
        SideMapIndex stateMapIndex = new SideMapIndex(stateListArray, rootView, getActivity());
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