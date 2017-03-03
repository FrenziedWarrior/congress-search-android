package edu.usc.a_karmakar.congress_lookup_hw9;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillParentFragment extends Fragment {
    private View rootView;
    private FragmentTabHost mTabHost;

    public BillParentFragment() {
        // Required empty public constructor
    }

    public static BillParentFragment newInstance() { return new BillParentFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_bill_parent, container, false);
        mTabHost = (FragmentTabHost) rootView.findViewById(R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("bill-act").setIndicator("Active Bills"), BillActFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("bill-new").setIndicator("New Bills"), BillNewFragment.class, null);
        return rootView;
    }


}
