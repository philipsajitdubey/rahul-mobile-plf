
/* Copyright (c) Koninklijke Philips N.V., 2018
 * All rights are reserved. Reproduction or dissemination
 * in whole or in part is prohibited without the prior written
 * consent of the copyright holder.
 */
package com.philips.cdp.di.iap.response.carts;

public class PromotionEntity {

    private String code;
    private String description;
    private boolean enabled;
    private String endDate;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getEndDate() {
        return endDate;
    }

}
