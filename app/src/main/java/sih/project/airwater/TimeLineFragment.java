package sih.project.airwater;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimeLineFragment extends BaseFragment {
    private static final String TAG = "TimeLineFragment";


    public TimeLineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_time_line, container, false);

        try{
            @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.tab_view, new DailyFragment() );
            fragmentTransaction.commit();

            TabLayout tabLayout = view.findViewById(R.id.tab_layout);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    Toast.makeText(getContext(), tab.getText(), Toast.LENGTH_SHORT).show();

                    if(Objects.requireNonNull(tab.getText()).toString().equals("DAILY")){
                        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.tab_view, new DailyFragment() );
                        fragmentTransaction.commit();
                    }else if (tab.getText().toString().equals("MONTHLY")){
                        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.tab_view, new MonthlyFragment() );
                        fragmentTransaction.commit();
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {}

                @Override
                public void onTabReselected(TabLayout.Tab tab) {}
            });
        }catch (Exception e){
            displayError(TAG, e);
        }
        return view;
    }
}
