package sg.edu.rp.c302.id19030019.demofilereadwriting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button writeButton, readButton;
    TextView statusTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        writeButton = findViewById(R.id.btnWrite);
        readButton = findViewById(R.id.btnWrite);
        statusTextView = findViewById(R.id.textView);

        //folder creation
        String folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";

        String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(MainActivity.this, permission, 0);

        File folder = new File(folderLocation);
        if (!folder.exists()) {
            boolean result = folder.mkdir();
            if (result) {
                Log.d("File read/write", "Folder Created");
            }
            else {
                Toast.makeText(this, "Failed to create!", Toast.LENGTH_SHORT).show();
            }
        }

        File textFile = new File(folderLocation, "data.txt");
        writeButton.setOnClickListener(view -> {
            try {
                FileWriter writer = new FileWriter(textFile, true);
                writer.write("test data: " + UUID.randomUUID() + "\n");
                writer.flush();
                writer.close();
            }
            catch (Exception e) {
                Toast.makeText(this, "Fail to write file", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
        readButton.setOnClickListener(view -> {
            if (textFile.exists()) {
                String data = "";
                try {
                    FileReader reader = new FileReader(textFile);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        data += line + "\n";
                        line = bufferedReader.readLine();
                    }
                    bufferedReader.close();
                    reader.close();
                }
                catch (Exception e) {
                    Toast.makeText(this, "Fail to read file", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                statusTextView.setText(data);
            }
        });
    }
}