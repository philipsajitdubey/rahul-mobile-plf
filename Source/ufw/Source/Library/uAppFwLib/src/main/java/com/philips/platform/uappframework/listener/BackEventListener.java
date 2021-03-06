/* Copyright (c) Koninklijke Philips N.V., 2016
 * All rights are reserved. Reproduction or dissemination
 * in whole or in part is prohibited without the prior written
 * consent of the copyright holder.
*/
package com.philips.platform.uappframework.listener;


import java.io.Serializable;

/**
 * This interface needs to be implemented by uApp fragments for handling back key events.
 * Application is expected to call this API for each uApp if it is launched as fragment on back event by checking
 * whether fragment is an instance of this interface.
 * @since 1.0.0
 */

public interface BackEventListener extends Serializable {
    /**
     * Check if UApp us handling back key event.
     * @return true if uApp is handling back key event else return false.
     * @since 1.0.0
     */
    boolean handleBackEvent();
}
