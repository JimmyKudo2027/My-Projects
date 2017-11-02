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


public class LuxorFragment extends Fragment {

    List<Place> places;

    public LuxorFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.place_list_fragment, container, false);

        places = new ArrayList<>();

        places.add(new Place(R.string.Valley_of_the_Kings,R.string.Valley_of_the_Kings_description,R.drawable.valley_of_the_kings));
        places.add(new Place(R.string.Tomb_of_Seti_I_KV_17,R.string.Tomb_of_Seti_I_KV_17_description,R.drawable.tomb_of_seti_i_kv_17));
        places.add(new Place(R.string.Tombs_of_the_Nobles,R.string.Tombs_of_the_Nobles_description,R.drawable.tombs_of_the_nobles));
        places.add(new Place(R.string.Karnak,R.string.Karnak_description,R.drawable.karnak));
        places.add(new Place(R.string.Medinat_Habu,R.string.Medinat_Habu_description,R.drawable.medinat_habu));

        PlaceAdapter adapter = new PlaceAdapter(getActivity(),places, R.color.category_luxor);

        ListView listView = rootView.findViewById(R.id.contentListView);

        listView.setAdapter(adapter);

        return rootView;
    }
}
