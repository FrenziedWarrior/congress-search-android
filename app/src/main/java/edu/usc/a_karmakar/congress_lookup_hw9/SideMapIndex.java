package edu.usc.a_karmakar.congress_lookup_hw9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abhishek-karmakar on 11/18/2016.
 */

public class SideMapIndex implements View.OnClickListener {
    private Map<String, Integer> theMapIndex;
    private View rootView;
    private ListView targetListView;
    private Context context;

    public SideMapIndex(String[] targetList, View rootView, Context context) {
        this.theMapIndex = new LinkedHashMap<>();
        this.rootView = rootView;
        this.context = context;
        makeIndexList(targetList);
    }

    private void makeIndexList(String[] targetList) {
        for (int i = 0; i < targetList.length; i++) {
            String state = targetList[i];
            String index = state.substring(0, 1);

            if (theMapIndex.get(index) == null)
                theMapIndex.put(index, i);
        }
    }

    // tab-specific
    public void populateIndex() {
        LinearLayout indexLayout = (LinearLayout) rootView.findViewById(R.id.side_index);
        if(indexLayout.getChildCount() > 0)
            indexLayout.removeAllViews();

        TextView textView;
        List<String> indexList = new ArrayList<>(theMapIndex.keySet());
        for (String index : indexList) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            textView = (TextView) inflater.inflate(R.layout.side_index_list_item, null);
            textView.setText("");
            textView.setText(index);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

    @Override
    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        targetListView = (ListView) rootView.findViewById(R.id.legs_list_view);
        targetListView.setSelection(theMapIndex.get(selectedIndex.getText()));
    }
}
