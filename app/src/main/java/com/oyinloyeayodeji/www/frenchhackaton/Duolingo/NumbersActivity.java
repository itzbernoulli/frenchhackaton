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

public class NumbersActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one","un",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two","deux",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three","trois",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four","quarte",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five","cinq",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six","six",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven","sept",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","huit",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","neuf",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten","dix",R.drawable.number_ten,R.raw.number_ten));


        WordAdapter adapter = new WordAdapter(this, words,R.color.category_phrases);

        final ListView listView =(ListView)findViewById(R.id.list);

        listView.setAdapter(adapter );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get(i);
                if(mediaPlayer != null){
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this,word.getAudioResourceId());
                    mediaPlayer.start();
                }else{
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this,word.getAudioResourceId());
                    mediaPlayer.start();
                }
            }
        });
    }
}
