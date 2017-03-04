package edu.usc.a_karmakar.congress_lookup_hw9;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by abhishek-karmakar on 11/18/2016.
 */

class LegislatorListAdapter extends ArrayAdapter<MyLegislatorListTag> {
    private Context mContext;
    private int layoutResourceId;
    private MyLegislatorListTag data[];

    LegislatorListAdapter (Context mContext, int layoutResourceId, MyLegislatorListTag[] data) {
        super(mContext, layoutResourceId, data);
        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    private static class ViewHolder {
        ImageView legImg;
        TextView legFullName;
        TextView otherDetails;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        View rowView = convertView;
        MyLegislatorListTag obj = data[position];

        if(rowView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            rowView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.legImg = (ImageView) rowView.findViewById(R.id.img_leg);
            holder.legFullName = (TextView) rowView.findViewById(R.id.tv_name);
            holder.otherDetails = (TextView) rowView.findViewById(R.id.tv_state);
            rowView.setTag(holder);
        }
        else {
            holder = (ViewHolder) rowView.getTag();
        }

        String fullName = obj.getLast_name() + ", " + obj.getFirst_name();
        holder.legFullName.setText(fullName);

        String imageUrl = "http://theunitedstates.io/images/congress/225x275/" + obj.getBioguide_id() + ".jpg";
        Picasso.with(mContext).load(imageUrl).into(holder.legImg);

        String otherDets = "(" + obj.getParty() + ")";
        otherDets += obj.getState_name();
        otherDets += " - ";
        otherDets += "District: " + Integer.toString(obj.getDistrict());
        holder.otherDetails.setText(otherDets);

        return rowView;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    public void update(MyLegislatorListTag[] data) {
        this.data = data;
    }

}
