/*
 * Copyright (c) 2017 Koninklijke Philips N.V.
 * All rights are reserved. Reproduction or dissemination
 * in whole or in part is prohibited without the prior written
 * consent of the copyright holder.
 */

package com.philips.cdp.registration.myaccount;

import android.text.TextUtils;
import android.view.View;

import com.philips.cdp.registration.ui.utils.RLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UserDetailPresenter extends MyaBasePresenter<MyaDetailContract.View> implements MyaDetailContract.Presenter {

    private MyaDetailContract.View view;

    private static String TAG = "UserDetailPresenter";

    UserDetailPresenter(MyaDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void setUserDetails(UserDataModelProvider userDataModelProvider) {
        if (userDataModelProvider != null) {
            UserDataModel userDataModel = (UserDataModel) userDataModelProvider.getData(DataModelType.USER);
            setUserName(userDataModel);
            view.setEmail(userDataModel.getEmail());
            view.setMobileNumber(userDataModel.getMobileNumber());
            if(userDataModel.getGender()!=null)
            view.setGender(userDataModel.getGender().toString());
            view.setDateOfBirth(userDataModel.getBirthday());
            view.setAddress(userDataModel.getAddress());
        }else {
            RLog.d(TAG,"setUserDetails : userDataModelProvider is null");
        }
    }

    private void setUserName(UserDataModel userDataModel) {
        RLog.d(TAG,"setUserName : is called");
        String givenName = userDataModel.getGivenName();
        String familyName = userDataModel.getFamilyName();
        if (!TextUtils.isEmpty(givenName) && !TextUtils.isEmpty(familyName) && !familyName.equalsIgnoreCase("null")) {
            view.setUserName(givenName.concat(" ").concat(familyName));
            view.setCircleText(String.valueOf(givenName.charAt(0)).toUpperCase().concat(String.valueOf(familyName.charAt(0))).toUpperCase());
        } else if (!TextUtils.isEmpty(givenName)) {
            view.setUserName(givenName);
            view.setCircleText(printFirstCharacter(givenName));
        }
    }


    private String printFirstCharacter(String nameString) {
        RLog.d(TAG,"printFirstCharacter : is called");
        StringBuilder finalName = new StringBuilder();
        Pattern pattern = Pattern.compile("\\b[a-zA-z[$&+,:;=?@#|'<>.-^*()%!]0-9]");
        Matcher matcher = pattern.matcher(nameString);
        while (matcher.find()) {
            String matchString = matcher.group();
            finalName.append(matchString);

        }
        if (finalName.toString().length() == 1) {
            return nameString.length() == 1 ? nameString : nameString.substring(0, 2).toUpperCase();
        } else if (finalName.toString().length() > 2) {
            return finalName.substring(0, 2).toUpperCase();
        }
        return finalName.toString().toUpperCase();
    }

    void makeVisible(View... views){
        for(View view:views){
            view.setVisibility(View.VISIBLE);
        }
    }

}
