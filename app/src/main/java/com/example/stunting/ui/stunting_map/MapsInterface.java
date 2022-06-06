package com.example.stunting.ui.stunting_map;

import com.example.stunting.data.model.child.DataChild;
import com.example.stunting.data.model.maps.DataPlace;

public interface MapsInterface {
    public void onDirection(DataPlace data);
    public void onShare(DataPlace data);
    public void onClick(DataPlace data);
}
