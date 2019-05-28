package sih.project.airwater;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

public class TabActivity extends BaseActvity {

    private static final String TAG = "TabActivity";
    private int isHome = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        try{
            BottomNavigationView navigationView = findViewById(R.id.nav_view_tab);
            findViewById(R.id.nav_view);

            openFragement(new HomeFragment());

            navigationView.setOnNavigationItemSelectedListener(menuItem -> {

                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        openFragement(new HomeFragment());
                        isHome = 1;
                        return true;
                    case R.id.nav_timeline:
                        openFragement(new TimeLineFragment());
                        isHome = 0;
                        return true;
                    case R.id.nav_news:
                        openFragement(new NewsFragment());
                        isHome = 0;
                        return true;
                    case R.id.nav_faq:
                        openFragement(new FAQFragment());
                        isHome = 0;
                        return true;
                    default:
                        return false;
                }

            });
        }catch (Exception e){
            displayError(TAG, e);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBackPressed() {
        try{
            if (isHome == 0){
                openFragement(new HomeFragment());
                isHome = 1;
            }else {
                System.exit(0);
            }
        }catch (Exception e){
            displayError(TAG, e);
        }
    }


}
