package com.oyinloyeayodeji.www.frenchhackaton;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.oyinloyeayodeji.www.frenchhackaton.Duolingo.ColorsActivity;
import com.oyinloyeayodeji.www.frenchhackaton.Duolingo.FamilyActivity;
import com.oyinloyeayodeji.www.frenchhackaton.Duolingo.NumbersActivity;

/**
 * Created by Ayo on 14/05/2017.
 */

public class TabLearn extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout rootView = (LinearLayout)inflater.inflate(R.layout.tab_learn, container, false);

        ImageView famImageView = (ImageView)rootView.findViewById(R.id.family);
        famImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),FamilyActivity.class);
                startActivity(i);
            }
        });

        ImageView numImageView = (ImageView)rootView.findViewById(R.id.numbers);
        numImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),NumbersActivity.class);
                startActivity(i);
            }
        });

        ImageView colorImageView = (ImageView)rootView.findViewById(R.id.colors);
        colorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),ColorsActivity.class);
                startActivity(i);
            }
        });

        return rootView;
    }


}
