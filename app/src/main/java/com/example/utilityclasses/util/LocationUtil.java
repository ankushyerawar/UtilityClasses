package com.example.utilityclasses.util;

import android.app.Activity;
import android.content.IntentSender;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.example.utilityclasses.OnLocationUpdateListener;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/* Add this in build.gradle(Model:app)
* //play services
* implementation 'com.google.android.gms:play-services-location:17.0.0'
* implementation 'com.google.android.gms:play-services-auth:17.0.0'
* Make sure to update to latest version
* */

/** The purpose of this class is to provide Location
 * There are 2 Main methods which are non static
 * 1) startLocationUpdates which receives requestCode as a parameter and stopLocationUpdate
 *    startLocationUpdates :-
 *    This request code is Used because if the GPS of User's device is 'OFF' it will show a Dialog to enable GPS
 *    To know the User action i.e Allow or Deny of Dialog
 *    stopLocationUpdate :-
 *    After getting the location stop the Update in the onGetLocation() method of OnLocationUpdateListener
 *    Or if you want to listen location update continuously then do it in onDestroy()
 * 2) How to Use:
 *    a)LocationUtil location = new LocationUtil(getActivity(), this); this -> OnLocationUpdateListener
 *      This is how you initialize the Class
 *    b)location.startLocationUpdates(101);
 *      This is how you start Listening to Location update.
 *      you will receive the Update in onGetLocation() method of OnLocationUpdateListener
 *    c)location.stopLocationUpdates()
 *      This is how you stop Location updates.
 */
public class LocationUtil {

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private Activity mActivity;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private OnLocationUpdateListener mLocationUpdateListener;

    public LocationUtil(Activity mActivity, OnLocationUpdateListener locationUpdateListener) {
        this.mActivity = mActivity;
        this.mLocationUpdateListener = locationUpdateListener;
        init();
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);
        mSettingsClient = LocationServices.getSettingsClient(mActivity);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLocationUpdateListener.onGetLocation(mCurrentLocation);
            }
        };

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    public void startLocationUpdates(final int LOCATION_PERMISSION_REQUEST_CODE) {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(mActivity, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                    }
                })
                .addOnFailureListener(mActivity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(mActivity,
                                            LOCATION_PERMISSION_REQUEST_CODE);


                                } catch (IntentSender.SendIntentException ignored) {

                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                break;

                        }

                    }
                });
    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

}
