package com.philips.cdp.prxclient.datamodels.Disclaimer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Data {


    public Disclaimers getDisclaimers() {
        return disclaimers;
    }

    public void setDisclaimers(Disclaimers disclaimers) {
        this.disclaimers = disclaimers;
    }

    @SerializedName("disclaimers")
    @Expose
    private Disclaimers disclaimers;


    public Data() {
    }

    public Data(Disclaimers disclaimers) {
        this.disclaimers = disclaimers;
    }


}
