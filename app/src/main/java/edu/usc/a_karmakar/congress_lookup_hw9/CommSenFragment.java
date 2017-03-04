package edu.usc.a_karmakar.congress_lookup_hw9;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommSenFragment extends Fragment implements MyJsonTask.AsyncResponse{
    MyCommTag[] commArray;
    CommListAdapter adapter;
    View rootView;
    private MyJsonTask myTask;

    public CommSenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comm_tab, container, false);
        try {
            myTask = new MyJsonTask(this);
            myTask = new MyJsonTask(this);
            CustomUriBuilder targetUri = new CustomUriBuilder("comm", "senate");
            myTask.execute(new URL(targetUri.buildUri()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void processFinish(String output) {
        ListView listView;
        GsonFilter gfObject = new GsonFilter(output);
        gfObject.commParse("senate");

        commArray = gfObject.getCommInfoArray();
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

    @Override
    public void onResume() {
        super.onResume();
        ActionBar mActionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setTitle("Committees");
    }

}
