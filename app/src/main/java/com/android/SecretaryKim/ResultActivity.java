package com.android.SecretaryKim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import net.alhazmy13.wordcloud.ColorTemplate;
import net.alhazmy13.wordcloud.WordCloud;
import net.alhazmy13.wordcloud.WordCloudView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ResultActivity extends AppCompatActivity {

    List<WordCloud> list;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        text = intent.getExtras().getString("list");

        generateText();
        WordCloudView wordCloud = (WordCloudView) findViewById(R.id.wordCloud);
        wordCloud.setDataSet(list);
        wordCloud.setSize(400,400);
        wordCloud.setColors(ColorTemplate.MATERIAL_COLORS);
        wordCloud.notifyDataSetChanged();

    }

    private void generateText() {
        String[] data = text.split(" ");
        list = new ArrayList<>();
        ArrayList<String> wordlist = new ArrayList<>();

        for (String s : data) {
            if(wordlist.isEmpty() || !wordlist.contains(s)) {
                wordlist.add(s);
                list.add(new WordCloud(s, 10));
            } else {
                if(wordlist.contains(s)) {
                    Iterator<WordCloud> it = list.iterator();
                    while(it.hasNext()) {
                        if(it.next().getText().equals(s)) {
                            it.next().setWeight(it.next().getWeight() + 3);
                        }
                    }
                }
            }
        }
    }
}