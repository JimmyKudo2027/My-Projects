package com.example.jimmykudo.tourgideegy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy Kudo on 10/15/2017.
 */

public class CairoGizaFragment extends Fragment {

    List<Place> places;
    public CairoGizaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.place_list_fragment, container, false);

        places = new ArrayList<>();

        places.add(new Place(R.string.Cairo_Tower,R.string.Cairo_Tower_description,R.drawable.cairo_tower));
        places.add(new Place(R.string.Great_Pyramid_of_Cheops,R.string.Great_Pyramid_of_Cheops_description,R.drawable.great_pyramid_of_cheops));
        places.add(new Place(R.string.Great_Sphinx,R.string.Great_Sphinx_description,R.drawable.sphinx));
        places.add(new Place(R.string.Citadel,R.string.Citadel_description,R.drawable.citadel));
        places.add(new Place(R.string.Nile_River,R.string.Nile_River_description,R.drawable.nile_river));


        PlaceAdapter adapter = new PlaceAdapter(getActivity(),places, R.color.category_cairo_giza);

        ListView listView = rootView.findViewById(R.id.contentListView);

        listView.setAdapter(adapter);

        return rootView;

    }
}
