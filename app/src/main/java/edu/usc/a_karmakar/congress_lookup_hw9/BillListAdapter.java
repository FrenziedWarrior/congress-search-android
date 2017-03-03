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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by abhishek-karmakar on 11/21/2016.
 */

class BillListAdapter extends ArrayAdapter<MyBillTag> {

    private Context mContext;
    private int layoutResourceId;
    private MyBillTag data[];

    BillListAdapter (Context mContext, int layoutResourceId, MyBillTag[] data) {
        super(mContext, layoutResourceId, data);
        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    private static class ViewHolder {
        TextView billId;
        TextView billTitle;
        TextView billDate;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        View rowView = convertView;
        MyBillTag selectedItem = data[position];

        if(rowView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            rowView = inflater.inflate(layoutResourceId, parent, false);
            holder = new BillListAdapter.ViewHolder();
            holder.billId = (TextView) rowView.findViewById(R.id.tv_bill_id);
            holder.billTitle= (TextView) rowView.findViewById(R.id.tv_bill_title);
            holder.billDate = (TextView) rowView.findViewById(R.id.tv_bill_date);
            rowView.setTag(holder);
        }
        else {
            holder = (BillListAdapter.ViewHolder) rowView.getTag();
        }

        String bill_id = selectedItem.getBill_id();
        holder.billId.setText(bill_id);
        String offTitle = selectedItem.getOfficial_title();
        String shortTitle = selectedItem.getShort_title();
        if (shortTitle != null) {
            holder.billTitle.setText(shortTitle);
        }
        else {
            holder.billTitle.setText(offTitle);
        }

        SimpleDateFormat formatOld = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat formatNew = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        String introOn = selectedItem.getIntroduced_on();
        try {
            introOn = formatNew.format(formatOld.parse(introOn));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.billDate.setText(introOn);

        return rowView;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    public void update(MyBillTag[] data) {
        this.data = data;
    }
}
