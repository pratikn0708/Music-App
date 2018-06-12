package com.example.nitinthakur.listenmp3;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView l;
   String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l=(ListView)findViewById(R.id.listview);
final ArrayList<File> mysongs=findsongs(Environment.getExternalStorageDirectory());
        items=new String[mysongs.size()];
        for (int i=0;i<mysongs.size();i++)
        {
       // toast(mysongs.get(i).getName().toString());
            items[i]=mysongs.get(i).getName().toString().replace(".mp3","");

        }
        ArrayAdapter<String> adb=new ArrayAdapter<String>(getApplicationContext(),R.layout.songlayout,R.id.textView2,items);
    l.setAdapter(adb);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
startActivity(new Intent(getApplicationContext(), Main2Activity.class).putExtra("pos",position).putExtra("songlist",mysongs));

            }
        });
    }
    public ArrayList<File>findsongs(File root)
    {
        ArrayList<File>al=new ArrayList<File>();
        File[] files=root.listFiles();
        for (File singlefile :files)
        {
            if(singlefile.isDirectory() && !singlefile.isHidden())
            {
              al.addAll(findsongs(singlefile));
            }
            else
            {
                if(singlefile.getName().endsWith(".mp3"))
                {
al.add(singlefile);
                }
            }
        }
    return al;}
    public void toast(String text)
    {
        Toast.makeText(this, "text", Toast.LENGTH_SHORT).show();
    }
}
