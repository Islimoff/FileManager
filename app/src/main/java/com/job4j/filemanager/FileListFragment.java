package com.job4j.filemanager;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class FileListFragment extends Fragment {

    private RecyclerView recycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        File directory = Environment.getExternalStorageDirectory();
        File[] files = directory.listFiles();
        recycler = view.findViewById(R.id.items_list);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(new FileAdapter(files));
        return view;
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
            textView.setText(file.getName());
            holder.view.setOnClickListener(btn -> {
                File currentDirectory = new File(path);
                files = currentDirectory.listFiles();
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
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
