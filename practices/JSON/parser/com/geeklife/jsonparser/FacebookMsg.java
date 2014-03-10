package com.geeklife.jsonparser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andysong on 14-3-10.
 */
public class FacebookMsg implements Serializable {

    private String id;
    private From from;
    private String message;
    private List<Action> actions;
    private String type;
    private String created_time;
    private String updated_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Action> getActions() {
        if (actions == null) {
            actions = new ArrayList<Action>();
        }
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }
}
