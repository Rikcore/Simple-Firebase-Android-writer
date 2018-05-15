package rikcore.chatfirebase;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Message message;
    DatabaseReference myRef;
    RelativeLayout layout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.relativeLayout);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("board");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (  DataSnapshot  postSnapshot: dataSnapshot.getChildren()) {
                Message currentMessage = postSnapshot.getValue(Message.class);
                printMessage(currentMessage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int)motionEvent.getX();
                int y = (int)motionEvent.getY();
                message = new Message("blue", 0, 0);
                message.setX(x);
                message.setY(y);

                showAlert(message);

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:
                }
                return false;
            }
        });

    }

    public void showAlert(final Message player){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Message");

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);


        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String message = input.getText().toString();
                        player.setMessage(message);
                        writeOnDb(player);
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    public void writeOnDb(Message message){
        // Write a message to the database
        myRef.push().setValue(message);
    }

    public void printMessage(Message message){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(this);
        textView.setText(message.getMessage());
        textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        textView.setPadding(5,0,5,0);
        textView.measure(0,0);
        int width = textView.getMeasuredWidth();
        int height = textView.getMeasuredHeight();
        int x = 0;
        int y = 0;
        if(message.getX() + width < layout.getMeasuredWidth() && message.getY() + height < layout.getMeasuredHeight()){
            x = message.getX() - width / 2;
            y = message.getY() - height / 2;
        } else if(message.getX() + width > layout.getMeasuredWidth() && message.getY() + height < layout.getMeasuredHeight()){
            x = message.getX() - width;
            y = message.getY();
        } else if(message.getX() + width < layout.getMeasuredWidth() && message.getY() + height > layout.getMeasuredHeight()){
            x = message.getX();
            y = message.getY() - height;
        }
          else {
            x = message.getX() - width;
            y = message.getY() - height;
        }
        params.leftMargin = x;
        params.topMargin = y;
        layout.addView(textView, params);


    }

}
