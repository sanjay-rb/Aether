package sih.project.airwater;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {
    private static final String TAG = "NewsFragment";

    int[] images = new int[]{R.drawable.delhi_air_quality, R.drawable.pollution_reuters_delhi, R.drawable.ngt_air, R.drawable.pollution_delhi};

    String[] headlines = new String[]{"Delhi's air quality very poor, expected to improve: SAFAR", "Over 1.2 million early deaths in India due to air pollution: Report", "NGT acts tough on air pollution, directs 6 states to submit action plan by April 30", "Delhi's air quality remains in 'poor' category, may improve in coming days"};

    String[] main_news = new String[]{"According to Central Pollution Control Board, the AQI was recorded at 339.", "The report said that air pollution is the third highest cause of death among all health risks in India, ranking just above smoking.", "The report said that air pollution is the third highest cause of death among all health risks in India, ranking just above smoking.", "According to CPCB, the air quality in at least 25 areas in Delhi was in \"poor\" category, while it was \"very poor\" in two areas, PTI reported."};

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        try{
            ListView news_list = view.findViewById(R.id.news_lists);
            CustomeAdapter customeAdapter = new CustomeAdapter();

            news_list.setAdapter(customeAdapter);
        }catch (Exception e){
            displayError(TAG, e);
        }
        return view;
    }

    public class CustomeAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_list_view, null);
            try{
                @SuppressLint("CutPasteId") ImageView imageView = convertView.findViewById(R.id.news_image);
                @SuppressLint("CutPasteId") TextView head_news_view = convertView.findViewById(R.id.news_head);
                @SuppressLint("CutPasteId") TextView main_news_view = convertView.findViewById(R.id.news_main);

                imageView.setImageResource(images[position]);
                head_news_view.setText(headlines[position]);
                main_news_view.setText(main_news[position]);

            }catch (Exception e){
                displayError(TAG, e);
            }
            return convertView;
        }
    }

}
