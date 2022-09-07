package com.stuntech.stunting.ui.stunting_map;

import com.stuntech.stunting.data.model.maps.DataPlace;
import com.stuntech.stunting.data.model.maps.DataPlace;

public interface MapsInterface {
    void onDirection(DataPlace data);

    void onShare(DataPlace data);

    void onClick(DataPlace data);
}
