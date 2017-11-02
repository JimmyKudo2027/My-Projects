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

public class AlexFragment extends Fragment {

    List<Place> places;

    public AlexFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.place_list_fragment, container, false);

        places = new ArrayList<>();

        places.add(new Place(R.string.Catacombs_of_Kom_el_Shoqafa,R.string.Catacombs_of_Kom_el_Shoqafa_description,R.drawable.catacombs_of_kom_el_shoqafa));
        places.add(new Place(R.string.Qaitbey_Fort_and_the_Pharos_Lighthouse,R.string.Qaitbey_Fort_and_the_Pharos_Lighthouse_description,R.drawable.qaitbey_fort_and_the_pharos_lighthouse));
        places.add(new Place(R.string.Pompey_Pillar,R.string.Pompey_Pillar_description,R.drawable.pompey_pillar));
        places.add(new Place(R.string.Kom_el_Dikka,R.string.Kom_el_Dikka_description,R.drawable.kom_el_dikka));

        PlaceAdapter adapter = new PlaceAdapter(getActivity(),places, R.color.category_alex);

        ListView listView = rootView.findViewById(R.id.contentListView);

        listView.setAdapter(adapter);

        return rootView;
    }
}
