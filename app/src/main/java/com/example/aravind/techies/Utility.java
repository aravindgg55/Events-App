package com.example.aravind.techies;

import java.util.ArrayList;

/**
 * Created by AkilAdeshwar on 03-02-2016.
 */
public class Utility {

    public static String generateJSON(ArrayList<Team> teamList,String level,String event){

        // Event and Level to be taken from SharedPreferences.
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("{");
        stringBuffer.append(generateJsonNameValuePair("event_name",event));
        stringBuffer.append(",");
        stringBuffer.append(generateJsonNameValuePair("level",level));
        stringBuffer.append(",");
        stringBuffer.append(generateTeamArrayJson(teamList,level));;
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public static String generateJsonNameValuePair(String name,String value){
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("\""+name+"\":");
        stringBuffer.append("\""+value+"\"");
        return stringBuffer.toString();
    }

    public static String generateTeamArrayJson(ArrayList<Team> teamList,String level){

        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("\"teams\":[");

        int i=0;
        for(i=0;i<teamList.size()-1;i++){
            stringBuffer.append(generateTeamJson(teamList.get(i),level));
            stringBuffer.append(",");
        }
        stringBuffer.append(generateTeamJson(teamList.get(i),level));
        stringBuffer.append("]");
        return stringBuffer.toString();
    }



    public static String generateTeamJson(Team team,String level){

        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("{");
        if(level.matches("final")){
            stringBuffer.append(generateJsonNameValuePair("position",team.position+""));
        }
        else{
            stringBuffer.append(generateJsonNameValuePair("position",0+""));
        }
        stringBuffer.append(",");
        stringBuffer.append("\"members\":");
        stringBuffer.append(generateMembersJson(team.participants));
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public static String generateMembersJson(ArrayList<String> participants){

        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("[");

        int i=0;
        for(i=0;i<participants.size()-1;i++){
            stringBuffer.append("\""+participants.get(i)+"\",");
        }
        stringBuffer.append("\""+participants.get(i)+"\"");
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}