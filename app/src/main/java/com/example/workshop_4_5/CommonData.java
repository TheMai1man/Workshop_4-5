package com.example.workshop_4_5;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CommonData extends ViewModel
{
    public MutableLiveData<Structure> selectedStructure;

    public CommonData()
    {
        selectedStructure = new MutableLiveData<Structure>();
        selectedStructure.setValue(null);
    }

    public Structure getSelectedStructure()
    {
        return selectedStructure.getValue();
    }

    public void setSelectedStructure(Structure value)
    {
        this.selectedStructure.setValue(value);
    }
}
