package com.geeklife.jsonparser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andysong on 14-3-10.
 */
public class FacebookMsgContainer {
    private List<FacebookMsg> data;


    public List<FacebookMsg> getData() {
        if (data == null) {
            data = new ArrayList<FacebookMsg>();
        }
        return data;
    }

    public void setData(List<FacebookMsg> data) {
        this.data = data;
    }
}
