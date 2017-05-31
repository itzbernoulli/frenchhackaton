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

public class MonthsActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("January","Janvier",R.raw.phrase_where_are_you_going));
        words.add(new Word("February","Fevrier",R.raw.phrase_what_is_your_name));
        words.add(new Word("March","Mars",R.raw.phrase_my_name_is));
        words.add(new Word("April","Avril",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("May","Mai",R.raw.phrase_im_feeling_good));
        words.add(new Word("June","Juin",R.raw.phrase_are_you_coming));
        words.add(new Word("July","Juillet",R.raw.phrase_yes_im_coming));
        words.add(new Word("August","Aout",R.raw.phrase_im_coming));
        words.add(new Word("September","Septembre",R.raw.phrase_lets_go));
        words.add(new Word("October","Octobre",R.raw.phrase_come_here));
        words.add(new Word("November","Novembre",R.raw.phrase_lets_go));
        words.add(new Word("December","Decembre",R.raw.phrase_come_here));


        WordAdapter adapter = new WordAdapter(this, words,R.color.category_phrases);

        ListView listView =(ListView)findViewById(R.id.list);

        listView.setAdapter(adapter );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get(i);
                if(mediaPlayer != null){
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(MonthsActivity.this,word.getAudioResourceId());
                    mediaPlayer.start();
                }else{
                    mediaPlayer = MediaPlayer.create(MonthsActivity.this,word.getAudioResourceId());
                    mediaPlayer.start();
                }
            }
        });
    }
}
