package com.epita.mti.androidvelibmti.WS;

import com.epita.mti.androidvelibmti.DBO.Station;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by William on 12/05/2017.
 */

public interface VelibService {
    String ENDPOINT = "http://www.tutos-android.com/";

    @GET("MTI/2018/TP3.json")
    Call<List<Station>> listStation();
}
