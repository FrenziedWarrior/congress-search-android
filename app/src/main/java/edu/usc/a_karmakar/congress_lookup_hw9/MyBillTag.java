package edu.usc.a_karmakar.congress_lookup_hw9;

import com.google.gson.Gson;

/**
 * Created by abhishek-karmakar on 11/21/2016.
 */

class MyBillTag {
    public String getBill_id() {
        return bill_id;
    }
    public String getOfficial_title() {
        return official_title;
    }
    public String getIntroduced_on() {
        return introduced_on;
    }
    public String getBill_type() {
        return bill_type;
    }
    public String getChamber() {
        return chamber;
    }

    private String bill_id;
    private String official_title;

    public String getShort_title() {
        return short_title;
    }

    private String short_title;
    private String introduced_on;
    private String bill_type;
    private String chamber;

    public History getHistory() {
        return history;
    }

    private History history;

    public LastVersion getLast_version() {
        return last_version;
    }

    public URLS getUrls() {
        return urls;
    }

    private LastVersion last_version;
    private URLS urls;

    public Sponsor getSponsor() {
        return sponsor;
    }

    private Sponsor sponsor;

    public class History {
        public boolean isActive() {
            return active;
        }
        private boolean active;
    }

    public class Sponsor {
        String getTitle() {
            return title;
        }
        String getFirst_name() {
            return first_name;
        }
        String getLast_name() {
            return last_name;
        }

        private String title;
        private String first_name;
        private String last_name;
    }

    public class LastVersion {
        public String getVersion_name() {
            return version_name;
        }

        public Links getUrl() {
            return urls;
        }

        private String version_name;
        private Links urls;
    }

    public class URLS {
        public String getCongress() {
            return congress;
        }

        private String congress;
    }

    public class Links{
        public String getHtml() {
            return html;
        }

        private String html;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
