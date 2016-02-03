package com.example.aravind.techies;

import java.util.ArrayList;

/**
 * Created by AkilAdeshwar on 03-02-2016.
 */
public class Utility {

    public static String generateJSON(ArrayList<Team> teamList){


        // Event and Level to be taken from SharedPreferences.
        String event = "Heptathlon";
        String level = "4";

        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("{\n");
        stringBuffer.append(generateJsonNameValuePair("event_name",event));
        stringBuffer.append(",\n");
        stringBuffer.append(generateJsonNameValuePair("level",level));
        stringBuffer.append(",\n");
        stringBuffer.append(generateTeamArrayJson(teamList,level));
        stringBuffer.append("\n");
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public static String generateJsonNameValuePair(String name,String value){
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("\""+name+"\" : ");
        stringBuffer.append("\""+value+"\" ");
        return stringBuffer.toString();
    }

    public static String generateTeamArrayJson(ArrayList<Team> teamList,String level){

        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("\"teams\": [ \n");

        int i=0;
        for(i=0;i<teamList.size()-1;i++){
            stringBuffer.append(generateTeamJson(teamList.get(i),level));
            stringBuffer.append(",\n");
        }
        stringBuffer.append(generateTeamJson(teamList.get(i),level));
        stringBuffer.append("]");
        return stringBuffer.toString();
    }



    public static String generateTeamJson(Team team,String level){

        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("{\n");
        if(level.matches("final")){
            stringBuffer.append(generateJsonNameValuePair("position",team.position+""));
        }
        else{
            stringBuffer.append(generateJsonNameValuePair("position",0+""));
        }
        stringBuffer.append(",\n");
        stringBuffer.append("\"members\" : ");
        stringBuffer.append(generateMembersJson(team.participants));
        stringBuffer.append("}\n");
        return stringBuffer.toString();
    }

    public static String generateMembersJson(ArrayList<String> participants){

        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("[ \n");

        int i=0;
        for(i=0;i<participants.size()-1;i++){
            stringBuffer.append("'"+participants.get(i)+"' , \n");
        }
        stringBuffer.append("'"+participants.get(i)+"'");
        stringBuffer.append(" ]\n");
        return stringBuffer.toString();
    }


}
