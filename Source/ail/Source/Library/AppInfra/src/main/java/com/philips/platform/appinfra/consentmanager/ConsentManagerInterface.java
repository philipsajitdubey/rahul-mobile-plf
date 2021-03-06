/* Copyright (c) Koninklijke Philips N.V., 2018
 * All rights are reserved. Reproduction or dissemination
 * in whole or in part is prohibited without the prior written
 * consent of the copyright holder.
 */
package com.philips.platform.appinfra.consentmanager;

import com.philips.platform.pif.chi.ConsentHandlerInterface;
import com.philips.platform.pif.chi.datamodel.ConsentDefinition;

import java.util.List;

public interface ConsentManagerInterface {

    /**
     * Register consent type to the given handler
     *
     * @param consentType             type of the consent
     * @param consentHandlerInterface Handler which handles the implementation for the given consent type
     * @since 1801.0
     */
    void registerHandler(List<String> consentType, ConsentHandlerInterface consentHandlerInterface);

    /**
     * Deregister consent type from the handler
     *
     * @param consentType the type that should be removed from the handler
     * @since 1801.0
     */
    void deregisterHandler(List<String> consentType);

    /**
     * Register Consent Definition to the type
     *
     * @param consentDefinitions given list of consent definitions
     * @since 1801.0
     */
    void registerConsentDefinitions(List<ConsentDefinition> consentDefinitions);

    /**
     * Fetch the consent status of the given consent definition by delegating to the corresponding handler
     *
     * @param consentDefinition Consent Definition for which the status has to be fetched
     * @param callback          The callback that should be invoked after fetch
     * @since 1801.0
     */
    void fetchConsentState(ConsentDefinition consentDefinition, final FetchConsentCallback callback);

    /**
     * Fetch the consents status of the given consent definition by delegating to the corresponding handler
     *
     * @param consentDefinitions Consent Definition for which the status has to be fetched
     * @param callback           The callback that should be invoked after fetch
     * @since 1801.0
     */
    void fetchConsentStates(List<ConsentDefinition> consentDefinitions, final FetchConsentsCallback callback);

    /**
     * Store the consents status of the given consent definition by delegating to the corresponding handler
     *
     * @param consentDefinition Consent Definition for which the status has to be stored
     * @param status            Consent status i.e, active, rejected or Inactive
     * @param callback          The callback that should be invoked after store
     * @since 1801.0
     */
    void storeConsentState(final ConsentDefinition consentDefinition, boolean status, PostConsentCallback callback);

    /**
     * Fetch the consent status of the give type by delegating to the corresponding handler
     *
     * @param type
     * @param callback
     * @since 1801.0
     */
    void fetchConsentTypeState(String type, FetchConsentCallback callback);

    /**
     * Get consent definition for consent type
     *
     * @param consentType
     * @since 1802.0
     */
    ConsentDefinition getConsentDefinitionForType(String consentType);

    /**
     * Register for conset status changes for a given {@link ConsentDefinition}
     * @param consentDefinition {@link ConsentDefinition} to be observed for the changes.
     * @param listener {@link ConsentStatusChangedListener} provides the callback for respective store call.
     * @since 1803.0
     */
    default void addConsentStatusChangedListener(ConsentDefinition consentDefinition, ConsentStatusChangedListener listener) {
    }

    /**
     * Unregister {@link ConsentDefinition} for update changes. Call this has no impact if the {@link ConsentDefinition} was not registered.
     *
     * @param consentDefinition {@link ConsentDefinition} for which no further callbacks are required.
     * @param consentStatusChangedListener {@link ConsentStatusChangedListener} to be unregistered.
     * @since 1803.0
     */
    default void removeConsentStatusChangedListener(ConsentDefinition consentDefinition, ConsentStatusChangedListener consentStatusChangedListener) {
    }
}