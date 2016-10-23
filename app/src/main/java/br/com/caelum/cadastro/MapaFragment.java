package br.com.caelum.cadastro;

import android.location.Geocoder;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by android6406 on 24/06/16.
 */
public class MapaFragment extends SupportMapFragment {

    @Override
    public void onResume(){
        AlunoDAO dao=new AlunoDAO(getActivity());
        final List<Aluno> alunos = dao.getLista();
        dao.close();
        super.onResume();

        getMapAsync(new OnMapReadyCallback(){

            @Override
            public void onMapReady(GoogleMap googleMap) {
                Geocoder geocoder = new Geocoder(getContext());


                for (Aluno a:alunos) {
                    try {
                        List<android.location.Address> endereco;

                        endereco=geocoder.getFromLocationName(a.getEndereco(),1);
                        if (!endereco.isEmpty()) {
                            android.location.Address address = endereco.get(0);
                            LatLng latLng = new  LatLng(address.getLatitude(),address.getLongitude());
                            googleMap.addMarker(new MarkerOptions().title(a.getNome()).position(latLng));

                        }


                    } catch (IOException e) {
                        e.printStackTrace();

                    }


                }

                LatLng latLng = new  LatLng(-23.588305,-46.632395);
                this.centralizadoNo(googleMap,latLng);

            }

            public void centralizadoNo(GoogleMap googleMap, LatLng latLng){
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12.05f));
            }
        });
    }
}
