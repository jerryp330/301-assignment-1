package com.example.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment1.Book;

import java.util.List;


// This class is a custom adapter designed to connect the book objects
// to the user interface components.
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private List<Book> bookList;
    private OnBookClickListener listener;
    private OnBookDeleteListener onBookDeleteListener;

    public BookAdapter(Context context, List<Book> bookList, OnBookClickListener listener) {
        this.context = context;
        this.bookList = bookList;
        this.listener = listener;
    }

    public interface OnBookClickListener {
        void onBookClick(Book book, int position);
    }

    public interface OnBookDeleteListener {
        void onBookDelete(int position);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book currentBook = bookList.get(position);

        holder.bookTitle.setText(currentBook.getTitle());
        holder.bookAuthor.setText(currentBook.getAuthor());
        holder.bookGenre.setText(currentBook.getGenre());
        holder.bookStatus.setText(currentBook.isRead() ? "Read" : "Unread");
        holder.itemView.setOnClickListener(v -> listener.onBookClick(currentBook, position));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        TextView bookTitle, bookAuthor, bookGenre, bookStatus;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookTitleText);
            bookAuthor = itemView.findViewById(R.id.bookAuthorText);
            bookGenre = itemView.findViewById(R.id.bookGenreText);
            bookStatus = itemView.findViewById(R.id.bookStatusText);
        }
    }

    public void deleteBook(int position) {
        bookList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bookList.size());
    }
}