/**
 * (C) Koninklijke Philips N.V., 2015.
 * All rights reserved.
 */
package com.philips.cdp.di.iap.iapHandler;

import com.philips.cdp.di.iap.integration.IAPInterface;
import com.philips.cdp.di.iap.integration.IAPListener;

public class LocalHandler extends IAPInterface implements IAPExposedAPI {

    @Override
    public void getProductCartCount(final IAPListener iapListener) {
        iapListener.onGetCartCount(-1);
    }

    @Override
    public void getCompleteProductList(IAPListener iapListener) {
    }
}