package com.project.appakarrandu.Api;

public interface ApiInterface {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("dataWarga")
    Call<ResponseModel> ardSelectData(@Header ("Authorization") String token,
                                      @Query("keldesa") String keldesa);
}
