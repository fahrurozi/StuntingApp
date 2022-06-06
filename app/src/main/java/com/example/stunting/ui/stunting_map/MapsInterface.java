package com.example.stunting.ui.stunting_map;

import com.example.stunting.data.model.maps.DataPlace;

public interface MapsInterface {
    void onDirection(DataPlace data);

    void onShare(DataPlace data);

    void onClick(DataPlace data);
}
