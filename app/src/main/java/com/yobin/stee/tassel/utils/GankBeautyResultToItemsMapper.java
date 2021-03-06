// (c)2016 Flipboard Inc, All Rights Reserved.

package com.yobin.stee.tassel.utils;

import com.yobin.stee.tassel.beans.GalleryBeauty;
import com.yobin.stee.tassel.beans.GalleryResult;
import com.yobin.stee.tassel.beans.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.functions.Func1;

public class GankBeautyResultToItemsMapper implements Func1<GalleryResult, List<Item>> {
    private static GankBeautyResultToItemsMapper INSTANCE = new GankBeautyResultToItemsMapper();

    private GankBeautyResultToItemsMapper() {
    }

    public static GankBeautyResultToItemsMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Item> call(GalleryResult galleryResult) {
        List<GalleryBeauty> gankBeauties = galleryResult.beautyList;
        List<Item> items = new ArrayList<>(gankBeauties.size());

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        for (GalleryBeauty gankBeauty : gankBeauties) {
            Item item = new Item();
            try {
                Date date = inputFormat.parse(gankBeauty.createdAt);
                item.description = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                item.description = "unknown date";
            }
            item.imageUrl = gankBeauty.url;
            items.add(item);
        }
        return items;
    }
}
