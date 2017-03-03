package edu.usc.a_karmakar.congress_lookup_hw9;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommParentFragment extends Fragment {
    private View rootView;
    private FragmentTabHost mTabHost;

    public CommParentFragment() {
        // Required empty public constructor
    }

    public static CommParentFragment newInstance() { return new CommParentFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comm_parent, container, false);
        mTabHost = (FragmentTabHost) rootView.findViewById(R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("comm-house").setIndicator("House"), CommHouseFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("comm-senate").setIndicator("Senate"), CommSenFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("comm-joint").setIndicator("Joint"), CommJointFragment.class, null);
        return rootView;
    }
}
