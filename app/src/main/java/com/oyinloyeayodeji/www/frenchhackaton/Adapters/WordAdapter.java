package com.oyinloyeayodeji.www.frenchhackaton.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oyinloyeayodeji.www.frenchhackaton.Objects.Word;
import com.oyinloyeayodeji.www.frenchhackaton.R;

import java.util.ArrayList;

/**
 * Created by Ayo on 30/12/2016.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> words, int ColorResourceId){
        super(context,0,words);
        mColorResourceId = ColorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Word currentWord = getItem(position);

        TextView defaultTextView = (TextView)listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());

        TextView miwokTextView = (TextView)listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getFrenchTranslation());

        ImageView imageView = (ImageView)listItemView.findViewById(R.id.imageView);
        if(currentWord.hasImage()){
            //Set the imageView to the resource specified in the current word
            imageView.setImageResource(currentWord.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            //otherwise hide the ImageView(set visibility to GONE)
            imageView.setVisibility(View.GONE);
        }
//      Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);

        //Find the color that the resource ID maps
        int color = ContextCompat.getColor(getContext(),mColorResourceId);

        //set the background color of the text container view
        textContainer.setBackgroundColor(color);

// Return the whole list item layout(containing 2 Textviews and one image view
        return listItemView;
    }
}
