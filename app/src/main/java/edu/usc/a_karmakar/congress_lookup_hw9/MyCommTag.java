package edu.usc.a_karmakar.congress_lookup_hw9;

import com.google.gson.Gson;

/**
 * Created by abhishek-karmakar on 11/22/2016.
 */

public class MyCommTag {
    private String committee_id;
    private String name;
    private String chamber;
    private String parent_committee_id;
    private String phone;
    private String office;

    public String getCommittee_id() {
        return committee_id;
    }

    public String getName() {
        return name;
    }

    public String getChamber() {
        return chamber;
    }

    public String getParent_committee_id() {
        return parent_committee_id;
    }

    public String getPhone() {
        return phone;
    }

    public String getOffice() {
        return office;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
