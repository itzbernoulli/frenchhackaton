package com.oyinloyeayodeji.www.frenchhackaton.Objects;

/**
 * Created by Ayo on 14/05/2017.
 */

public class Word {
    //Default translation of the word
    private String mDefaultTranslation;

    private int mAudioResourceId;

    private  String mFrenchTranslation;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;


    public Word(String defaultTranslation,String miwokTranslation, int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mFrenchTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    public Word(String defaultTranslation,String miwokTranslation,int imageResourceId,int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mFrenchTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

//    Get the default translation of the word

    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    //    Get the French translation of the word
    public String getFrenchTranslation(){
        return mFrenchTranslation;
    }

    public int getImageResourceId(){
        return mImageResourceId;
    }

    //    this checks if there is an image or not
    public boolean hasImage(){
        return  mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceId(){
        return mAudioResourceId;
    }

//    No setter methods are created to modify the words because
//    we do not expect people to start modifying the meaning of words
}
