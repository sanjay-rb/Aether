package sih.project.airwater;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerFragment extends BaseFragment {

    private static final String TAG = "AnswerFragment";


    public AnswerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_answer, container, false);

        try{
            Bundle arg = getArguments();
            assert arg != null;
            int index = arg.getInt("list_item", 0);
            Log.i(TAG, "get argument from faq fragment");
            LinearLayout india_aqi = view.findViewById(R.id.india_aqi_view);
            LinearLayout pm25_view = view.findViewById(R.id.pm2_5_view);
            LinearLayout pm10_view = view.findViewById(R.id.pm10_view);
            LinearLayout co2_view = view.findViewById(R.id.co2_view);

            switch (index){
                case 0:
                    india_aqi.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    pm25_view.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    pm10_view.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    co2_view.setVisibility(View.VISIBLE);
                    break;
            }
        }catch (Exception e){
            displayError(TAG, e);
        }
        return view;
    }
}
