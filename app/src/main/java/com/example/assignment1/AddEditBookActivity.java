package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;


// This class is responsible for managing the user interface and interactions involved
// in adding a new book to the wishlist or editing an existing book's details.
public class AddEditBookActivity extends AppCompatActivity {

    private EditText bookTitleInput, bookAuthorInput, bookYearInput;
    private Spinner bookGenreInput;
    private Switch bookReadStatusSwitch;
    private Button saveBookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_book);

        bookTitleInput = findViewById(R.id.bookTitleInput);
        bookAuthorInput = findViewById(R.id.bookAuthorInput);
        bookYearInput = findViewById(R.id.bookYearInput);
        bookGenreInput = findViewById(R.id.bookGenreInput);
        bookReadStatusSwitch = findViewById(R.id.bookReadStatusSwitch);
        saveBookButton = findViewById(R.id.saveBookButton);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);

        Button deleteButton = findViewById(R.id.deleteButton);
        if (position != -1) {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(v -> {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("delete_position", position);
                setResult(RESULT_OK, resultIntent);
                finish();
            });
        } else {
            deleteButton.setVisibility(View.GONE); // Hide delete button if adding a new book
        }

        if (intent.hasExtra("title")) {
            bookTitleInput.setText(intent.getStringExtra("title"));
            bookAuthorInput.setText(intent.getStringExtra("author"));
            bookYearInput.setText(String.valueOf(intent.getIntExtra("year", 0)));

            String genre = intent.getStringExtra("genre");
            if (genre != null) {
                int spinnerPosition = getGenreSpinnerPosition(genre);
                bookGenreInput.setSelection(spinnerPosition);
            }

            bookReadStatusSwitch.setChecked(intent.getBooleanExtra("isRead", false));
        }

        saveBookButton.setOnClickListener(v -> saveBook());
    }

    private int getGenreSpinnerPosition(String genre) {
        String[] genres = getResources().getStringArray(R.array.genres_array);
        for (int i = 0; i < genres.length; i++) {
            if (genres[i].equals(genre)) {
                return i;
            }
        }
        return 0;
    }

    private void saveBook() {
        String title = bookTitleInput.getText().toString();
        String author = bookAuthorInput.getText().toString();
        String genre = bookGenreInput.getSelectedItem().toString();
        int year = Integer.parseInt(bookYearInput.getText().toString());
        boolean isRead = bookReadStatusSwitch.isChecked();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("title", title);
        resultIntent.putExtra("author", author);
        resultIntent.putExtra("genre", genre);
        resultIntent.putExtra("year", year);
        resultIntent.putExtra("isRead", isRead);

        if (getIntent().hasExtra("position")) {
            resultIntent.putExtra("position", getIntent().getIntExtra("position", -1));
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}


