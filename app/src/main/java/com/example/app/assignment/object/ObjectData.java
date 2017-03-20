package com.example.app.assignment.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LeHoangHan on 3/16/2017.
 */

public class ObjectData {
    @SerializedName("id")
    private String id;

    @SerializedName("creatorId")
    private String creatorId;

    @SerializedName("startedDate")
    private String startedDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartedDate() {
        return startedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}
