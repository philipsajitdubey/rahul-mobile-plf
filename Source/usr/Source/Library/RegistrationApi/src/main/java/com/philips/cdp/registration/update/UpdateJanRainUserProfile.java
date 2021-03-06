package com.philips.cdp.registration.update;

import com.janrain.android.Jump;
import com.janrain.android.capture.Capture;
import com.janrain.android.capture.Capture.CaptureApiRequestCallback;
import com.janrain.android.capture.CaptureApiError;
import com.janrain.android.capture.CaptureRecord;
import com.philips.cdp.registration.ui.utils.RLog;

import org.json.JSONException;

import io.reactivex.Completable;

public class UpdateJanRainUserProfile implements UpdateUserProfile {

    private String TAG = "UpdateJanRainUserProfile";

    private static final String JANRAIN_UPDATE_EMAIL_KEY = "email";
    private static final String EDIT_PROFILE_FORM_NAME = "editProfileForm";

    @Override
    public Completable updateUserEmail(String emailId) {
        RLog.d(TAG, "updateUserEmail");
        return Completable.create(emitter -> {
            CaptureApiRequestCallback callback = new CaptureApiRequestCallback() {
                @Override
                public void onSuccess() {
                    RLog.d(TAG, "updateUserEmail : onSuccess");
                    emitter.onComplete();
                }

                @Override
                public void onFailure(CaptureApiError e) {
                    RLog.e(TAG, "updateUserEmail : onFailure : error " + e.raw_response);
                    emitter.onError(new Throwable(e.error_description));
                }
            };
            updateUserEmail(emailId, callback);
        });
    }

    void updateUserEmail(String emailId, CaptureApiRequestCallback captureApiRequestCallback) {
        CaptureRecord currentUser = Jump.getSignedInUser();
        try {
            RLog.d(TAG, "updateUserEmail : initiated");
            currentUser.put(JANRAIN_UPDATE_EMAIL_KEY, emailId);
            Capture.updateUserProfile(currentUser, EDIT_PROFILE_FORM_NAME, captureApiRequestCallback);
        } catch (JSONException e) {
            RLog.e(TAG, "updateUserEmail : Exception while updating User Email with provided email" + e.getMessage());
        }
    }
}
