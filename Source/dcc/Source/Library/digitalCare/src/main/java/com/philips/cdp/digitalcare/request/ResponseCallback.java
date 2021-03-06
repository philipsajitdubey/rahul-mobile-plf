/**
 * ResponseCallback is interface for Aysnc task RequestThread class. Once
 * data has been fetched(response received) from server then this will be invoked programmetically.
 *
 * @author : Ritesh.jha@philips.com
 * @since : 16 Dec 2014
 * <p/>
 * Copyright (c) 2016 Philips. All rights reserved.
 */

package com.philips.cdp.digitalcare.request;


/**
 * CallBack interface used for getting the network response acroos the application.
 */
public interface ResponseCallback {
    void onResponseReceived(String response);
}
