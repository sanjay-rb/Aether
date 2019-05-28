package sih.project.airwater;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tooltip.Tooltip;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "HomeFragment";


    private DatabaseReference airRef;
    private TextView air_real_time_data;
    private CardView air_card_view;
    private Dialog popup;
    private ProgressDialog progressDialog;
    private ImageView face_img;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        try{
            progressDialog = showProgression(getContext(), "Fetching Data", "Loading");
            progressDialog.show();
            airRef = FirebaseDatabase.getInstance().getReference("AirRealTimeData");
            air_real_time_data = view.findViewById(R.id.air_quality_value);
            popup = new Dialog(Objects.requireNonNull(getContext()));

            air_card_view = view.findViewById(R.id.air_card_view);
            face_img = view.findViewById(R.id.face_image);


            air_card_view.setOnClickListener(v -> onAirClick());
        }catch (Exception e){
            displayError(TAG, e);
        }

        return view;
    }
    public void onAirClick(){
        try{
            popup.setContentView(R.layout.custom_dialog_popup);
            popup.show();
            progressDialog.show();
            popup.setCancelable(true);

            final PointerSpeedometer spv = popup.findViewById(R.id.gauge_level);
            final TextView air_level = popup.findViewById(R.id.air_pollution_level);
            final TextView air_description = popup.findViewById(R.id.air_description);

            spv.setWithTremble(false);
            spv.setUnit("ppm");
            spv.setCenterCircleColor(Color.BLACK);
            spv.setMaxSpeed(50.4f);

            airRef.addValueEventListener(new ValueEventListener() {
                @SuppressLint({"SetTextI18n"})
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Double air_value = (Double) Objects.requireNonNull(dataSnapshot.child("value").getValue());
                    progressDialog.dismiss();
                    if(air_value < 3.0){
                        spv.setBackgroundCircleColor(getResources().getColor(R.color.good_green));
                        air_level.setText("Good");
                        air_description.setText("Enjoy your usual outdoor activities. No health implications. Everyone can continue their outdoor activities normally.\n");
                        popup.findViewById(R.id.danger_layout).setVisibility(View.GONE);
                        popup.findViewById(R.id.moderate_layout).setVisibility(View.GONE);
                        popup.findViewById(R.id.good_layout).setVisibility(View.VISIBLE);

                    }else if (air_value > 3.0 && air_value < 4.0) {
                        spv.setBackgroundCircleColor(getResources().getColor(R.color.moderate_yellow));
                        air_level.setText("Moderate");
                        air_description.setText("Adults and children with lung problems, and adults with heart problems, who experience symptoms, should consider reducing strenuous physical activity, particularly outdoors.\n");
                        popup.findViewById(R.id.danger_layout).setVisibility(View.GONE);
                        popup.findViewById(R.id.good_layout).setVisibility(View.GONE);
                        popup.findViewById(R.id.moderate_layout).setVisibility(View.VISIBLE);
                    }else {
                        spv.setBackgroundCircleColor(getResources().getColor(R.color.danger_red));
                        air_level.setText("Danger");
                        air_description.setText("Adults and children with lung problems, and adults with heart problems, should reduce strenuous physical exertion, particularly outdoors, and particularly if they experience symptoms. People with asthma may find they need to use their reliever inhaler more often. Older people should also reduce physical exertion.\n");
                        popup.findViewById(R.id.moderate_layout).setVisibility(View.GONE);
                        popup.findViewById(R.id.good_layout).setVisibility(View.GONE);
                        popup.findViewById(R.id.danger_layout).setVisibility(View.VISIBLE);
                    }
                    spv.speedTo(Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("value").getValue()) + ""), 1);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    displayDBError(TAG, databaseError);
                }
            });

            popup.findViewById(R.id.good_face).setOnClickListener(this);
            popup.findViewById(R.id.good_sport).setOnClickListener(this);
            popup.findViewById(R.id.good_window).setOnClickListener(this);
            popup.findViewById(R.id.mod_face).setOnClickListener(this);
            popup.findViewById(R.id.mod_sport).setOnClickListener(this);
            popup.findViewById(R.id.mod_window).setOnClickListener(this);
            popup.findViewById(R.id.bad_face).setOnClickListener(this);
            popup.findViewById(R.id.bad_sport).setOnClickListener(this);
            popup.findViewById(R.id.bad_window).setOnClickListener(this);
            popup.findViewById(R.id.bad_mask).setOnClickListener(this);
            popup.findViewById(R.id.bad_air_pure).setOnClickListener(this);
        }catch (Exception e){
            displayError(TAG, e);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        try{
            airRef.addValueEventListener(new ValueEventListener() {
                @SuppressLint({"SetTextI18n", "ResourceType"})
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Log.d("Data", Objects.requireNonNull(dataSnapshot.child("value").getValue()) + "");
                    air_real_time_data.setText(Objects.requireNonNull(dataSnapshot.child("value").getValue()) + "");

                    Double air_value = (Double) Objects.requireNonNull(dataSnapshot.child("value").getValue());

                    progressDialog.dismiss();
                    try{
                        if(air_value < 3.0){
                            air_card_view.setCardBackgroundColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.good_green));
                            face_img.setImageResource(R.drawable.ic_ic_face_1_green);
                        }else if (air_value >= 3.0 && air_value < 4.0){
                            air_card_view.setCardBackgroundColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.moderate_yellow));
                            face_img.setImageResource(R.drawable.ic_ic_face_2_yellow);
                        }else {
                            air_card_view.setCardBackgroundColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.danger_red));
                            face_img.setImageResource(R.drawable.ic_ic_face_4_red);
                        }
                    }catch (Exception ex){
                        String err = (ex.getMessage()==null)?"Data Exception":ex.getMessage();
                        Log.e("Error:",err);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    displayDBError(TAG, databaseError);
                }
            });
        }catch (Exception e){
            displayError(TAG, e);
        }
    }
    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.good_face:
                    showToolTips(v, "Good air Enjoy environment!", getResources().getColor(R.color.good_green));
                    break;
                case R.id.good_sport:
                    showToolTips(v, "Enjoy your usual outdoor activities!", getResources().getColor(R.color.good_green));
                    break;
                case R.id.good_window:
                    showToolTips(v, "Open your window fresh and good air!", getResources().getColor(R.color.good_green));
                    break;
                case R.id.mod_face:
                    showToolTips(v, "Moderate air be aware of environment!", getResources().getColor(R.color.moderate_yellow));
                    break;
                case R.id.mod_sport:
                    showToolTips(v, "Sensitive person please avoid outdoor activity!", getResources().getColor(R.color.moderate_yellow));
                    break;
                case R.id.mod_window:
                    showToolTips(v, "Close your window some polluted air outside!" , getResources().getColor(R.color.moderate_yellow));
                    break;
                case R.id.bad_face:
                    showToolTips(v, "Take your mask danger air!", getResources().getColor(R.color.danger_red));
                    break;
                case R.id.bad_sport:
                    showToolTips(v, "Avoid Outdoor activities!", getResources().getColor(R.color.danger_red));
                    break;
                case R.id.bad_window:
                    showToolTips(v, "Close your window some polluted air outside!", getResources().getColor(R.color.danger_red));
                    break;
                case R.id.bad_mask:
                    showToolTips(v, "Wear your mask!", getResources().getColor(R.color.danger_red));
                    break;
                case R.id.bad_air_pure:
                    showToolTips(v, "Have a air purifier!", getResources().getColor(R.color.danger_red));
                    break;
            }
        }catch (Exception e){
            displayError(TAG, e);
        }
    }
    private void showToolTips(View v, String text, int color) {
        try{
            new Tooltip.Builder(v)
                    .setText(text)
                    .setTextColor(color)
                    .setGravity(Gravity.TOP)
                    .setCornerRadius(8f)
                    .setDismissOnClick(true).show();
        }catch (Exception e){
            displayError(TAG, e);
        }
    }

}
