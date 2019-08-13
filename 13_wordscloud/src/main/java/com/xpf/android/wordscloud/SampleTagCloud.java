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
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("Tencent", 8, "www.qq.com"));
        tagList.add(new Tag("Alibaba", 6, "www.alipay.com"));
        tagList.add(new Tag("Baidu", 7, "www.baidu.com"));
        tagList.add(new Tag("Google", 7, "www.google.com"));
        tagList.add(new Tag("Yahoo", 3, "www.yahoo.com"));
        tagList.add(new Tag("CNN", 4, "www.cnn.com"));
        tagList.add(new Tag("MSNBC", 5, "www.msnbc.com"));
        tagList.add(new Tag("CNBC", 5, "www.CNBC.com"));
        tagList.add(new Tag("Facebook", 7, "www.facebook.com"));
        tagList.add(new Tag("Youtube", 3, "www.youtube.com"));
        tagList.add(new Tag("BlogSpot", 5, "www.blogspot.com"));
        tagList.add(new Tag("Bing", 3, "www.bing.com"));
        tagList.add(new Tag("Wikipedia", 8, "www.wikipedia.com"));
        tagList.add(new Tag("Twitter", 5, "www.twitter.com"));
        tagList.add(new Tag("Msn", 1, "www.msn.com"));
        tagList.add(new Tag("Amazon", 3, "www.amazon.com"));
        tagList.add(new Tag("Ebay", 7, "www.ebay.com"));
        tagList.add(new Tag("LinkedIn", 5, "www.linkedin.com"));
        tagList.add(new Tag("Live", 7, "www.live.com"));
        tagList.add(new Tag("Microsoft", 3, "www.microsoft.com"));
        tagList.add(new Tag("Flicker", 1, "www.flicker.com"));
        tagList.add(new Tag("Apple", 5, "www.apple.com"));
        tagList.add(new Tag("Paypal", 5, "www.paypal.com"));
        tagList.add(new Tag("Craigslist", 7, "www.craigslist.com"));
        tagList.add(new Tag("Imdb", 2, "www.imdb.com"));
        tagList.add(new Tag("Ask", 4, "www.ask.com"));
        tagList.add(new Tag("Weibo", 1, "www.weibo.com"));
        tagList.add(new Tag("Tagin!", 8, "http://scyp.idrc.ocad.ca/projects/tagin"));
        tagList.add(new Tag("Shiftehfar", 8, "www.shiftehfar.org"));
        tagList.add(new Tag("Soso", 5, "www.google.com"));
        tagList.add(new Tag("XVideos", 3, "www.xvideos.com"));
        tagList.add(new Tag("BBC", 5, "www.bbc.co.uk"));
        return tagList;
    }
}