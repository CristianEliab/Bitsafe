package com.appmoviles.proyecto.util;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class FileChooser {


    private static final String PARENT_DIR = "..";

    private final Activity activity;
    private ListView list;
    private Dialog dialog;
    private File currentPath;

    // filter on file extension
    private String extension = null;

    public void setExtension(String extension) {
        this.extension = (extension == null) ? null :
                extension.toLowerCase();
    }

    // file selection event handling
    public interface FileSelectedListener {
        void fileSelected(File file);
    }

    public FileChooser setFileListener(FileSelectedListener fileListener) {
        this.fileListener = fileListener;
        return this;
    }

    private FileSelectedListener fileListener;

    public FileChooser(final Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        list = new ListView(activity);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int which, long id) {
                Archivos file = (Archivos) list.getItemAtPosition(which);
                String fileChosen = file.getArchivo();
                File chosenFile = getChosenFile(fileChosen);
                if (chosenFile != null) {
                    if (chosenFile.isDirectory()) {
                        refresh(chosenFile);
                    } else {
                        if(chosenFile.isFile()){
                            if (fileListener != null) {
                                fileListener.fileSelected(chosenFile);
                            }
                            dialog.dismiss();
                        }else{
                            Toast.makeText(activity.getApplicationContext(), "Directorio vacio!", Toast.LENGTH_SHORT).show();
                            refresh(chosenFile);
                        }
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog.setContentView(list);
        dialog.setTitle("Seleccione el archivo");
        dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        refresh(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
    }

    public void showDialog() {
        dialog.show();
    }


    /**
     * Sort, filter and display the files for the given path.
     */
    private void refresh(File path) {
        this.currentPath = path;
        if (path.exists()) {
            File[] dirs = path.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return (file.isDirectory() && file.canRead());
                }
            });
            File[] files = path.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (!file.isDirectory()) {
                        if (!file.canRead()) {
                            return false;
                        } else if (extension == null) {
                            return true;
                        } else {
                            return file.getName().toLowerCase().endsWith(extension);
                        }
                    } else {
                        return false;
                    }
                }
            });

            if (dirs == null || dirs.length == 0) {

            } else {
                // convert to an array
                int i = 0;
                String[] fileList;
                if (path.getParentFile() == null) {
                    fileList = new String[dirs.length + files.length];
                } else {
                    fileList = new String[dirs.length + files.length + 1];
                    fileList[i++] = PARENT_DIR;
                }
                Arrays.sort(dirs);
                Arrays.sort(files);
                for (File dir : dirs) {
                    fileList[i++] = dir.getName();
                }
                for (File file : files) {
                    fileList[i++] = file.getName();
                }

                // refresh the user interface
                dialog.setTitle(currentPath.getPath());

                ArrayList<Archivos> archivos = new ArrayList<>();
                for (String ar : fileList) {
                    Archivos archivos1 = new Archivos(ar);
                    archivos.add(archivos1);
                }

                AdapterArchivos adapterArchivos = new AdapterArchivos(activity, archivos);
                list.setAdapter(adapterArchivos);
            }
        }
    }


    /**
     * Convert a relative filename into an actual File object.
     */
    private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR)) {
            return currentPath.getParentFile();
        } else {
            return new File(currentPath, fileChosen);
        }
    }
}