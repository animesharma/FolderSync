package com.skylar.foldersync;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    private static final int REQUEST_WRITE_PERMISSION = 1;
    private boolean isChosen = false;
    private String dir = "";

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }// else onDirSel();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            onDirSel();
        else requestPermission();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
    }

    public void onDirSel() {
        final Button dirChooserButton = (Button) findViewById(R.id.button);
        final TextView tv = (TextView) findViewById(R.id.textView);
        dirChooserButton.setOnClickListener(new OnClickListener() {
            private String m_chosenDir = "";
            private boolean m_newFolderEnabled = true;


            @Override
            public void onClick(View v) {
                /*if (isChosen) {
                    Intent myIntent = new Intent(MainActivity.this, CryptActivity.class);
                    myIntent.putExtra("ChosenDirectory", m_chosenDir);
                    startActivity(myIntent);

                }*/
                // Create DirectoryChooserDialog and register a callback
                DirectoryChooserDialog directoryChooserDialog =
                        new DirectoryChooserDialog(MainActivity.this,
                                new DirectoryChooserDialog.ChosenDirectoryListener() {
                                    @Override
                                    public void onChosenDir(String chosenDir) {
                                        m_chosenDir = chosenDir;
                                        dir = chosenDir;
                                        Toast.makeText(
                                                MainActivity.this, "Chosen directory: " +
                                                        chosenDir, Toast.LENGTH_LONG).show();
                                        tv.setText(chosenDir);
                                        //dirChooserButton.setText(getString(R.string.compute_button));
                                        isChosen = true;

                                        //finish();
                                    }
                                });
                // Toggle new folder button enabling
                directoryChooserDialog.setNewFolderEnabled(m_newFolderEnabled);
                // Load directory chooser dialog for initial 'm_chosenDir' directory.
                // The registered callback will be called upon final directory selection.
                directoryChooserDialog.chooseDirectory(m_chosenDir);
                m_newFolderEnabled = !m_newFolderEnabled;

            }

        });


        final Button computeButton = (Button) findViewById(R.id.button2);
        computeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChosen) {
                    Intent myIntent = new Intent(MainActivity.this, CryptActivity.class);
                    myIntent.putExtra("ChosenDirectory", dir);
                    startActivity(myIntent);
                }
                else Toast.makeText(MainActivity.this, "No Directory Chosen!", Toast.LENGTH_LONG).show();
            }
        });

    }
}



