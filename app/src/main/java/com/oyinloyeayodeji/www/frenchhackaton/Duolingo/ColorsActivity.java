package com.oyinloyeayodeji.www.frenchhackaton.Duolingo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.oyinloyeayodeji.www.frenchhackaton.Objects.Word;
import com.oyinloyeayodeji.www.frenchhackaton.R;
import com.oyinloyeayodeji.www.frenchhackaton.Adapters.WordAdapter;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red","rouge",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("yellow","jaune",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        words.add(new Word("dusty yellow","topiise",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("green","chokokki",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("brown","takaakki",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("gray","topoppi",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("black","kululli",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("white","kelelli",R.drawable.color_white,R.raw.color_white));


        WordAdapter adapter = new WordAdapter(this, words,R.color.category_phrases);

        ListView listView =(ListView)findViewById(R.id.list);

        listView.setAdapter(adapter );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get(i);
                if(mediaPlayer != null){
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(ColorsActivity.this,word.getAudioResourceId());
                    mediaPlayer.start();
                }else{
                    mediaPlayer = MediaPlayer.create(ColorsActivity.this,word.getAudioResourceId());
                    mediaPlayer.start();
                }

            }
        });
    }
}
