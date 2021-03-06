/*
 * Copyright (c) 2015-2018 Koninklijke Philips N.V.
 * All rights reserved.
 */

package com.philips.cdp.registration.errors;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class URErrorTest {

    URError urError;

    NetworkErrorEnum networkErrorEnum;

    @Mock
    Context mContext;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        urError = new URError(mContext);
    }

    @Test
    public void shouldTestNetworkTypeError() {
        urError.getLocalizedError(ErrorType.NETWOK, ErrorCodes.NETWORK_ERROR);
    }

    @Test
    public void shouldTestNetworkTypeError_ForNoNetwok() {
        urError.getLocalizedError(ErrorType.NETWOK, ErrorCodes.NO_NETWORK);
    }

    @Test(expected = NullPointerException.class)
    public void shouldTestHSDPError() {
        urError.getLocalizedError(ErrorType.HSDP, ErrorCodes.HSDP_INPUT_ERROR_1114);
    }

    @Test(expected = NullPointerException.class)
    public void shouldTestHSDPError_WithDefault() {
        urError.getLocalizedError(ErrorType.HSDP, ErrorCodes.HSDP_INPUT_ERROR_1112);
    }

    @Test
    public void shouldTestURXError() {
        urError.getLocalizedError(ErrorType.URX, ErrorCodes.URX_INVALID_VERIFICATION_CODE);
    }

    @Test
    public void shouldTestURXError_WithDefault() {
        urError.getLocalizedError(ErrorType.URX, ErrorCodes.UNKNOWN_ERROR);
    }

    @Test
    public void shouldTestJANRAINError() {
        urError.getLocalizedError(ErrorType.JANRAIN, ErrorCodes.JANRAIN_AUTHORIZATION_CODE_EXPIRED);
    }

    @Test
    public void shouldTestJANRAINError_WithDefault() {
        urError.getLocalizedError(ErrorType.UNKNOWN, ErrorCodes.JANRAIN_AUTHORIZATION_CODE_EXPIRED);
    }
    

}