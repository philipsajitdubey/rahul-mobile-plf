/* Copyright (c) Koninklijke Philips N.V., 2016
 * All rights are reserved. Reproduction or dissemination
 * in whole or in part is prohibited without the prior written
 * consent of the copyright holder.
 */

package com.philips.platform.baseapp.screens.inapppurchase;

public class IAPOrderHistoryState extends IAPState{
    @Override
    public void updateDataModel() {
        setLaunchType(IAPState.IAP_PURCHASE_HISTORY_VIEW);
    }
}
