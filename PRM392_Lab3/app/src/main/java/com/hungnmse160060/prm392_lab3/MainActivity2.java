package com.hungnmse160060.prm392_lab3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    ListView lvFruit;
    FruitAdapter fruitAdapter;
    ImageView imgF;
    EditText txtName;
    EditText txtDescription;
    Button btnDelete;
    Button btnAdd;
    Button btnUpdate;
    List<Fruit> fruitList;
    int curPos = -1;
    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mapping();
        button = (FloatingActionButton) findViewById(R.id.changeLab);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        imgF = (ImageView) findViewById(R.id.imgCFruit);
        txtName = (EditText) findViewById(R.id.txtCName);
        txtDescription = (EditText) findViewById(R.id.txtCDescription);
        btnDelete = (Button) findViewById(R.id.btnDeleteFruit);
        btnAdd = (Button) findViewById(R.id.btnAddFruit);
        btnUpdate = (Button) findViewById(R.id.btnUpdateFruit);
        fruitAdapter = new FruitAdapter(this, R.layout.fruit_item, fruitList);
        lvFruit.setAdapter(fruitAdapter);
        lvFruit.setOnItemClickListener((aV, v, i, l) -> {
            curPos = i;
            Fruit fr = fruitList.get(i);
            imgF.setImageDrawable(fr.getImage());
            txtName.setText(fr.getName());
            txtDescription.setText(fr.getDescription());
        });
        btnDelete.setOnClickListener(v -> {
            if (curPos == -1) return;
            fruitList.remove(curPos);
            fruitAdapter.notifyDataSetChanged();
            resetCur();
        });
        btnAdd.setOnClickListener(v -> {
            String name = txtName.getText().toString();
            String description = txtDescription.getText().toString();
            if (name.length() == 0) {
                txtName.setError("can not empty");

            }
            else if (description.length() == 0) {
                txtDescription.setError("can not empty");

            }
            else {
                Fruit fruit = new Fruit(name, description, imgF.getDrawable());
                fruitList.add(fruit);
                fruitAdapter.notifyDataSetChanged();
                resetCur();
            }

        });
        btnUpdate.setOnClickListener(v -> {
            if (curPos == -1) return;
            String name = txtName.getText().toString();
            String description = txtDescription.getText().toString();
            if (name.length() == 0) {
                txtName.setError("can not empty");

            }
            else if (description.length() == 0) {
                txtDescription.setError("can not empty");

            }
            else {
                Fruit fruit = new Fruit(name, description, imgF.getDrawable());
                fruitList.set(curPos, fruit);
                fruitAdapter.notifyDataSetChanged();
                resetCur();
            }
        });
        imgF.setOnClickListener(v -> {
            openSomeActivityForResult();
        });
    }
    private void mapping() {
        lvFruit = (ListView) findViewById(R.id.lvFruit);
        fruitList = new ArrayList<>();
        fruitList.add(new Fruit("Thanh Long","thanh long ngon bo re",getResources().getDrawable(R.drawable.dua_hau)));
        fruitList.add(new Fruit("Dua Hau","dua hau mat mong nuoc",getResources().getDrawable(R.drawable.dua_hau)));
    }
    private void resetCur() {
        curPos = -1;
        imgF.setImageResource(0);
        txtName.setText("");
        txtDescription.setText("");
    }
    public static final int PICK_IMAGE = 1;

    public void openSomeActivityForResult() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        if (uri != null) {
                            imgF.setImageURI(uri);
                        }
                    }
                }
            });
}