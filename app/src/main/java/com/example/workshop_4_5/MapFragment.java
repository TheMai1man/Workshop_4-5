package com.example.workshop_4_5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MapFragment extends Fragment
{
    private CommonData mViewModel;
    private MapData data;

    public MapFragment() {}

    public MapFragment(MapData md)
    {
        this.data = md;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_map, ui, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.mapRecyclerView);
        rv.setLayoutManager( new GridLayoutManager(getActivity(), MapData.HEIGHT,
                GridLayoutManager.HORIZONTAL, false) );

        if(data == null)
        {
            data = MapData.get();
        }

        MyAdapter adapter = new MyAdapter(data);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);

        /* handle user interaction here if not involving recycler view*/

    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataVHolder>
    {
        MapData data;

        public MyAdapter(MapData md)
        {
            this.data = md;
        }

        @Override
        public int getItemCount()
        {
            return (MapData.HEIGHT * MapData.WIDTH);
        }

        @NonNull
        @Override
        public MyDataVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new MyDataVHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(MyDataVHolder vh, int index)
        {
            int row = index % MapData.HEIGHT;
            int col = index / MapData.HEIGHT;
            vh.bind(data.get(row, col));

            /* this is where the structure image changes to the selected */
            vh.structure.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if( data.get(row, col).isBuildable() )
                    {
                        Structure structure = mViewModel.getSelectedStructure();
                        if( structure != null )
                        {
                            data.get(row, col).setStructure(structure);
                            vh.structure.setImageResource(structure.getDrawableId());
                        }
                    }

                }
            });

        }
    }

    private static class MyDataVHolder extends RecyclerView.ViewHolder
    {
        ImageView southEast, southWest, northEast, northWest, structure;

        public MyDataVHolder(LayoutInflater li, ViewGroup parent)
        {
            super( li.inflate(R.layout.grid_cell, parent, false) );

            int size = parent.getMeasuredHeight() / MapData.HEIGHT + 1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;

            southEast = (ImageView) itemView.findViewById(R.id.southEast);
            southWest = (ImageView) itemView.findViewById(R.id.southWest);
            northEast = (ImageView) itemView.findViewById(R.id.northEast);
            northWest = (ImageView) itemView.findViewById(R.id.northWest);
            structure = (ImageView) itemView.findViewById(R.id.structure);
        }

        public void bind(MapElement me)
        {
            southWest.setImageResource(me.getSouthWest());
            southEast.setImageResource(me.getSouthEast());
            northEast.setImageResource(me.getNorthEast());
            northWest.setImageResource(me.getNorthWest());
            if( me.getStructure() != null )
            {
                structure.setVisibility(View.VISIBLE);
                structure.setImageResource(me.getStructure().getDrawableId());
            }
            else
            {
                structure.setVisibility(View.GONE);
            }
        }
    }

}
