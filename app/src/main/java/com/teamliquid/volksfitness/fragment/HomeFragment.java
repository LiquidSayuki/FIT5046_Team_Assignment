package com.teamliquid.volksfitness.fragment;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.squareup.picasso.Picasso;
import com.teamliquid.volksfitness.R;
import com.teamliquid.volksfitness.WeatherService;
import com.teamliquid.volksfitness.databinding.FragmentHomeBinding;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public HomeFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentHomeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        // Use a monash location here, if implement getting users current location, then replace this
        String lat = "37.909566";
        String lon = "145.135262";
        String key = getString(R.string.weather_api_key);
        Call<CurrentWeather> call = service.getWeather(lat, lon, key);
        // Get the response from the website
        call.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                CurrentWeather currentWeather = response.body();
                double temp = currentWeather.getMain().getTemp() - 273.15;
                String weatherIcon = "https://openweathermap.org/img/wn/"+ currentWeather.getWeather().get(0).icon +"@2x.png";
                String weatherCondition = currentWeather.getWeather().get(0).main;


                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        binding.textHome.setText(String.valueOf((int)temp) + "℃");
                        binding.weatherCondition.setText(weatherCondition);
                        // This idea come from https://www.geeksforgeeks.org/how-to-build-a-weather-app-in-android/
                        binding.weatherIcon.setImageURI(Uri.parse("https://openweathermap.org/img/wn/10d@2x.png"));
                        Picasso.get().load(weatherIcon).into(binding.weatherIcon);
                    }
                });


                //I do not know why this method can not work.
                //Bitmap bitmap = getImageBitmap("https://openweathermap.org/img/wn/10d@2x.png");
                //binding.weatherIcon.setImageBitmap(bitmap);

            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {

            }
        });

        // Map navigation
        binding.cardRunning.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_map_fragment,null));

        return view;
    }
//    private Bitmap getImageBitmap(String url) {
//        Bitmap bm = null;
//        try {
//            URL aURL = new URL(url);
//            URLConnection conn = aURL.openConnection();
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(is);
//            bm = BitmapFactory.decodeStream(bis);
//            bis.close();
//            is.close();
//        } catch (IOException e) {
//            Log.e(TAG, "Error getting bitmap", e);
//        }
//        return bm;
//    }

}
