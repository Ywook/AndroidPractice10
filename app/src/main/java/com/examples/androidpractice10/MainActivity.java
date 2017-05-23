package com.examples.androidpractice10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {
    CheckBox checkBox;
    MyCanvas canvas;
    String file_name = "sample.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = (CheckBox)findViewById(R.id.checkBox);
        canvas = (MyCanvas)findViewById(R.id.canvas);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
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
        if(item.getItemId() == 1 || item.getItemId() == 2) {
            if (item.isChecked()) {
                item.setChecked(false);
                canvas.setMaskFilter(false);
            } else {
                item.setChecked(true);
                canvas.setMaskFilter(true);
            }
        }else if(item.getItemId() == 3){
            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            canvas.setPenWidth(item.isChecked());

        }else if(item.getItemId() == 4){
            canvas.setPenColor(true);
        }else if(item.getItemId() == 5){
            canvas.setPenColor(false);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){
        String tag = (String)v.getTag();
        switch (tag){
            case "save":
                canvas.save(getFilesDir() + file_name);
                break;
            case "open":
                canvas.open(getFilesDir() + file_name, getApplicationContext());
                break;
            case "eraser":
                checkBox.setChecked(false);
                canvas.setOperationType(tag);
                break;
            default:
                checkBox.setChecked(true);
                canvas.setOperationType(tag);
                break;
        }
    }
}
