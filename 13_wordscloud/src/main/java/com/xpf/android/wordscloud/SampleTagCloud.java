package com.xpf.android.wordscloud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

import java.util.ArrayList;
import java.util.List;

public class SampleTagCloud extends AppCompatActivity {

    private TagCloudView mTagCloudView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        List<Tag> myTagList = createTags();

        mTagCloudView = new TagCloudView(this, width, height, myTagList);
        setContentView(mTagCloudView);
        mTagCloudView.requestFocus();
        mTagCloudView.setFocusableInTouchMode(true);
    }

    private List<Tag> createTags() {
        List<Tag> tempList = new ArrayList<>();
        tempList.add(new Tag("Tencent", 8, "Tencent"));
        tempList.add(new Tag("Alibaba", 6, "Alibaba"));
        tempList.add(new Tag("Baidu", 7, "Baidu"));
        tempList.add(new Tag("Google", 7, "Google"));
        tempList.add(new Tag("Yahoo", 3, "Yahoo"));
        tempList.add(new Tag("CNN", 4, "CNN"));
        tempList.add(new Tag("MSNBC", 5, "MSNBC"));
        tempList.add(new Tag("CNBC", 5, "CNBC"));
        tempList.add(new Tag("Facebook", 7, "Facebook"));
        tempList.add(new Tag("Youtube", 3, "Youtube"));
        tempList.add(new Tag("BlogSpot", 5, "BlogSpot"));
        tempList.add(new Tag("Bing", 3, "Bing"));
        tempList.add(new Tag("Wikipedia", 8, "Wikipedia"));
        tempList.add(new Tag("Twitter", 5, "Twitter"));
        tempList.add(new Tag("Msn", 1, "Msn"));
        tempList.add(new Tag("Amazon", 3, "Amazon"));
        tempList.add(new Tag("Ebay", 7, "Ebay"));
        tempList.add(new Tag("LinkedIn", 5, "LinkedIn"));
        tempList.add(new Tag("Live", 7, "Live"));
        tempList.add(new Tag("Microsoft", 3, "Microsoft"));
        tempList.add(new Tag("Flicker", 1, "Flicker"));
        tempList.add(new Tag("Apple", 5, "Apple"));
        tempList.add(new Tag("Paypal", 5, "Paypal"));
        tempList.add(new Tag("Craigslist", 7, "Craigslist"));
        tempList.add(new Tag("Imdb", 2, "Imdb"));
        tempList.add(new Tag("Ask", 4, "Ask"));
        tempList.add(new Tag("Weibo", 1, "Weibo"));
        tempList.add(new Tag("Tagin!", 8, "Tagin"));
        tempList.add(new Tag("Shiftehfar", 8, "Shiftehfar"));
        tempList.add(new Tag("Soso", 5, "Soso"));
        tempList.add(new Tag("XVideos", 3, "XVideos"));
        tempList.add(new Tag("BBC", 5, "BBC"));
        return tempList;
    }
}