package com.example.attendanceappgeneral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button[] buttons;
    String[] currentWorkNumString;
    Service service;
    DataStorage data;
    Context context;
    TextView currentWorkNumTextView;
    EditText employeeNumEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        service = new Service();
        data = new DataStorage(this);
        buttons = new Button[24];
        currentWorkNumString = new String[24];
        context = this;
        currentWorkNumTextView = findViewById(R.id.currentWorkNum);
        employeeNumEditText = findViewById(R.id.employeeNumEditText);
        employeeNumEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideSoftKeyboard(employeeNumEditText);
                    return true;
                }
                return false;
            }
        });

        setUpWorkNumButtons();
        setUpGoToWorkAndOffFromWorkButton();
        getAndSetEmployeeNumToEditText();
        findCurrentWorkNum();
        currentWorkNumTextView.setText(getWorkNum());

    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    private void setUpGoToWorkAndOffFromWorkButton(){
        Button goToWork = findViewById(R.id.goToWorkButton);
        goToWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employeeNum = data.getStringData("employeeNum");
                if (employeeNum.length() == 6)
                    service.goToWork(employeeNum, context);
                else
                    Toast.makeText(context, "请输入至少6位员工号码", Toast.LENGTH_SHORT).show();
            }
        });

        Button offFromWork = findViewById(R.id.offFromWorkButton);
        offFromWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employeeNum = data.getStringData("employeeNum");
                if (employeeNum.length() == 6) {
                    if (getWorkNum().equals("空白")) {
                        Toast.makeText(context, "请至少选择一个工作号码", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String fullWorkNum = service.workNumWithPoundAndPause(currentWorkNumString);
                        service.offFromWork(employeeNum, fullWorkNum, context);
                    }
                }

                else
                    Toast.makeText(context, "请输入至少6位员工号码", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAndSetEmployeeNumToEditText() {
        employeeNumEditText.setText(data.getStringData("employeeNum"));
    }

    private void recordEmployeeNum(){
        data.storeData("employeeNum", employeeNumEditText.getText().toString());
    }

    private void findCurrentWorkNum() {
        for (int i=0; i<buttons.length; i++){
            if (data.getBooleanData(buttons[i].getText().toString())){
                currentWorkNumString[i] = buttons[i].getText().toString();
            }
            else
                currentWorkNumString[i] = "";
        }
    }

    private void setUpWorkNumButtons(){
        Button button101 = findViewById(R.id.button101);
        buttons[0] = button101;
        Button button102 = findViewById(R.id.button102);
        buttons[1] = button102;
        Button button106 = findViewById(R.id.button106);
        buttons[2] = button106;
        Button button107 = findViewById(R.id.button107);
        buttons[3] = button107;
        Button button108 = findViewById(R.id.button108);
        buttons[4] = button108;
        Button button110 = findViewById(R.id.button110);
        buttons[5] = button110;
        Button button111 = findViewById(R.id.button111);
        buttons[6] = button111;
        Button button112 = findViewById(R.id.button112);
        buttons[7] = button112;
        Button button113 = findViewById(R.id.button113);
        buttons[8] = button113;
        Button button117 = findViewById(R.id.button117);
        buttons[9] = button117;
        Button button202 = findViewById(R.id.button202);
        buttons[10] = button202;
        Button button203 = findViewById(R.id.button203);
        buttons[11] = button203;
        Button button204 = findViewById(R.id.button204);
        buttons[12] = button204;
        Button button205 = findViewById(R.id.button205);
        buttons[13] = button205;
        Button button300 = findViewById(R.id.button300);
        buttons[14] = button300;
        Button button301 = findViewById(R.id.button301);
        buttons[15] = button301;
        Button button411 = findViewById(R.id.button411);
        buttons[16] = button411;
        Button button500 = findViewById(R.id.button500);
        buttons[17] = button500;
        Button button501 = findViewById(R.id.button501);
        buttons[18] = button501;
        Button button502 = findViewById(R.id.button502);
        buttons[19] = button502;
        Button button506 = findViewById(R.id.button506);
        buttons[20] = button506;
        Button button508 = findViewById(R.id.button508);
        buttons[21] = button508;
        Button button509 = findViewById(R.id.button509);
        buttons[22] = button509;
        Button button511 = findViewById(R.id.button511);
        buttons[23] = button511;

        checkButtonStatus(buttons);
    }

    private void checkButtonStatus(Button [] buttons) {
        for(Button button: buttons) {
            service.changeButtonStatusAndColor(button, data.getBooleanData(button.getText().toString()));
            setUpOnclickForWorkNumButtons(button);
        }
    }

    private void setUpOnclickForWorkNumButtons(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = button.getText().toString();
                boolean currentButtonStatus = data.getBooleanData(key);
                if (currentButtonStatus) {
                    service.changeButtonStatusAndColor(button, false);
                    data.storeData(key, false);
                }
                else{
                    service.changeButtonStatusAndColor(button, true);
                    data.storeData(key, true);
                }
                findCurrentWorkNum();
                currentWorkNumTextView.setText(getWorkNum());
            }
        });
    }

    private String getWorkNum(){
        StringBuilder returnVal = new StringBuilder("");
        for(String workNum: currentWorkNumString) {
            if (!workNum.equals(""))
                returnVal.append(workNum).append(", ");
        }

        if (returnVal.toString().equals("")){
            return "空白";
        }
        else {
            returnVal.deleteCharAt(returnVal.length()-2);
            return returnVal.toString();
        }

    }

    public void checkPermission()
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        }
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        recordEmployeeNum();
    }
}