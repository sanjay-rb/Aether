package sih.project.airwater;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import io.blackbox_vision.materialcalendarview.view.CalendarView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonthlyFragment extends BaseFragment {


    private LineChart mp_lineChart;
    private ArrayList<Entry> yData;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd/hh:mm a");
    private static final String TAG = "MonthlyFragment";


    public MonthlyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly, container, false);
        try{
            mp_lineChart = view.findViewById(R.id.date_chart);
            mp_lineChart.getAxisRight().setEnabled(false);
            // enable description text
            mp_lineChart.getDescription().setEnabled(false);
            // enable touch gestures
            mp_lineChart.setTouchEnabled(true);
            // enable scaling and dragging
            mp_lineChart.setDragEnabled(true);
            mp_lineChart.setScaleEnabled(true);
            mp_lineChart.setDrawGridBackground(false);
            // if disabled, scaling can be done on x- and y-axis separately
            mp_lineChart.setPinchZoom(true);
            // set an alternative background color
            mp_lineChart.setBackgroundColor(Color.WHITE);
            LineData data = new LineData();
            data.setValueTextColor(Color.BLACK);
            // add empty data
            mp_lineChart.setData(data);
            mp_lineChart.animateX(3000);

            // get the legend (only possible after setting data)
            Legend l = mp_lineChart.getLegend();

            // modify the legend ...
            l.setXEntrySpace(50f);
            l.setForm(Legend.LegendForm.LINE);
            l.setTextColor(Color.BLACK);

            XAxis xl = mp_lineChart.getXAxis();
            xl.setTextColor(Color.BLACK);
            xl.setLabelRotationAngle(35f);
            xl.setDrawGridLines(false);
            xl.setAvoidFirstLastClipping(true);
            xl.setEnabled(true);
            xl.setAxisLineWidth(5f);
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            xl.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    Long timeStamp = (long) value;
                    Date date = new Date(timeStamp);

                    return sdf.format(date);
                }
            });

            YAxis leftAxis = mp_lineChart.getAxisLeft();
            leftAxis.setTextColor(Color.BLACK);
            leftAxis.setAxisMinimum(0f);
            leftAxis.setDrawGridLines(true);
            leftAxis.setAxisLineWidth(5f);

            YAxis rightAxis = mp_lineChart.getAxisRight();
            rightAxis.setEnabled(false);
            yData = new ArrayList<>();


            CalendarView calendarView = view.findViewById(R.id.calender_view);

            calendarView.shouldAnimateOnEnter(true)
                    .setOnDateClickListener(selectedDate -> {
                        Toast.makeText(getContext(), selectedDate.toString(), Toast.LENGTH_SHORT).show();
                        yData.clear();
                        for(int i=0; i<10; i++){
                            yData.add(new Entry(new Date().getTime()*(i*1235), new Random().nextFloat()));
                        }
                        mp_lineChart.animateX(3000);
                        updateGraph();
                    });

            calendarView.update(Calendar.getInstance(Locale.getDefault()));
        }catch (Exception e){
            displayError(TAG, e);
        }
        return view;
    }

    private void updateGraph() {
        try{
            LineDataSet lineDataSet = new LineDataSet(yData, "Air Quality PPM");
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSet.setColor(ColorTemplate.getHoloBlue());
            lineDataSet.setCircleColor(ColorTemplate.getHoloBlue());
            lineDataSet.setLineWidth(2f);
            lineDataSet.setCircleRadius(4f);
            lineDataSet.setFillAlpha(65);
            lineDataSet.setFillColor(ColorTemplate.getHoloBlue());
            lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));
            lineDataSet.setValueTextColor(Color.BLACK);
            lineDataSet.setValueTextSize(9f);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillFormatter((dataSet, dataProvider) -> mp_lineChart.getAxisLeft().getAxisMinimum());
            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.fill_color);
                lineDataSet.setFillDrawable(drawable);
            } else {
                lineDataSet.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet);
            LineData data = new LineData(dataSets);
            mp_lineChart.notifyDataSetChanged();
            mp_lineChart.setData(data);
            mp_lineChart.moveViewToX(data.getEntryCount());
            mp_lineChart.setVisibleXRangeMaximum(data.getXMax());
            mp_lineChart.invalidate();
        }catch (Exception e){
            displayError(TAG, e);
        }
    }

}
