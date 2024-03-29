package com.epita.mti.androidvelibmti.WS;

import com.epita.mti.androidvelibmti.DBO.VelibObject;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by William on 12/05/2017.
 */

public interface VelibService {
    String ENDPOINT = "https://opendata.paris.fr/";

    @GET("api/records/1.0/search/?dataset=stations-velib-disponibilites-en-temps-reel&rows=100&facet=banking&facet=bonus&facet=status&facet=contract_name")
    Call<VelibObject> listStation();

}
