package com.zhaochunqi.android.gankio.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 24/11/2016.
 */

public class Content {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("desc")
    @Expose
    public String desc;
    @SerializedName("publishedAt")
    @Expose
    public String publishedAt;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("used")
    @Expose
    public Boolean used;
    @SerializedName("who")
    @Expose
    public String who;
    @SerializedName("images")
    @Expose
    public List<String> images = new ArrayList<String>();
}
