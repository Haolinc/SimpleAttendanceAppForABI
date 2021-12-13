package com.example.attendanceappgeneral;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.widget.Button;

public class Service {
    //Change color of the button base on whether active or not
    public void changeButtonStatusAndColor(Button btn, boolean status){
        if (status)
            btn.setBackgroundColor(Color.rgb(222,184,135));
        else
            btn.setBackgroundColor(Color.GRAY);
    }


    //Buttons function
    public void goToWork(String employeeNum, Context context){
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:7184807170" + pause(8) + "1" + pause(4) + employeeNum + pause(6) + "1"));
        context.startActivity(i);
    }

    public void offFromWork(String employeeNum, String workNum, Context context){
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:7184807170" + pause(8) + "2" + pause(4) + employeeNum + pause(6) + "1"
                + pause(4) + workNum));
        context.startActivity(i);
    }


    //Combine number with , and #
    public String workNumWithPoundAndPause(String [] arr){
        StringBuilder returnVal = new StringBuilder();
        for(String string: arr){
            if (!string.equals(""))
                returnVal.append(string).append(pound()).append(pause(4));
        }
        returnVal.append("000").append(pound());
        return returnVal.toString();
    }

    private String pound(){
        return Uri.encode("#");
    }

    private String pause(int second){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < second/2; i++){
            result.append(",");
        }
        return Uri.encode(result.toString());
    }
}
