package com.example.workshop_4_5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{

    MapData map = MapData.get();
    StructureData structures = new StructureData();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        SelectorFragment selectorFragment = (SelectorFragment) fm.findFragmentById(R.id.selector);

        if(mapFragment == null)
        {
            mapFragment = new MapFragment(map);
        }

        if(selectorFragment == null)
        {
            selectorFragment = new SelectorFragment(structures);
        }

        fm.beginTransaction().add(R.id.map, mapFragment).commit();
        fm.beginTransaction().add(R.id.selector, selectorFragment).commit();
    }

}