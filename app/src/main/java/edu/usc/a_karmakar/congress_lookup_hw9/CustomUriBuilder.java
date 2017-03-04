package edu.usc.a_karmakar.congress_lookup_hw9;

/**
 * Created by abhishek-karmakar on 3/1/2017.
 */

public class CustomUriBuilder {
    private String method;
    private String meta;

    private String[] methList = { "legislators?", "bills?", "committees?", "amendments?" };

    public CustomUriBuilder(String meth, String meta) {
        this.method = meth;
        this.meta = meta;
    }

    private String combineParam(String param, String value) {
        return param+"="+value;
    }

    String buildUri() {
        String urlHead = "https://congress.api.sunlightfoundation.com/";
        String apikeyParam = "apikey=f2f1a9b078cb4a41b12596f120841842";
        String perPageParam = "per_page";
        String orderParam = "order";
        String chamberParam = "chamber";
        String bioguideParam = "bioguide_id";

        String finalUri = urlHead;

        switch (method) {
            case "legs":
                finalUri += methList[0];
                finalUri += apikeyParam + "&";
                finalUri += combineParam(perPageParam, "all") + "&";
                switch (meta) {
                    case "states":
                        finalUri += combineParam(orderParam, "state__asc,last_name__asc");
                        break;

                    case "house":
                    case "senate":
                        finalUri += combineParam(chamberParam, meta) + "&";
                        finalUri += combineParam(orderParam, "last_name__asc");
                        break;

                    default:
                        // bioguide_id case, separator = ^
                        // details url
                        String targetBid = meta.split(":")[1];
                        finalUri += combineParam(bioguideParam, targetBid);

                        /*
                        // top bills url
                        finalUri += urlHead;
                        finalUri += methList[1];
                        finalUri += apikeyParam + "&";
                        finalUri += combineParam(perPageParam, "5") + "&";
                        finalUri += combineParam("sponsor_id__in", targetBid) + "^";

                        // top comm url
                        finalUri += urlHead;
                        finalUri += methList[2];
                        finalUri += apikeyParam + "&";
                        finalUri += combineParam(perPageParam, "5") + "&";
                        finalUri += combineParam("member_ids", targetBid);
                        */
                }
                break;

            case "bill":
                finalUri += methList[1];
                finalUri += apikeyParam + "&";
                finalUri += combineParam(perPageParam, "50") + "&";
                switch (meta) {
                    case "active":
                        finalUri += combineParam("history.active", "true") + "&";
                        break;

                    case "new":
                        finalUri += combineParam("history.active", "false") + "&";
                        break;
                }

                finalUri += combineParam("order", "introduced_on") + "&";
                finalUri += combineParam("last_version.urls.pdf__exists", "true");
                break;

            case "comm":
                finalUri += methList[2];
                finalUri += apikeyParam + "&";
                finalUri += combineParam(perPageParam, "all");
                break;

        }

        return finalUri;
    }
}
