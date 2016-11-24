package com.zhaochunqi.android.gankio.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 24/11/2016.
 */
public class Datas {

    @SerializedName("error")
    @Expose
    public Boolean error;
    @SerializedName("results")
    @Expose
    public List<Content> mContents = new ArrayList<Content>();

}
