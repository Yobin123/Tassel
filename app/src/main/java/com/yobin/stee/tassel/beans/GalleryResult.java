package com.yobin.stee.tassel.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yobin_he on 2017/2/15.
 */

public class GalleryResult {
    public boolean error;
    public @SerializedName("results")
    List<GalleryBeauty> beautyList;
}
