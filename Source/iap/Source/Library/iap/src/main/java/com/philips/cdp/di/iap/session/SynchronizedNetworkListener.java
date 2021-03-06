/**
 * (C) Koninklijke Philips N.V., 2015.
 * All rights reserved.
 */
package com.philips.cdp.di.iap.session;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;


public interface SynchronizedNetworkListener {
    void onSyncRequestSuccess(Response<JSONObject> jsonObjectResponse);

    void onSyncRequestError(VolleyError volleyError);
}
