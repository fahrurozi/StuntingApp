package com.example.stunting.ui.stunting_map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.BuildConfig;
import com.example.stunting.R;
import com.example.stunting.data.model.maps.DataPlace;
import com.example.stunting.data.model.maps.ResponseMaps;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONException;
import org.json.JSONStringer;

import dmax.dialog.SpotsDialog;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StuntingMapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, MapsInterface {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location location;
    private Integer lcoationPermissionCode = 2;
    private SpotsDialog spotsDialog;
    private LocationManager locationManager;

    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();

    private BottomSheetBehavior sheetBehavior;
    private LinearLayout bottom_sheet;

    private MapsAdapter adapter;
    EditText etSearch;

    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stunting_map);

        sharedPref = this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        ImageView btnBack = findViewById(R.id.btnBack);
        spotsDialog = new SpotsDialog(this, "Mohon Tunggu...");

        adapter = new MapsAdapter(this);
        RecyclerView rvData = findViewById(R.id.rvData);
        rvData.setAdapter(adapter);
        rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        btnBack.setOnClickListener(v -> finish());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        bottom_sheet = findViewById(R.id.llBottom);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        etSearch = findViewById(R.id.etSearch);
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                try {
                    searchLocation();
                    hideSoftKeyboard();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        });

    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    private void searchLocation() throws JSONException {
        if (!etSearch.getText().toString().isEmpty()) {
            spotsDialog.show();
            RequestBody body;
            JSONStringer json = new JSONStringer();
            json.object();
            json.key("location").value(location.getLatitude() + "," + location.getLongitude());
            json.key("keyword").value(etSearch.getText().toString());
            json.endObject();
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            Call<ResponseMaps> mapsCall = endpoint.getMaps(sharedPref.getString(getString(R.string.token), ""), body);
            mapsCall.enqueue(new Callback<ResponseMaps>() {

                @Override
                public void onResponse(Call<ResponseMaps> call, Response<ResponseMaps> response) {
                    spotsDialog.dismiss();
                    if (response.body().getPlaces() != null && response.body().getPlaces().size() > 0) {
                        adapter.insertDataList(response.body().getPlaces());
                        for (DataPlace data : response.body().getPlaces()) {
                            MarkerOptions markerLocation = new MarkerOptions()
                                    .position(new LatLng(data.getPlaceDetail().getResult().getGeometry().getLocation().getLat(), data.getPlaceDetail().getResult().getGeometry().getLocation().getLng()))
                                    .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_point_maps))
                                    .title(data.getPlaceDetail().getResult().getName());
                            mMap.addMarker(markerLocation);
                        }
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }

                @Override
                public void onFailure(Call<ResponseMaps> call, Throwable t) {
                    spotsDialog.dismiss();
                    Toast.makeText(StuntingMapActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLocationSettingsDialog();
    }

    private void showLocationSettingsDialog() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this)
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(task -> {
            try {
                task.getResult(ApiException.class);
            } catch (ApiException exception) {
                if (exception.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) exception;
                        resolvable.startResolutionForResult(this, 100);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        spotsDialog.show();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, lcoationPermissionCode);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this);
        }
    }

    @SuppressLint("MissingPermission")
    private void setUserLocation() {
        if (mMap != null && location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.setMyLocationEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getLocation();
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.location = location;
        spotsDialog.dismiss();
        locationManager.removeUpdates(this);
        setUserLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
    }

    @Override
    public void onDirection(DataPlace data) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+ location.getLatitude()+","+ location.getLongitude()+"&daddr="+data.getPlaceDetail().getResult().getGeometry().getLocation().getLat()+","+data.getPlaceDetail().getResult().getGeometry().getLocation().getLng()));
        startActivity(intent);
    }

    @Override
    public void onShare(DataPlace data) {
        Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, data.getPlaceDetail().getResult().getUrl());
        startActivity(Intent.createChooser(intent2, "Share via"));
    }

    @Override
    public void onClick(DataPlace data) {
        LatLng latLng = new LatLng(data.getPlaceDetail().getResult().getGeometry().getLocation().getLat(), data.getPlaceDetail().getResult().getGeometry().getLocation().getLng());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
    }
}