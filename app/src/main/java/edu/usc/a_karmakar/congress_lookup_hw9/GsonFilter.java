package edu.usc.a_karmakar.congress_lookup_hw9;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by abhishek-karmakar on 11/18/2016.
 */

public class GsonFilter {
    private Gson gson;
    private String myObj;
    private JSONObject details;
//    private JSONArray details;

    MyLegsDetailTag getLegDetailBundle() { return ldBundle; }
    private MyLegsDetailTag ldBundle;

    public MyBillTag getBillDetailBundle() { return bdBundle; }
    private MyBillTag bdBundle;

//    public MyLegsDetailTag getLegDetailBundle() { return ldBundle; }
//    private MyLegsDetailTag ldBundle;

    MyLegislatorListTag[] getLegsInfoArray() { return legsInfoArray; }
    private MyLegislatorListTag[] legsInfoArray;

    public MyLegislatorListTag[] getFavLegArray() {return favLegArray;}
    private MyLegislatorListTag[] favLegArray;

    public MyBillTag[] getFavBillArray() {return favBillArray;}
    private MyBillTag[] favBillArray;

    public MyCommTag[] getFavCommArray() {return favCommArray;}
    private MyCommTag[] favCommArray;

    MyBillTag[] getBillInfoArray() {
        return billInfoArray;
    }
    private MyBillTag[] billInfoArray;

    MyCommTag[] getCommInfoArray() {
        return commInfoArray;
    }
    private MyCommTag[] commInfoArray;

    ArrayList<String> getIndexTargetList() {
        return indexTargetList;
    }
    private ArrayList<String> indexTargetList;


    public GsonFilter(String o) {
        this.myObj = o;
        gson = new Gson();
        indexTargetList = new ArrayList<>();
    }

    void legsParse(String indexAttr) {
        JSONObject currLeg;
        JSONArray allLegs;

        try {
            allLegs = new JSONObject(myObj).getJSONArray("results");
            legsInfoArray = new MyLegislatorListTag[allLegs.length()];
            for(int i=0; i<allLegs.length(); i++) {
                currLeg = allLegs.getJSONObject(i);

     /*           if (!currLeg.has("facebook_id") || currLeg.getString("facebook_id")=="null") {
                    Log.v("FBID Missing", currLeg.getString("bioguide_id"));
                }

                if (!currLeg.has("twitter_id") || currLeg.getString("twitter_id")=="null") {
                    Log.v("TwID Missing", currLeg.getString("bioguide_id"));
                }*/

                legsInfoArray[i] = new MyLegislatorListTag();
                legsInfoArray[i] = gson.fromJson(currLeg.toString(), MyLegislatorListTag.class);
                if (indexAttr.equals("state")) {
                    indexTargetList.add(legsInfoArray[i].getState_name());
                }
                else if (indexAttr.equals("name")) {
                    indexTargetList.add(legsInfoArray[i].getLast_name());
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void legsDetailsParse() {
        try {
//            String sth = new JSONArray(myObj).getString(0);
            details = new JSONObject(myObj).getJSONArray("results").getJSONObject(0);
            ldBundle = gson.fromJson(details.toString(), MyLegsDetailTag.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void filterFavLegislators(Set<String> s) {
        JSONObject currLeg;
        JSONArray allLegs;
        ArrayList<MyLegislatorListTag> targetLegs = new ArrayList<>();

        try {
            allLegs = new JSONObject(myObj).getJSONArray("results");
            for(int i=0; i<allLegs.length(); i++) {
                currLeg = allLegs.getJSONObject(i);
                if (s.contains(currLeg.getString("bioguide_id"))) {
                    targetLegs.add(gson.fromJson(currLeg.toString(), MyLegislatorListTag.class));
                    indexTargetList.add(currLeg.getString("last_name"));
                }
                favLegArray = targetLegs.toArray(new MyLegislatorListTag[targetLegs.size()]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void billDetailParse() {
        try {
            details = new JSONObject(myObj).getJSONArray("results").getJSONObject(0);
            ldBundle = gson.fromJson(details.toString(), MyLegsDetailTag.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    void billParse() {
//        String sth = null;
        JSONObject currBill;
        try {
/*
            if(check.equals("active")) {
                sth = new JSONArray(myObj).getString(0);
            }
            else if (check.equals("new")) {
                sth = new JSONArray(myObj).getString(1);
            }
*/
            JSONArray resultArray = new JSONObject(myObj).getJSONArray("results");
            billInfoArray = new MyBillTag[resultArray.length()];
            for(int i=0; i<resultArray.length(); i++) {
                currBill = resultArray.getJSONObject(i);
                billInfoArray[i] = new MyBillTag();
                billInfoArray[i] = gson.fromJson(currBill.toString(), MyBillTag.class);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void filterFavBills(Set<String> s) {
        String currentCategory;
        JSONObject currBill;
        JSONArray allBills;
        ArrayList<MyBillTag> targetBills = new ArrayList<>();

        try {
            currentCategory = new JSONArray(myObj).getString(0);
            JSONArray resultArray = new JSONObject(currentCategory).getJSONArray("results");
            for(int i=0; i<resultArray.length(); i++) {
                currBill = resultArray.getJSONObject(i);
                if (s.contains(currBill.getString("bill_id"))) {
                    targetBills.add(gson.fromJson(currBill.toString(), MyBillTag.class));
                }
            }

            currentCategory = new JSONArray(myObj).getString(1);
            resultArray = new JSONObject(currentCategory).getJSONArray("results");
            for(int i=0; i<resultArray.length(); i++) {
                currBill = resultArray.getJSONObject(i);
                if (s.contains(currBill.getString("bill_id"))) {
                    targetBills.add(gson.fromJson(currBill.toString(), MyBillTag.class));
                }
            }

            favBillArray = targetBills.toArray(new MyBillTag[targetBills.size()]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    void commParse(String check) {
        JSONArray allComms;
        JSONObject currComm;
        String whichCh;
        ArrayList<MyCommTag> targetComms = new ArrayList<>();

        try {
            allComms = new JSONObject(myObj).getJSONArray("results");
            for(int i=0; i<allComms.length(); i++) {
                currComm = allComms.getJSONObject(i);
                whichCh = currComm.getString("chamber");
                if(whichCh.equals(check)) {
                    targetComms.add(gson.fromJson(currComm.toString(), MyCommTag.class));
                }
            }

            commInfoArray = targetComms.toArray(new MyCommTag[targetComms.size()]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void filterFavComms(Set<String> s) {
        JSONArray allComms;
        JSONObject currComm;
        ArrayList<MyCommTag> targetComms = new ArrayList<>();

        try {
            allComms = new JSONObject(myObj).getJSONArray("results");
            for(int i=0; i<allComms.length(); i++) {
                currComm = allComms.getJSONObject(i);
                if (s.contains(currComm.getString("committee_id"))) {
                    targetComms.add(gson.fromJson(currComm.toString(), MyCommTag.class));
                }
            }

            favCommArray = targetComms.toArray(new MyCommTag[targetComms.size()]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
