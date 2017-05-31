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

public class PhrasesActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?","tinne oyasse'ne",R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is ...","oyaaset...",R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?","michekses?",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?","eenes'aa?",R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I'm coming.","hee' eenem",R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming.","eenem",R.raw.phrase_im_coming));
        words.add(new Word("Let's go.","yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come here.","enni'nem",R.raw.phrase_come_here));


        WordAdapter adapter = new WordAdapter(this, words,R.color.category_phrases);

        ListView listView =(ListView)findViewById(R.id.list);

        listView.setAdapter(adapter );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get(i);
                if(mediaPlayer != null){
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this,word.getAudioResourceId());
                    mediaPlayer.start();
                }else{
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this,word.getAudioResourceId());
                    mediaPlayer.start();
                }
            }
        });
    }
}
