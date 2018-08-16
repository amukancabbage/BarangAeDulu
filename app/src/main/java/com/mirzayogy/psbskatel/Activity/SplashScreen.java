package com.mirzayogy.psbskatel.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirzayogy.psbskatel.DetectConnection;
import com.mirzayogy.psbskatel.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashScreen extends AppCompatActivity {

    @BindView(R.id.internetNotAvailable)
    LinearLayout internetNotAvailable;
    @BindView(R.id.splashImage)
    ImageView splashImage;
    SharedPreferences sharedPreference, sharedPreferencesCache;
    SharedPreferences.Editor editor;
    @BindView(R.id.errorMessage)
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferencesCache = getSharedPreferences("cacheExist", 0);

        sharedPreference = getSharedPreferences("localData", 0);
        editor = sharedPreference.edit();
        // Check the internet and get response from API's
        if (DetectConnection.checkInternetConnection(getApplicationContext())) {
            //getCategoryList();
            moveNext();

        } else {
            if (sharedPreference.getString("categoryList", "").equalsIgnoreCase("") ||
                    sharedPreferencesCache.getBoolean("exist", false) == false) {
                internetNotAvailable.setVisibility(View.VISIBLE);
                splashImage.setVisibility(View.GONE);
                errorMessage.setText("Internet Connection Not Available");
            } else {
//                Gson gson = new Gson();
//                String json = sharedPreference.getString("categoryList", "");
//                String json1 = sharedPreference.getString("placelist", "");
//                Log.d("savedCategoryData", sharedPreference.getString("categoryList", "Not Available"));
//                List categoryData, placeData;
//                CategoryListResponse[] categoryItems = gson.fromJson(json, CategoryListResponse[].class);
//                categoryData = Arrays.asList(categoryItems);
//                categoryListResponseData = new ArrayList(categoryData);
//                Log.d("categoryListRseDataD", categoryListResponseData.size() + "");
//
//                Place[] placeItems = gson.fromJson(json1, Place[].class);
//                placeData = Arrays.asList(placeItems);
//                newsListResponsesData = new ArrayList(placeData);
//                Log.d("placeListRseDataD", newsListResponsesData.size() + "");
                Intent intent = new Intent(SplashScreen.this, Welcome.class);
                startActivity(intent);
                moveNext();
            }
        }
    }

    @OnClick(R.id.tryAgain)
    public void onClick() {
        if (DetectConnection.checkInternetConnection(getApplicationContext())) {
            internetNotAvailable.setVisibility(View.GONE);
            splashImage.setVisibility(View.VISIBLE);
            //getCategoryList();
        } else {
            internetNotAvailable.setVisibility(View.VISIBLE);
            splashImage.setVisibility(View.GONE);
        }
    }
    private void moveNext() {
// redirect to next page after getting data from server
//        try {
//            imagesList1 = new ArrayList<>();
//            if (id.length() > 0) {
//                for (int j = 0; j < newsListResponsesData.size(); j++) {
//                    if (newsListResponsesData.get(j).getPlaceId().trim().equalsIgnoreCase(id)) {
//                        imagesList1.add(newsListResponsesData.get(j));
//                    }
//                }
//
//                PlaceDetail.newsListResponsesData = imagesList1;
//                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
//                intent.putExtra("pos", 0);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finishAffinity();
//            } else {
                Intent intent = new Intent(SplashScreen.this, Welcome.class);
                startActivity(intent);
                finish();
//            }
//        } catch (Exception e) {
//            Log.d("error notification data", e.toString());
//            Intent intent = new Intent(SplashScreen.this, WelcomeActivity.class);
//            startActivity(intent);
//            finish();
//        }

    }
}
