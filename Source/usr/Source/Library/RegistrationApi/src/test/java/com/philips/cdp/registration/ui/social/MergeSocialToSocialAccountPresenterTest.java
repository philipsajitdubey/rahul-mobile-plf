/*
 * Copyright (c) 2015-2018 Koninklijke Philips N.V.
 * All rights reserved.
 */

package com.philips.cdp.registration.ui.social;

import com.philips.cdp.registration.User;
import com.philips.cdp.registration.configuration.RegistrationConfiguration;
import com.philips.cdp.registration.dao.UserRegistrationFailureInfo;
import com.philips.cdp.registration.injection.RegistrationComponent;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.verify;

/**
 * Created by philips on 11/22/17.
 */
@RunWith(RobolectricTestRunner.class)
public class MergeSocialToSocialAccountPresenterTest {

    @Mock
    private User userMock;

    @Mock
    private RegistrationComponent mockRegistrationComponent;

    @Mock
    private UserRegistrationFailureInfo userRegistrationFailureInfoMock;

    @Mock
    private MergeSocialToSocialAccountContract mergeSocialToSocialAccountContract;

    private MergeSocialToSocialAccountPresenter mergeSocialToSocialAccountPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        RegistrationConfiguration.getInstance().setComponent(mockRegistrationComponent);

        mergeSocialToSocialAccountPresenter = new MergeSocialToSocialAccountPresenter(mergeSocialToSocialAccountContract,userMock);
    }

    @Test
    public void onNetWorkStateReceived() throws Exception {
        mergeSocialToSocialAccountPresenter.onNetWorkStateReceived(true);
        verify(mergeSocialToSocialAccountContract).connectionStatus(true);
        verify(mergeSocialToSocialAccountContract).mergeStatus(true);
    }

    @Test
    public void onLoginSuccess() throws Exception {
        mergeSocialToSocialAccountPresenter.onLoginSuccess();
        verify(mergeSocialToSocialAccountContract).mergeSuccess();
    }

    @Test
    public void onLoginFailedWithError() throws Exception {
        Mockito.when(userRegistrationFailureInfoMock.getErrorCode()).thenReturn(12);
        mergeSocialToSocialAccountPresenter.onLoginFailedWithError(userRegistrationFailureInfoMock);
        verify(mergeSocialToSocialAccountContract).mergeFailure(userRegistrationFailureInfoMock);
    }


    @Test
    public void onLoginFailedWithTwoStepError() throws Exception {
        JSONObject jsonObject=new JSONObject();
        mergeSocialToSocialAccountPresenter.onLoginFailedWithTwoStepError(jsonObject,"token");
        verify(mergeSocialToSocialAccountContract).mergeFailureIgnored();
    }

    @Test
    public void onLoginFailedWithMergeFlowError() throws Exception {
        mergeSocialToSocialAccountPresenter.onLoginFailedWithMergeFlowError("token","existingprovider","identityProvider",
                "nameLocalized","existingNameLocalized","email");
        verify( mergeSocialToSocialAccountContract).mergeFailureIgnored();
    }

    @Test
    public void onContinueSocialProviderLoginSuccess() throws Exception {
        mergeSocialToSocialAccountPresenter.onContinueSocialProviderLoginSuccess();
        verify(mergeSocialToSocialAccountContract).mergeSuccess();
    }

    @Test
    public void onContinueSocialProviderLoginFailure() throws Exception {
        mergeSocialToSocialAccountPresenter.onContinueSocialProviderLoginFailure(userRegistrationFailureInfoMock);
        verify(mergeSocialToSocialAccountContract).mergeFailureIgnored();
    }

    @Test
    public void logout() throws Exception {
        mergeSocialToSocialAccountPresenter.logout();
        verify(userMock).logout(null);
    }

    @Test
    public void loginUserUsingSocialProvider() throws Exception {
        mergeSocialToSocialAccountPresenter.loginUserUsingSocialProvider("provider","token");
    }


    @Test
    public void shouldReturnEmailIfValid_OnCallGetLoginWithDetails() throws Exception {
        Mockito.when(userMock.getEmail()).thenReturn("philips@gmail.com");
        Assert.assertSame("philips@gmail.com",mergeSocialToSocialAccountPresenter.getLoginWithDetails());
    }

    @Test
    public void shouldReturnMobileIfInvalidEmail_OnCallGetLoginWithDetails() throws Exception {
        Mockito.when(userMock.getEmail()).thenReturn("philips");
        Mockito.when(userMock.getMobile()).thenReturn("8904291902");
        Assert.assertSame("8904291902",mergeSocialToSocialAccountPresenter.getLoginWithDetails());
    }

}