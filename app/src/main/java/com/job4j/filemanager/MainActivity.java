package com.job4j.filemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File directory = Environment.getExternalStorageDirectory();
        File[] files = directory.listFiles();
        recycler = findViewById(R.id.items_list);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileHolder> {

        private List<File> fileList;

        public FileAdapter(List<File> fileList) {
            this.fileList = fileList;
        }

        @NonNull
        @Override
        public FileAdapter.FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.file_manager, parent, false);
            return new FileAdapter.FileHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FileAdapter.FileHolder holder, int position) {
            final File file=fileList.get(position);
            TextView textView=holder.view.findViewById(R.id.file_name);
            textView.setText(file.getName());
        }

        @Override
        public int getItemCount() {
            return fileList.size();
        }

        public class FileHolder extends RecyclerView.ViewHolder {

            public View view;

            public FileHolder(@NonNull View view) {
                super(view);
                this.view = itemView;
            }
        }
    }
}
