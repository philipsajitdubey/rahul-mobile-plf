package com.philips.cdp.di.iap.integration;


import com.philips.platform.uappframework.uappinput.UappLaunchInput;

import java.util.ArrayList;

/**
 * IAPLaunchInput is responsible for initializing the settings required for launching.
 * @since 1.0.0
 */
public class IAPLaunchInput extends UappLaunchInput {
    int mLandingView;
    public IAPFlowInput mIAPFlowInput;
    private IAPListener iapListener;
    private ArrayList<String> mIgnoreRetailers;
    private ArrayList<String> mFirstIgnoreRetailers = new ArrayList<>();
    private String voucherCode;

    /**
     * IAPLaunchInput setIAPFlow method to set the flow of uApp with required inputs
     * @param pLandingView  pass int value from IAPFlows enums
     * @param pIapFlowInput  pass object of IAPFlowInput
     * @param voucherCode pass String value of voucher Code
     * @param pBlackListedRetailer  pass list of retailer which you want to ignore from Retailer list
     * @since 1.0.0
     */
    public void setIAPFlow(int pLandingView, IAPFlowInput pIapFlowInput,String voucherCode, ArrayList<String> pBlackListedRetailer) {
        mLandingView = pLandingView;
        mIAPFlowInput = pIapFlowInput;
        this.voucherCode=voucherCode;
        mIgnoreRetailers = pBlackListedRetailer;
    }

    /**
     * IAPLaunchInput setIAPFlow method to set the flow of uApp with required inputs
     * @param pLandingView  pass int value from IAPFlows enums
     * @param pIapFlowInput  pass object of IAPFlowInput
     * @param voucherCode pass String value of voucher Code
     * @since 1.0.0
     */
    public void setIAPFlow(int pLandingView, IAPFlowInput pIapFlowInput,String voucherCode) {
        mLandingView = pLandingView;
        mIAPFlowInput = pIapFlowInput;
        this.voucherCode=voucherCode;
        mIgnoreRetailers = new ArrayList<>();
    }

    /**
     * Returns ignored or blacklisted retailers
     * @return ignoredRetailerList  pass list of retailer which you want to ignore from Retailer list
     * @since 1.0.0
     */
    public ArrayList<String> getIgnoreRetailers() {
        if (mIgnoreRetailers == null || mIgnoreRetailers.size() == 0) return mIgnoreRetailers;
        for (String str : mIgnoreRetailers) {
            String[] first = str.split(" ");
            String firstNameOfRetailer = first[0];
            mFirstIgnoreRetailers.add(firstNameOfRetailer);
        }
        return mFirstIgnoreRetailers;
    }

    /**
     * Returns IAPListener instance
     * @return iapListener  instance of IAPListener
     * @since 1.0.0
     */
    public IAPListener getIapListener() {
        if (iapListener == null) new RuntimeException("Set IAPListener in your vertical app ");
        return iapListener;
    }

    /**
     * Sets IAPListener instance
     * @param iapListener  instance of IAPListener for callbacks to proposition
     * @since 1.0.0
     */
    public void setIapListener(IAPListener iapListener) {
        this.iapListener = iapListener;
    }


    public String getVoucher(){
        return voucherCode;
    }



    /**
     * This enum is used to set the landing view of IAP
     * @since 1.0.0
     */
    public interface IAPFlows {
        /**
         * To launch product catalog Screen
         * @since 1.0.0
         */
        int IAP_PRODUCT_CATALOG_VIEW = 0;
        /**
         * To launch shopping cart Screen
         * @since 1.0.0
         */
        int IAP_SHOPPING_CART_VIEW = 1;
        /**
         * To launch purchase history Screen
         * @since 1.0.0
         */
        int IAP_PURCHASE_HISTORY_VIEW = 2;
        /**
         * To launch product detail Screen
         * @since 1.0.0
         */
        int IAP_PRODUCT_DETAIL_VIEW = 3;
        /**
         * To launch buy direct Screen
         * @since 1.0.0
         */
        int IAP_BUY_DIRECT_VIEW = 4;
    }
}
