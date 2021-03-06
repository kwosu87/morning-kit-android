package com.yooiistudios.morningkit.setting.theme.language;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yooiistudios.morningkit.R;

public class MNLanguageFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MNSoundEffectFragment.
     */
    public static MNLanguageFragment newInstance() {
        return new MNLanguageFragment();
    }
    public MNLanguageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.setting_theme_language_fragment, container, false);
        if (rootView != null) {
            ListView listView = (ListView) rootView.findViewById(R.id.setting_theme_language_listview);
            listView.setAdapter(new MNLanguageListAdapter(getActivity()));
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
