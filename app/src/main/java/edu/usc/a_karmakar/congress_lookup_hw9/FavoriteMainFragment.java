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
public class FavoriteMainFragment extends Fragment {
    private View rootView;
    private FragmentTabHost mTabHost;


    public FavoriteMainFragment() {
        // Required empty public constructor
    }

    public static FavoriteMainFragment newInstance() { return new FavoriteMainFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comm_parent, container, false);
        mTabHost = (FragmentTabHost) rootView.findViewById(R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("fav-legs").setIndicator("Legislators"), FavLegsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fav-bill").setIndicator("Bills"), FavBillFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fav-comm").setIndicator("Committees"), FavCommFragment.class, null);
        return rootView;
    }
}
