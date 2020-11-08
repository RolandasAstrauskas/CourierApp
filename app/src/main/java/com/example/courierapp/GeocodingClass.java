package com.example.courierapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class GeocodingClass {

    static String Lng, Lat;
    private static Geocoder coder;


    public static LatLng getLocationFromAddress(Context context, String strAddress) {

        if(coder == null) {
            coder = new Geocoder(context);
        }

        LatLng p1 = null;

        try {
            List<Address> address = coder.getFromLocationName(strAddress, 5);

            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            Lng = String.valueOf(location.getLongitude());
            Lat = String.valueOf(location.getLatitude());

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return p1;

    }

}
