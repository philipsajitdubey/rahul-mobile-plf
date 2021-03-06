/*
 * Copyright (c) 2015-2018 Koninklijke Philips N.V.
 * All rights reserved.
 */
package com.philips.platform;

import android.app.Dialog;

import com.philips.platform.appframework.R;
import com.philips.platform.baseapp.base.AbstractAppFrameworkBaseActivity;
import com.philips.platform.uid.view.widget.Label;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;

import static org.junit.Assert.assertEquals;

/**
 * Created by philips on 8/29/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = TestAppFrameworkApplication.class)
public class AppFrameworkBaseActivityTest {

    private AbstractAppFrameworkBaseActivity testActivity;
    private ActivityController<TestActivity> activityController;

    @Before
    public void setUp() {
        activityController = Robolectric.buildActivity(TestActivity.class);
        testActivity = activityController.create().start().get();
    }

    @Test
    public void testOverlayLaunch() {
        testActivity.showOverlayDialog(R.string.RA_DLS_Help_Philips_Shop, R.mipmap.philips_shop_overlay, null);
        Dialog dialog = ShadowDialog.getLatestDialog();
        Label description = (Label) dialog.findViewById(R.id.textView_overlay_subText);
        assertEquals(testActivity.getString(R.string.RA_DLS_Help_Philips_Shop), description.getText().toString());
    }

    @After
    public void tearDown() {
        testActivity = null;
        activityController = null;
    }
}
