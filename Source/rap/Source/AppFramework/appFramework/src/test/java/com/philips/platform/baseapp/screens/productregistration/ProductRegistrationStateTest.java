/*
 * Copyright (c) 2015-2018 Koninklijke Philips N.V.
 * All rights reserved.
 */
package com.philips.platform.baseapp.screens.productregistration;

import com.philips.platform.TestActivity;
import com.philips.platform.TestAppFrameworkApplication;
import com.philips.platform.appframework.R;
import com.philips.platform.appframework.flowmanager.base.UIStateData;
import com.philips.platform.appframework.homescreen.HamburgerActivity;
import com.philips.platform.baseapp.screens.utility.Constants;
import com.philips.platform.uappframework.launcher.FragmentLauncher;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestAppFrameworkApplication.class)
public class ProductRegistrationStateTest extends TestCase {
    private ProductRegistrationState productRegistrationState;
    private FragmentLauncher fragmentLauncher;
    private HamburgerActivity hamburgerActivity;
    private ActivityController<TestActivity> activityController;

    @After
    public void tearDown(){
        activityController.pause().stop().destroy();
    }
    @Before
    public void setUp() throws Exception{
        super.setUp();
        productRegistrationState = new ProductRegistrationState();
        UIStateData supportStateData = new UIStateData();
        supportStateData.setFragmentLaunchType(Constants.CLEAR_TILL_HOME);
        productRegistrationState.setUiStateData(supportStateData);

        activityController= Robolectric.buildActivity(TestActivity.class);
        hamburgerActivity=activityController.create().start().get();
        fragmentLauncher = new FragmentLauncher(hamburgerActivity, R.id.frame_container, hamburgerActivity);
    }

    @Test
    public void launchSupportState(){
//        productRegistrationState.init(application);
//        productRegistrationState.navigate(fragmentLauncher);
//        FragmentManager fragmentManager = hamburgerActivity.getSupportFragmentManager();
//        int fragmentCount = fragmentManager.getBackStackEntryCount();
//        assertEquals(1,fragmentCount);
    }
}
