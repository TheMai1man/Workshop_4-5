package com.example.workshop_4_5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectorFragment extends Fragment
{
    private CommonData mViewModel;
    private StructureData data;

    public SelectorFragment() {}

    public SelectorFragment(StructureData data)
    {
        this.data = data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_selector, ui, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.selectorRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager( getActivity(),
                LinearLayoutManager.HORIZONTAL, false ) );

        if(data == null)
        {
            data = new StructureData();
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

        /* handle user interaction here */

    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataVHolder>
    {
        private final StructureData data;

        public MyAdapter(StructureData data)
        {
            this.data = data;
        }

        @Override
        public int getItemCount()
        {
            return data.size();
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
            vh.bind(data.get(index));

            /* here sets the selected structure */
            vh.imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mViewModel.setSelectedStructure( data.get(vh.getBindingAdapterPosition()) );
                }
            });


        }
    }

    private static class MyDataVHolder extends RecyclerView.ViewHolder
    {
        private final TextView textView;
        private final ImageView imageView;

        public MyDataVHolder(LayoutInflater li, ViewGroup parent)
        {
            super( li.inflate(R.layout.list_selection, parent, false) );
            textView = (TextView) itemView.findViewById(R.id.structureText);
            imageView = (ImageView) itemView.findViewById(R.id.structureImage);
        }

        public void bind(Structure data)
        {
            textView.setText(data.getLabel());
            imageView.setImageResource( data.getDrawableId() );
        }
    }

}
