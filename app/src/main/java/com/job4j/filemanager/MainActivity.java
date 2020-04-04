package com.job4j.filemanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<String> tree = new ArrayList<>();
    private int count;

    @Override
    protected void onCreate(@Nullable Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_main);
        count = 0;
        File directory = Environment.getExternalStorageDirectory();
        tree.add(directory.getAbsolutePath());
        File[] files = directory.listFiles();
        recycler = findViewById(R.id.items_list);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        FileAdapter adapter = new FileAdapter(files);
        recycler.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.previousButton();
            }
        });
    }

    class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileHolder> {

        private File[] files;

        public FileAdapter(File[] files) {
            this.files = files;
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
            final File file = files[position];
            String path = file.getAbsolutePath();
            TextView textView = holder.view.findViewById(R.id.file_name);
            ImageView imageView = holder.view.findViewById(R.id.file_image);
            if (file.isFile()) {
                imageView.setImageResource(R.drawable.ic_file);
            }else {
                imageView.setImageResource(R.drawable.ic_action_name);
            }
            textView.setText(file.getName());
            holder.view.setOnClickListener(btn -> {
                refreshList(path);
                tree.add(path);
                count++;
            });
        }

        public void previousButton() {
            if (count > 0) {
                count--;
                refreshList(tree.get(count));
            }
        }

        private void refreshList(String path) {
            File directory = new File(path);
            files = directory.listFiles();
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if (files == null) {
                return 0;
            }
            return files.length;
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