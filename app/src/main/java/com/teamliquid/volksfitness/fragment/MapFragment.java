package com.teamliquid.volksfitness.fragment;


import static com.mapbox.navigation.ui.utils.internal.extensions.DrawableExKt.getBitmap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate;

import com.teamliquid.volksfitness.R;
import com.teamliquid.volksfitness.databinding.FragmentMapBinding;

public class MapFragment extends Fragment {
    private FragmentMapBinding binding;
    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private FusedLocationProviderClient fusedLocationClient;

    public MapFragment(){}

    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentMapBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        mapView = binding.mapView;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        // https://github.com/mapbox/mapbox-maps-android/issues/916
        // Thanks for Karatuno providing the solution for mapBox V10's Java version of annotation
        AnnotationPlugin annotationAPI = AnnotationPluginImplKt.getAnnotations((MapPluginProviderDelegate) mapView);
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationAPI,mapView);


        // Check permission
        // If permission granted
        if(!(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
            // Request for permission
            ActivityResultLauncher<String[]> locationPermissionRequest =
                    registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),result -> {
                        Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false);
                        if(fineLocationGranted != null && fineLocationGranted){
                            // Precise location access granted.
                        }else if (coarseLocationGranted != null && coarseLocationGranted){
                            // Only approximate location access granted.
                        }else{
                            // No location access granted.
                        }
                    });
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            onMapReady();
            refreshMapLocation();

        }

        return view;
    }

    private void onMapReady(){
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
        mapView.getMapboxMap().setCamera(new CameraOptions.Builder()
                .zoom(1.0)
                .build());
    }

    @SuppressLint("MissingPermission")
    private void refreshMapLocation(){
        // get current location, as the function name
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }

            @Override
            public boolean isCancellationRequested() {
                return false;
            }
        }).addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Point point = Point.fromLngLat(location.getLongitude(),location.getLatitude());
                    mapView.getMapboxMap().setCamera(new CameraOptions.Builder()
                            .zoom(14.0)
                            .center(point)
                            .build());

                    PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                            .withPoint(Point.fromLngLat(location.getLongitude(),location.getLatitude()))
                            .withIconImage(getBitmap(getResources().getDrawable(R.drawable.ic_baseline_location_on_24)));
                    pointAnnotationManager.create(pointAnnotationOptions);
                }
            }
        });
    }

    @SuppressLint("Lifecycle")
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @SuppressLint("Lifecycle")
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @SuppressLint("Lifecycle")
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @SuppressLint("Lifecycle")
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }
}
