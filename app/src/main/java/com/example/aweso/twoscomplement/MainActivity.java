package com.example.aweso.twoscomplement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
    private TextView answerTV;
    private EditText inputET;
    private Switch negativeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.answerTV = (TextView)this.findViewById(R.id.answerTV);
        this.inputET = (EditText)this.findViewById(R.id.inputET);
        this.negativeSwitch = (Switch)this.findViewById(R.id.negativeSwitch);
    }

    private String flipTheBits(String bin)
    {
        String answer = "";
        for(int i = 0; i < bin.length(); i++)
        {
            answer += bin.charAt(i) == '0' ? '1' : '0';
        }
        return answer;
    }

    private String addOne(String bin) {
        StringBuilder answer = new StringBuilder(bin);
        for (int i = answer.length()-1; i > 0; i--) {
            if (answer.charAt(i) == '0') {
                answer.setCharAt(i, '1');
                break; //Once we've added the one we want to quit looping
            } else {
                // add one results in it being equal to 2 in base 10 or 10 in base 2, so we put a zero
                // and carry the one.
                answer.setCharAt(i, '0');
            }
        }
        return answer.toString();
    }

    private String encodeAsTwosComplement(String bin)
    {
        String bitsFlipped = this.flipTheBits(bin);
        String oneAdded = this.addOne(bitsFlipped);
        return oneAdded;
    }

    public void onConvertButtonPressed(View v)
    {
        String theBinaryNumber = this.inputET.getText().toString();

        if(this.negativeSwitch.isChecked())
        {
            this.answerTV.setText(this.encodeAsTwosComplement(theBinaryNumber));
        }
        else
        {
            this.answerTV.setText(theBinaryNumber);
        }

    }
}
