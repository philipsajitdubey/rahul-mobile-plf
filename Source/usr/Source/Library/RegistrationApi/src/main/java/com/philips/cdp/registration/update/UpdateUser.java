package com.philips.cdp.registration.update;

import com.janrain.android.capture.Capture;
import com.janrain.android.capture.CaptureApiError;
import com.janrain.android.capture.CaptureRecord;
import com.philips.cdp.registration.errors.ErrorCodes;
import com.philips.cdp.registration.ui.utils.RLog;

import org.json.JSONObject;

public class UpdateUser implements Capture.CaptureApiRequestCallback {

    private String TAG = "UpdateUser";

    private UpdateUserListener mUpdateUserListener;

    public interface UpdateUserListener {

        void onUserUpdateSuccess();

        void onUserUpdateFailed(int error);
    }

    public void update(JSONObject updatedUserData, JSONObject userData,
                       UpdateUserListener updateUserListener) {
        mUpdateUserListener = updateUserListener;
        if (null != updatedUserData && null != userData) {
            try {
                RLog.d(TAG, "update:updating User  ");
                ((CaptureRecord) updatedUserData).synchronize(this, userData);
            } catch (Capture.InvalidApidChangeException e) {
                RLog.e(TAG, "update: Exception occurred while updating User Info "+ e.getMessage());
                mUpdateUserListener.onUserUpdateFailed(ErrorCodes.UNKNOWN_ERROR);
            }
        } else {
            RLog.e(TAG, "update: updatedUserData NULL ");
            mUpdateUserListener.onUserUpdateFailed(ErrorCodes.UNKNOWN_ERROR);
        }
    }

    @Override
    public void onSuccess() {
        RLog.d(TAG, "onSuccess  ");
        mUpdateUserListener.onUserUpdateSuccess();
    }

    @Override
    public void onFailure(CaptureApiError e) {
        RLog.e(TAG, "onFailure updating User Info " + e.raw_response);
        mUpdateUserListener.onUserUpdateFailed(e.code);
    }
}
