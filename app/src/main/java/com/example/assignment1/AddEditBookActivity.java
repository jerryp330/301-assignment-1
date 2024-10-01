package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditBookActivity extends AppCompatActivity {

    private EditText bookTitleInput, bookAuthorInput, bookYearInput;
    private Spinner bookGenreInput;
    private Switch bookReadStatusSwitch;
    private Button saveBookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_book);

        // Initialize UI components
        bookTitleInput = findViewById(R.id.bookTitleInput);
        bookAuthorInput = findViewById(R.id.bookAuthorInput);
        bookYearInput = findViewById(R.id.bookYearInput);
        bookGenreInput = findViewById(R.id.bookGenreInput);
        bookReadStatusSwitch = findViewById(R.id.bookReadStatusSwitch);
        saveBookButton = findViewById(R.id.saveBookButton);

        // Check if we're editing a book
        Intent intent = getIntent();
        if (intent.hasExtra("title")) {
            bookTitleInput.setText(intent.getStringExtra("title"));
            bookAuthorInput.setText(intent.getStringExtra("author"));
            bookYearInput.setText(String.valueOf(intent.getIntExtra("year", 0)));

            // Set the genre spinner selection
            String genre = intent.getStringExtra("genre");
            if (genre != null) {
                int spinnerPosition = getGenreSpinnerPosition(genre);
                bookGenreInput.setSelection(spinnerPosition);
            }

            // Set the read status switch
            bookReadStatusSwitch.setChecked(intent.getBooleanExtra("isRead", false));
        }

        // Set up the save button click listener
        saveBookButton.setOnClickListener(v -> saveBook());
    }

    // Helper method to get the position of the genre in the spinner
    private int getGenreSpinnerPosition(String genre) {
        String[] genres = getResources().getStringArray(R.array.genres_array);
        for (int i = 0; i < genres.length; i++) {
            if (genres[i].equals(genre)) {
                return i;
            }
        }
        return 0;  // Default to the first option if not found
    }

    // Method to save the book details and return to the main activity
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

        // If editing an existing book, include the position
        if (getIntent().hasExtra("position")) {
            resultIntent.putExtra("position", getIntent().getIntExtra("position", -1));
        }

        // Set the result and finish the activity
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}


