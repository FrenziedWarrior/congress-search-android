package edu.usc.a_karmakar.congress_lookup_hw9;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by abhishek-karmakar on 11/22/2016.
 */

public class CommListAdapter extends ArrayAdapter<MyCommTag> {
    private Context mContext;
    private int layoutResourceId;
    private MyCommTag data[];

    CommListAdapter (Context mContext, int layoutResourceId, MyCommTag[] data) {
        super(mContext, layoutResourceId, data);
        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    private static class ViewHolder {
        TextView commId;
        TextView commName;
        TextView commChamber;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        View rowView = convertView;
        MyCommTag selectedItem = data[position];

        if(rowView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            rowView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.commId = (TextView) rowView.findViewById(R.id.tv_comm_id);
            holder.commName = (TextView) rowView.findViewById(R.id.tv_comm_name);
            holder.commChamber = (TextView) rowView.findViewById(R.id.tv_comm_chamber);
            rowView.setTag(holder);
        }
        else {
            holder = (ViewHolder) rowView.getTag();
        }

        String comm_id = selectedItem.getCommittee_id();
        holder.commId.setText(comm_id);
        String comm_name = selectedItem.getName();
        holder.commName.setText(comm_name);
        String thisChamber = selectedItem.getChamber();
        String comm_chamber = thisChamber.equals("house") ? "House" : (thisChamber.equals("senate") ? "Senate" : "Joint");
        holder.commChamber.setText(comm_chamber);

        return rowView;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    public void update(MyCommTag[] data) {
        this.data = data;
    }
}
