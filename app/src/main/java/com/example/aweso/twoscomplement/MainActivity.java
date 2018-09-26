package com.example.aweso.twoscomplement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
    private TextView answerTV;
    private EditText inputET;
    private TextView decodeTV;
    private Button decodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.answerTV = (TextView)this.findViewById(R.id.answerTV);
        this.inputET = (EditText)this.findViewById(R.id.inputET);
        this.decodeTV = (TextView)this.findViewById(R.id.decodeTV);
        this.decodeBtn = (Button)this.findViewById(R.id.onDecodeBtn);
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

//    private String addOne(String bin) {
//        StringBuilder answer = new StringBuilder(bin);
//        for (int i = answer.length()-1; i > 0; i--) {
//            if (answer.charAt(i) == '0') {
//                answer.setCharAt(i, '1');
//                break; //Once we've added the one we want to quit looping
//            } else {
//                // add one results in it being equal to 2 in base 10 or 10 in base 2, so we put a zero
//                // and carry the one.
//                answer.setCharAt(i, '0');
//            }
//        }
//        return answer.toString();
//    }

    private int binaryToDecimal(String bin) {
        int place = 1;
        int sum = 0;
        for (int i = bin.length()-1; i >= 0; i--) {
            sum += place * (bin.charAt(i) == '0' ? 0 : 1);
            place *= 2;
        }
        return sum;
    }

    private String decimalToBinary(int n) {
        String answer = "";
        while(n != 0) {
            answer = n % 2 + answer;
            n /= 2;
        }

        return answer;
    }

    private String addOne2(String bin) {
        return decimalToBinary(binaryToDecimal(bin) + 1);
//        return Integer.toBinaryString(Integer.parseInt(bin, 2) + 1);
    }

    /** In Class version */
    private String addOne(String bin) {
        int carry = 0;
        String temp = "";
        for(int i = 0; i < bin.length()-1; i++) {
            temp += "0";
        }
        temp += "1";
        String answer = "";
        for(int i = bin.length()-1; i >= 0; i--) {
            int n1 = bin.charAt(i) == '0' ? 0 : 1;
            int n2 = temp.charAt(i) == '0' ? 0 : 1;
            int sum = n1 + n2 + carry;
            if (sum < 2) {
                carry = 0;
                answer = sum + answer;
            } else if (sum == 2) {
                carry = 1;
                answer = "0" + answer;
            } else {
                carry = 1;
                answer = "1" + answer;
            }
        }

        if (carry == 1) {
            answer = 1 + answer;
        } else {
            answer = 0 + answer;
        }
        return answer;
    }

    //Closer to the examples we did in class
//    private String subtractOne(String bin) {
//        String answer = "";
//        boolean subtracted = false;
//        for(int i = bin.length()-1; i >= 0; i--) {
//            if(bin.charAt(i) == '1' && !subtracted) {
//                //We subtract one and then mark that we did the subtraction. Otherwise we know we will
//                //have to carry on the next iteration.
//                answer = "0" + answer;
//                subtracted = true;
//            } else if(bin.charAt(i) == '0' && !subtracted){
//                // We flip the bit because we are carrying a one through this spot to subtract
//                answer = "1" + answer;
//            } else {
//                //The math has been done so we just need to finish moving the numbers into the answer
//                answer = bin.charAt(i) + answer;
//            }
//        }
//        return answer;
//    }

    /** Potentially cleaner option **/
    private String subtractOne(String bin) {
        StringBuilder answer = new StringBuilder(bin);
        for (int i = answer.length()-1; i > 0; i--) {
            if (answer.charAt(i) == '1') {
                answer.setCharAt(i, '0');
                break; //Once we've subtracted we quite the loop
            } else {
                // If the number is a zero we flip it to a one and will carry from the previous place.
                answer.setCharAt(i, '1');
            }
        }
        return answer.toString();
    }

    private String decodeTwosComplement(String bin) {
        String minusOne = this.subtractOne(bin);
        return this.flipTheBits(minusOne);
    }

    private String encodeAsTwosComplement(String bin)
    {
        String bitsFlipped = this.flipTheBits(bin);
        String oneAdded = this.addOne2(bitsFlipped);
        return oneAdded;
    }

    public void onConvertButtonPressed(View v)
    {
        this.decodeBtn.setEnabled(true);
        int theDecimalNumber = Integer.parseInt(this.inputET.getText().toString());
        if (theDecimalNumber < 0) {
            this.answerTV.setText(this.encodeAsTwosComplement(0 + this.decimalToBinary(theDecimalNumber * -1)));
        } else {
            this.answerTV.setText("0" + this.decimalToBinary(theDecimalNumber));
        }
    }

    public void onDecodeButtonPressed(View v) {
        String currentEncodedValue = this.answerTV.getText().toString();
        if(currentEncodedValue.charAt(0) == '0') {
            this.decodeTV.setText(binaryToDecimal(currentEncodedValue) + "");
        } else {
            //Simple option - coded in class before assignment was given because it gets the same result
            //the book tells about this way.
//            String decoded = this.encodeAsTwosComplement(currentEncodedValue);

            //Method that actually uses a method I wrote for subtraction.
            String decoded = this.decodeTwosComplement(currentEncodedValue);
            this.decodeTV.setText(binaryToDecimal(decoded) * -1 + "");
        }
    }
}
