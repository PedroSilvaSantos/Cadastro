package br.com.caelum.cadastro;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by android6406 on 24/06/16.
 */
public class AtualizadorDeLocalizacao implements LocationListener {
    private GoogleApiClient client;
    public AtualizadorDeLocalizacao(Context ctx){
        Configurador configurador = new Configurador(this);
        this.client = new GoogleApiClient.Builder(ctx).addApi(LocationServices.API).addConnectionCallbacks(configurador).build();
                this.client.connect();
        }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng local = new LatLng(latitude,longitude);
    }


    public void inicia(LocationRequest request){
        LocationServices.FusedLocationApi.requestLocationUpdates(client,request,this);

    }
    public void cancela(){
        LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        this.client.disconnect();
    }
}
