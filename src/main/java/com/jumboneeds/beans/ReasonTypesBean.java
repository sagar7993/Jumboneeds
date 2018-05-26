package com.jumboneeds.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 19-Oct-16.
 */
public class ReasonTypesBean extends StatusBean {

    private List<ReasonTypeBean> reasonTypeBeans = new ArrayList<>();

    public ReasonTypesBean(){

    }

    public List<ReasonTypeBean> getReasonTypeBeans() {
        return reasonTypeBeans;
    }

    public void setReasonTypeBeans(List<ReasonTypeBean> reasonTypeBeans) {
        this.reasonTypeBeans = reasonTypeBeans;
    }
}
