package android.r3n3_r4ss3r.singleTask_11703190;

import android.os.AsyncTask;
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
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private TextView serverAnswer;
    private EditText student_number;
    private ServerRequest serverRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serverAnswer = findViewById(R.id.serverAnswer);
        student_number = findViewById(R.id.student_num);
        serverRequest = new ServerRequest();
    }

    /**
     * This method is used to send the entered student number to the specified server (see @strings)
     * and write the answer to a text-field.
     * The variables
     * _COSI [Client Out Server In] and
     * _CISO [Client In Server Out]
     * are the used streams.
     *
     * @param view
     */
    public void send(View view) {
        ServerRequest request = new ServerRequest();
        request.execute((Void) null);
        try {
            serverAnswer.setText(request.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void calc(View view) {
        // TODO add functionality for 11703190 mod 7 = 2
        String studentNumber = student_number.getText().toString();
        if (!studentNumber.equals("")) {
            char[] studentNumChars = studentNumber.toCharArray();
            Arrays.sort(studentNumChars); // change order - numbers ascending
            StringBuffer sb = new StringBuffer();
            for (char c : studentNumChars) {
                if (!isPrime(c)) {
                    sb.append(c);
                }
            }
            serverAnswer.setText(sb.toString());
        } else {
            serverAnswer.setText(R.string.error_no_studentNum);
        }
    }

    private boolean isPrime(char c) {
        int number = Integer.parseInt("" + c);
        if (number == 0)
            return false;
        if (number == 1)
            return true;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    class ServerRequest extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void...voids) {
            String studentNumber = (student_number).getText().toString();
            try {
                Socket socket = new Socket(getString(R.string.server_host), Integer.parseInt(getString(R.string.port)));
                DataOutputStream _COSI = new DataOutputStream(socket.getOutputStream());
                BufferedReader _CISO = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                _COSI.writeBytes(studentNumber + "\n");
                String res = _CISO.readLine();
                socket.close();
                return res;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
