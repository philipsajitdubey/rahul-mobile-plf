/**
 * (C) Koninklijke Philips N.V., 2015.
 * All rights reserved.
 */
package com.philips.cdp.di.iap.model;

import com.android.volley.Request;
import com.philips.cdp.di.iap.store.StoreListener;
import com.philips.cdp.di.iap.utils.IAPLog;

import java.util.Map;

public class DeleteCartRequest extends AbstractModel {


    public DeleteCartRequest(StoreListener store, Map<String, String> query, DataLoadListener listener) {
        super(store, query, listener);
    }

    @Override
    public Object parseResponse(Object response) {
        return null;
    }

    @Override
    public int getMethod() {
        IAPLog.d(IAPLog.LOG, "DELETE");
        return Request.Method.DELETE;
    }

    @Override
    public Map<String, String> requestBody() {
        return null;
    }

    @Override
    public String getUrl() {
        IAPLog.d(IAPLog.LOG, "Request URL = " + store.getDeleteCartUrl());
        return store.getDeleteCartUrl();
    }
}
