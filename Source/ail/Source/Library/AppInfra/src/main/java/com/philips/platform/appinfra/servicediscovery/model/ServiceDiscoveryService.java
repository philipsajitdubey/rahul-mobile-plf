package com.philips.platform.appinfra.servicediscovery.model;


import java.util.Map;

/**
 * The model class of ServiceDiscoveryService.
 */
@SuppressWarnings("unchecked")
public class ServiceDiscoveryService {

    private String mLocale;
    private String mConfigUrl;
    private String mError;
    private Map<?, ?> kMap;
    private AIKMResponse.KError kError;

    public void init(String localeParam, String configUrlParam) {
        mLocale = localeParam;
        mConfigUrl = configUrlParam;
    }

    public String getmError() {
        return mError;
    }

    public void setmError(String mError) {
        this.mError = mError;
    }

    public String getLocale() {
        return mLocale;
    }

    public String getConfigUrls() {
        return mConfigUrl;
    }

    public void setConfigUrl(String mConfigUrl) {
        this.mConfigUrl = mConfigUrl;
    }

    public Map<?, ?> getKMap() {
        return kMap;
    }

    public void setKMap(Map<?, ?> kMap) {
        this.kMap = kMap;
    }

    public AIKMResponse.KError getKError() {
        return kError;
    }

    public void setKError(AIKMResponse.KError kError) {
        this.kError = kError;
    }
}
