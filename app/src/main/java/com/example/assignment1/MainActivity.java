package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


// Typical MainActivity class,it handles
// the main functionality of the book wishlist application.
public class MainActivity extends AppCompatActivity {

    private RecyclerView bookRecyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private TextView bookCountText;
    private Button addBookButton;

    private static final int ADD_BOOK_REQUEST = 1;
    private static final int EDIT_BOOK_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookRecyclerView = findViewById(R.id.bookRecyclerView);
        bookCountText = findViewById(R.id.bookCountText);
        addBookButton = findViewById(R.id.addBookButton);

        bookList = new ArrayList<>();

        bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(this, bookList, this::editBook);
        bookRecyclerView.setAdapter(bookAdapter);

        addBookButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditBookActivity.class);
            startActivityForResult(intent, ADD_BOOK_REQUEST);
        });

        updateBookCount();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ADD_BOOK_REQUEST) {
                String title = data.getStringExtra("title");
                String author = data.getStringExtra("author");
                String genre = data.getStringExtra("genre");
                int year = data.getIntExtra("year", 0);
                boolean isRead = data.getBooleanExtra("isRead", false);
                bookList.add(new Book(title, author, genre, year, isRead));
                bookAdapter.notifyDataSetChanged();

            } else if (requestCode == EDIT_BOOK_REQUEST) {
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    String title = data.getStringExtra("title");
                    String author = data.getStringExtra("author");
                    String genre = data.getStringExtra("genre");
                    int year = data.getIntExtra("year", 0);
                    boolean isRead = data.getBooleanExtra("isRead", false);
                    Book book = bookList.get(position);
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setGenre(genre);
                    book.setPublicationYear(year);
                    book.setRead(isRead);
                    bookAdapter.notifyItemChanged(position);
                }
            }

            int deletePosition = data.getIntExtra("delete_position", -1);
            if (deletePosition != -1) {
                onBookDelete(deletePosition);
            }

            updateBookCount();
        }
    }



    private void editBook(Book book, int position) {
        Intent intent = new Intent(MainActivity.this, AddEditBookActivity.class);
        intent.putExtra("title", book.getTitle());
        intent.putExtra("author", book.getAuthor());
        intent.putExtra("genre", book.getGenre());
        intent.putExtra("year", book.getPublicationYear());
        intent.putExtra("isRead", book.isRead());
        intent.putExtra("position", position);
        startActivityForResult(intent, EDIT_BOOK_REQUEST);
    }

    public void onBookDelete(int position) {
        bookAdapter.deleteBook(position);
        updateBookCount();
    }

    private void updateBookCount() {
        int totalBooks = bookList.size();
        int readBooks = (int) bookList.stream().filter(Book::isRead).count();
        TextView bookCountText = findViewById(R.id.bookCountText);
        bookCountText.setText("Total Books: " + totalBooks + ", Read: " + readBooks);
    }
}
