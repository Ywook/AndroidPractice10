package com.examples.androidpractice10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {
    CheckBox checkBox;
    MyCanvas canvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = (CheckBox)findViewById(R.id.checkBox);
        canvas = (MyCanvas)findViewById(R.id.canvas);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("함수ㅊ체크", "클릭됨 : "+ b);
                canvas.setStemp(b);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"Bluring").setCheckable(true);
        menu.add(1,2,0,"Coloring").setCheckable(true);
        menu.add(2,3,0,"Pen Width Big").setCheckable(true);
        menu.add(3,4,0,"Pen Color RED");
        menu.add(4,5,0,"Pen Color BLUE");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 1){

        }else if(item.getItemId() == 2){

        }else if(item.getItemId() == 3){

        }else if(item.getItemId() == 4){

        }else if(item.getItemId() == 5){

        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){
        String tag = (String)v.getTag();
        if(tag.equals("eraser")) checkBox.setChecked(false);
        if(tag.equalsIgnoreCase("rotate") || tag.equalsIgnoreCase("move")
                ||tag.equals("scale") || tag.equals("skew")){
            checkBox.setChecked(true);
        }
        canvas.setOperationType(tag);
    }
}
