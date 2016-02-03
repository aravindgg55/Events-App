package com.example.aravind.techies;

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

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("");
        int i=0;
        stringBuffer.append("Team Count: "+participants.size()+"\n");
        for(i=0;i<participants.size();i++){
            stringBuffer.append((i+1)+" - "+participants.get(i)+".\n");
        }
        return stringBuffer.toString();
    }

}
