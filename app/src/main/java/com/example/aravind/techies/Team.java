package com.example.aravind.techies;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aravind on 24/01/16.
 */
public class Team implements Serializable{

    public ArrayList<String> participants;
    int position=0;

    public Team(){
        participants = new ArrayList<>();
    }

    public Team(int posSet){
        super();
        this.position = posSet;
    }

}
