package com.hungnmse160060.prm392_lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvSubject;
    EditText etCurrentItem;
    Button btnAdd;
    Button btnUpdate;
    Button btnDelete;
    int curPos;
    ArrayList <String> subjects;
    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvSubject = (ListView) findViewById(R.id.lvSubject);
        etCurrentItem = (EditText) findViewById(R.id.etCurrentItem);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        button = (FloatingActionButton) findViewById(R.id.changeLab);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        });
        curPos = -1;
        subjects = new ArrayList<>();
        subjects.add("PRM");
        subjects.add("PRN");
        subjects.add("EXE");
        subjects.add("PRU");
        subjects.add("SWD");
        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, subjects);
        lvSubject.setAdapter(adapter);
        lvSubject.setOnItemClickListener((aV, v, i, l) -> {
            etCurrentItem.setText(subjects.get(i));
            curPos = i;
        });
        btnAdd.setOnClickListener(v -> {
            String subject = etCurrentItem.getText().toString();
            if (subject.length() == 0) {
                etCurrentItem.setError("Empty String");
            }
            else {
                subjects.add(subject);
                adapter.notifyDataSetChanged();
                resetCur();
            }
        });
        btnDelete.setOnClickListener(v -> {
            if (curPos == -1) return;
            subjects.remove(curPos);
            adapter.notifyDataSetChanged();
            resetCur();
        });
        btnUpdate.setOnClickListener(v -> {
            if (curPos == -1) return;
            String subject = etCurrentItem.getText().toString();
            if (subject.length() == 0) {
                etCurrentItem.setError("Empty String");
            }
            else {
                subjects.set(curPos, subject);
                adapter.notifyDataSetChanged();
                resetCur();
            }
        });
    }
    private void resetCur() {
        curPos = -1;
        etCurrentItem.setText("");
    }
}