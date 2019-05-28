package sih.project.airwater;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FAQFragment extends BaseFragment {


    private static final String TAG = "FAQFragment";

    public FAQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);

        try{
            ListView listView = view.findViewById(R.id.faq_list_view);

            String[] item_array = {
                    "1.Indian AQI", "2.PM 2.5", "3.PM 10", "4.CO2", "5.Temperature",
                    "6.Humidity", "7.Volatile organic compounds", "8.CO", "9.Oxygen", "10.Ozone", "11.Data modelling & forecasting"
            };

            final ArrayList<String> list_item = new ArrayList<>(Arrays.asList(item_array));

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, list_item);

            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener((parent, view1, position, id) -> {
                Toast.makeText(getContext(), list_item.get(position), Toast.LENGTH_LONG).show();
                Bundle arg = new Bundle();
                arg.putInt("list_item", position);
                AnswerFragment answerFragment = new AnswerFragment();
                answerFragment.setArguments(arg);
                @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("Answer");
                fragmentTransaction.replace(R.id.nav_view, answerFragment, "Answer");
                fragmentTransaction.commit();
            });
        }catch (Exception e){
            displayError(TAG, e);
        }
        return view;
    }


}
