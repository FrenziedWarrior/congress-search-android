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
public class BillActFragment extends Fragment implements MyJsonTask.AsyncResponse {
    MyBillTag[] billArray;
    BillListAdapter adapter;
    View rootView;
    private MyJsonTask myTask;

    public BillActFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bill_act, container, false);
        try {
            myTask = new MyJsonTask(this);
            CustomUriBuilder targetUri = new CustomUriBuilder("bill", "active");
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
        gfObject.billParse();

        billArray = gfObject.getBillInfoArray();
        adapter = new BillListAdapter(getActivity(), R.layout.list_item_bill, billArray);
        listView = (ListView) rootView.findViewById(R.id.bill_list_view);
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
        mActionBar.setTitle("Bills");
    }
}
