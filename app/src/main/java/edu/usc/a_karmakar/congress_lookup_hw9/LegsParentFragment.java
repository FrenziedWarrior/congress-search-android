package edu.usc.a_karmakar.congress_lookup_hw9;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhishek-karmakar on 11/15/2016.
 */

public class LegsParentFragment extends Fragment{
    private View rootView;
    private FragmentTabHost mTabHost;

    public static LegsParentFragment newInstance() { return new LegsParentFragment(); }

    public LegsParentFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_legislator_list, container, false);
        mTabHost = (FragmentTabHost) rootView.findViewById(R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("legs-states").setIndicator("By State"), LegislatorStatesFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("legs-house").setIndicator("House"), LegislatorHouseFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("legs-senate").setIndicator("Senate"), LegislatorSenateFragment.class, null);

        return rootView;
    }
}