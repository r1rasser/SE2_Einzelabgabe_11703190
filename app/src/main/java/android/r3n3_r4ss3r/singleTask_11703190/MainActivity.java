package android.r3n3_r4ss3r.singleTask_11703190;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void send(View view){
        TextView serverAnswer = findViewById(R.id.serverAnswer);
        String studentNumber = ((EditText)findViewById(R.id.student_num)).getText().toString();
        if(!studentNumber.equals("")) {
            try {
                Socket clientSocket = new Socket("se2-isys.aau.at", 53212);
                DataOutputStream _COSI = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader _CISO = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                _COSI.writeBytes(studentNumber);
                serverAnswer.setText(_CISO.readLine());
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            serverAnswer.setText(R.string.error_no_studentNum);
        }
    }
}
