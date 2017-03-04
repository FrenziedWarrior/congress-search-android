package edu.usc.a_karmakar.congress_lookup_hw9;

import com.google.gson.Gson;

/**
 * Created by abhishek-karmakar on 11/20/2016.
 */

class MyLegsDetailTag {
    String getBioguide_id() {
        return bioguide_id;
    }
    String getTitle() {
        return title;
    }
    String getFirst_name() {
        return first_name;
    }
    String getLast_name() {
        return last_name;
    }
    String getBirthday() {
        return birthday;
    }
    String getParty() {
        return party;
    }
    String getChamber() {
        return chamber;
    }
    String getTerm_start() {
        return term_start;
    }
    String getTerm_end() {
        return term_end;
    }
    String getOffice() {
        return office;
    }
    String getPhone() {
        return phone;
    }
    String getFax() {
        return fax;
    }
    String getState() {
        return state;
    }
    String getState_name() { return state_name; }
    String getOc_email() {
        return oc_email;
    }
    String getTwitter_id() {
        return twitter_id;
    }
    String getFacebook_id() {
        return facebook_id;
    }
    String getWebsite() {
        return website;
    }

    private String bioguide_id;
    private String title;
    private String first_name;
    private String last_name;
    private String birthday;
    private String party;
    private String chamber;
    private String state_name;
    private String state;
    private String term_start;
    private String term_end;
    private String office;
    private String phone;
    private String fax;
    private String oc_email;
    private String twitter_id;
    private String facebook_id;
    private String website;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}