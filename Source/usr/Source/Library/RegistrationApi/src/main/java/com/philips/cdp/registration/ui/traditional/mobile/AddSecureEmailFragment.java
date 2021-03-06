package com.philips.cdp.registration.ui.traditional.mobile;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.philips.cdp.registration.R;
import com.philips.cdp.registration.R2;
import com.philips.cdp.registration.ui.customviews.XRegError;
import com.philips.cdp.registration.ui.traditional.AccountActivationFragment;
import com.philips.cdp.registration.ui.traditional.RegistrationBaseFragment;
import com.philips.cdp.registration.ui.utils.RLog;
import com.philips.cdp.registration.ui.utils.RegConstants;
import com.philips.cdp.registration.ui.utils.RegPreferenceUtility;
import com.philips.platform.uid.view.widget.InputValidationLayout;
import com.philips.platform.uid.view.widget.ProgressBarButton;
import com.philips.platform.uid.view.widget.ValidationEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.philips.cdp.registration.app.tagging.AppTagingConstants.REGISTRATION_ACTIVATION_SMS;


public class AddSecureEmailFragment extends RegistrationBaseFragment implements AddSecureEmailContract {

    private String TAG = "AddSecureEmailFragment";

    @BindView(R2.id.btn_reg_secure_data_email)
    ProgressBarButton addRecoveryEmailButton;

    @BindView(R2.id.btn_reg_secure_data_email_later)
    Button maybeLaterButton;

    @BindView(R2.id.rl_reg_securedata_email_field)
    ValidationEditText recoveryEmail;

   @BindView(R2.id.rl_reg_securedata_email_field_inputValidation)
    InputValidationLayout rl_reg_securedata_email_field_inputValidation;

    @BindView(R2.id.reg_error_msg)
    XRegError recoveryErrorTextView;

    private AddSecureEmailPresenter addSecureEmailPresenter;

    @BindView(R2.id.ll_reg_root_container)
    LinearLayout regRootContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        registerInlineNotificationListener(this);
        RLog.i(TAG,"Screen name is "+ TAG);
        addSecureEmailPresenter = new AddSecureEmailPresenter(this);
        View view = inflater.inflate(R.layout.reg_fragment_secure_email, container, false);
        ButterKnife.bind(this, view);
        trackActionStatus(REGISTRATION_ACTIVATION_SMS, "", "");
        setUpRecoveryEmail();
        return view;
    }

    private void setUpRecoveryEmail() {
        recoveryEmail.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    @Override
    protected void setViewParams(Configuration config, int width) {
       // applyParams(config,regRootContainer,width);
    }

    @Override
    protected void handleOrientation(View view) {
        handleOrientationOnView(view);
    }

    @Override
    public int getTitleResourceId() {
        return R.string.USR_DLS_URCreateAccount_NavTitle;
    }


    @OnClick(R2.id.btn_reg_secure_data_email)
    public void addEmailButtonClicked() {
        RLog.i(TAG,TAG+".addEmailButton clicked");
        recoveryErrorTextView.setVisibility(GONE);
        addSecureEmailPresenter.addEmailClicked(recoveryEmail.getText().toString());
    }

    @OnClick(R2.id.btn_reg_secure_data_email_later)
    public void maybeLaterButtonClicked() {
        RLog.i(TAG,TAG+".maybeLaterButton clicked");
        addSecureEmailPresenter.maybeLaterClicked();
    }

    @Override
    public void registrationComplete() {
        getRegistrationFragment().userRegistrationComplete();
    }

    @Override
    public void showInvalidEmailError() {
        rl_reg_securedata_email_field_inputValidation.setErrorMessage(
                getString(R.string.USR_InvalidEmailAdddress_ErrorMsg));
        rl_reg_securedata_email_field_inputValidation.showError();
    }

    @Override
    public void onAddRecoveryEmailSuccess() {
        getRegistrationFragment().addFragment(new AccountActivationFragment());
    }

    @Override
    public void onAddRecoveryEmailFailure(String error) {
        recoveryErrorTextView.setError(error);
        recoveryErrorTextView.setVisibility(VISIBLE);
    }

    @Override
    public void enableButtons() {
        addRecoveryEmailButton.setEnabled(true);
        maybeLaterButton.setEnabled(true);

    }

    @Override
    public void disableButtons() {
        addRecoveryEmailButton.setEnabled(false);
        maybeLaterButton.setEnabled(false);
    }

    @Override
    public void showNetworkUnavailableError() {
        recoveryErrorTextView.setError(getResources().getString(R.string.USR_JanRain_Server_ConnectionLost_ErrorMsg));
        recoveryErrorTextView.setVisibility(VISIBLE);
    }

    @Override
    public void hideError() {
        recoveryErrorTextView.setError(null);
        recoveryErrorTextView.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        addSecureEmailPresenter.registerNetworkListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addSecureEmailPresenter.cleanUp();
    }

    @Override
    public void showProgress() {
        addRecoveryEmailButton.showProgressIndicator();
        maybeLaterButton.setEnabled(false);
    }

    @Override
    public void hideProgress() {
        addRecoveryEmailButton.hideProgressIndicator();
        maybeLaterButton.setEnabled(true);
    }

    @Override
    public void storePreference(String emailOrMobileNumber) {
        RegPreferenceUtility.storePreference(
                getRegistrationFragment().getContext(), RegConstants.TERMS_N_CONDITIONS_ACCEPTED,emailOrMobileNumber);
    }

    @Override
    public void notificationInlineMsg(String msg) {
        recoveryErrorTextView.setError(msg);
    }
}
