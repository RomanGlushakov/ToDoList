package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextEnterNote;

    private RadioGroup radioGroupPriorities;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private Button buttonSave;

    private AddNoteViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();
        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose)
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

    }

    private void initViews() {
        radioGroupPriorities = findViewById(R.id.radioGroupPriorities);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        editTextEnterNote = findViewById(R.id.editTextEnterNote);
        buttonSave = findViewById(R.id.buttonSave);
    }

    private void saveNote() {
        String text = editTextEnterNote.getText().toString().trim();
        if (!text.isEmpty()) {
            int priority = getPriority();
            Note note = new Note ( text, priority);
            viewModel.saveNote(note);


        }
            else {
            Toast.makeText(AddNoteActivity.this, R.string.empty_note, Toast.LENGTH_SHORT).show();
        }

    }
    private int getPriority() {

        int priority;
        if (radioButtonLow.isChecked()) {
            priority = 0;
        }
        else if (radioButtonMedium.isChecked()) {
            priority = 1;
        }
        else {
            priority = 2;
        }
        return priority;
    }

    public static Intent newIntent(Context context) {
        return new Intent (context, AddNoteActivity.class);
    }
}