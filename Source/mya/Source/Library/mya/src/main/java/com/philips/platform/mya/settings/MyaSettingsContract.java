/*
 * Copyright (c) Koninklijke Philips N.V. 2017
 * All rights are reserved. Reproduction or dissemination in whole or in part
 * is prohibited without the prior written consent of the copyright holder.
 */
package com.philips.platform.mya.settings;

import android.os.Bundle;

import com.philips.platform.appinfra.AppInfraInterface;
import com.philips.platform.appinfra.appconfiguration.AppConfigurationInterface;
import com.philips.platform.mya.base.MyaBaseView;
import com.philips.platform.mya.base.MyaPresenterInterface;
import com.philips.platform.mya.interfaces.MyaListener;
import com.philips.platform.uappframework.launcher.FragmentLauncher;

import java.util.Map;

interface MyaSettingsContract {

    interface View extends MyaBaseView {
        void showSettingsItems(Map<String, SettingsModel> dataModelLinkedHashMap);
        void setLinkUrl(String url);

        void onLogOutClick(MyaListener.MyaLogoutListener myaLogoutListener);
    }

    interface Presenter extends MyaPresenterInterface<View> {

        void getSettingItems(AppInfraInterface appInfra, AppConfigurationInterface.AppConfigurationError error);

        void onClickRecyclerItem(String key, SettingsModel settingsModel);

        void logOut(Bundle bundle);

        boolean handleOnClickSettingsItem(String key, FragmentLauncher fragmentLauncher);
    }
}
