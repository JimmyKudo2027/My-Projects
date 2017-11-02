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

public class AswanFragment extends Fragment {
    List<Place> places;

    public AswanFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.place_list_fragment, container, false);

        places = new ArrayList<>();

        places.add(new Place(R.string.Phiale_Temple,R.string.Phiale_Temple_description,R.drawable.phiale_temple));
        places.add(new Place(R.string.Egyptian_Russian_Friendship_Monument,R.string.Egyptian_Russian_Friendship_Monument_description,R.drawable.egyptian_russian_friendship_monument));
        places.add(new Place(R.string.Coptic_Orthodox_Cathedral,R.string.Coptic_Orthodox_Cathedral_description,R.drawable.coptic_orthodox_cathedral));
        places.add(new Place(R.string.Monastery_Of_St_Simeon,R.string.Monastery_Of_St_Simeon_description,R.drawable.monastery_of_st_simeon));
        places.add(new Place(R.string.Mausoleum_Of_The_Aga_Khan,R.string.Mausoleum_Of_The_Aga_Khan_description,R.drawable.mausoleum_of_the_aga_khan));

        PlaceAdapter adapter = new PlaceAdapter(getActivity(),places, R.color.category_aswan);

        ListView listView = rootView.findViewById(R.id.contentListView);

        listView.setAdapter(adapter);

        return rootView;
    }
}
