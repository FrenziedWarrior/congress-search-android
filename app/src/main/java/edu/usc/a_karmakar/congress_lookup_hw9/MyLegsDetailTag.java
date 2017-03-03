package edu.usc.a_karmakar.congress_lookup_hw9;

import com.google.gson.Gson;

/**
 * Created by abhishek-karmakar on 11/20/2016.
 */

public class MyLegsDetailTag {
    public String getBioguide_id() {
        return bioguide_id;
    }
    public String getTitle() {
        return title;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getBirthday() {
        return birthday;
    }
    public String getParty() {
        return party;
    }
    public String getChamber() {
        return chamber;
    }
    public String getTerm_start() {
        return term_start;
    }
    public String getTerm_end() {
        return term_end;
    }
    public String getOffice() {
        return office;
    }
    public String getPhone() {
        return phone;
    }
    public String getFax() {
        return fax;
    }
    public String getState() {
        return state;
    }
    public String getState_name() { return state_name; }
    public String getOc_email() {
        return oc_email;
    }
    public String getTwitter_id() {
        return twitter_id;
    }
    public String getFacebook_id() {
        return facebook_id;
    }
    public String getWebsite() {
        return website;
    }


    public void setBioguide_id(String bioguide_id) {
        this.bioguide_id = bioguide_id;
    }
    public void setTitle(String title) { this.title = title; }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public void setParty(String party) {
        this.party = party;
    }
    public void setChamber(String chamber) {
        this.chamber = chamber;
    }
    public void setState_name(String state_name) {
        this.state_name = state_name;
    }
    public void setTerm_start(String term_start) {
        this.term_start = term_start;
    }
    public void setTerm_end(String term_end) {
        this.term_end = term_end;
    }
    public void setOffice(String office) {
        this.office = office;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setOc_email(String oc_email) {
        this.oc_email = oc_email;
    }
    public void setTwitter_id(String twitter_id) {
        this.twitter_id = twitter_id;
    }
    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }
    public void setWebsite(String website) {
        this.website = website;
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
