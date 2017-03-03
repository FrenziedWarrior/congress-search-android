package edu.usc.a_karmakar.congress_lookup_hw9;

/**
 * Created by abhishek-karmakar on 3/1/2017.
 */

public class CustomUriBuilder {
    private String method;
    private String meta;

    private String[] methList = { "legislators?", "bills?", "committees?", "amendments?" };
    private String urlHead = "https://congress.api.sunlightfoundation.com/";
    private String apikeyParam = "apikey=f2f1a9b078cb4a41b12596f120841842";
    private String perPageParam = "per_page";
    private String orderParam = "order";
    private String chamberParam = "chamber";
    private String bioguideParam = "bioguide_id";

    public CustomUriBuilder(String meth, String meta) {
        this.method = meth;
        this.meta = meta;
    }

    private String combineParam(String param, String value) {
        return param+"="+value;
    }

    String buildUri() {
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

                }
                break;

            case "bill":
                finalUri += methList[1];
                break;

            case "comm":
                finalUri += methList[2];
                break;

        }

        return finalUri;
    }
}

/*
    $bioguideParam = combineParam($bioguideParam, $_GET['bid']);
    array_push($urlComponents, $reqUrl, $perPageParam, $bioguideParam);
    $reqUrl = join("&", $urlComponents);

    $jsonPersonal = file_get_contents($reqUrl);

    $urlComponents = array();
    $sponsorIdParam = combineParam("sponsor_id__in", $_GET['bid']);
    $perPageParam = combineParam("per_page", "5");
    $reqUrl = $urlHead;
    $reqUrl .= "bills?";
    $reqUrl .= $apikeyParam;
    array_push($urlComponents, $reqUrl, $perPageParam, $sponsorIdParam);
    $reqUrl = join("&", $urlComponents);

    $jsonTopBills = file_get_contents($reqUrl);


    $urlComponents = array();
    $memberIdParam = combineParam("member_ids", $_GET['bid']);
    $perPageParam = combineParam("per_page", "5");
    $reqUrl = $urlHead;
    $reqUrl .= "committees?";
    $reqUrl .= $apikeyParam;
    array_push($urlComponents, $reqUrl, $perPageParam, $memberIdParam);
    $reqUrl = join("&", $urlComponents);
    $jsonTopComs = file_get_contents($reqUrl);
    $jsonContents = json_encode(array($jsonPersonal, $jsonTopBills, $jsonTopComs));

*/

