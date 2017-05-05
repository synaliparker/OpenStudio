package edu.mills.openstudio;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("get_all_studios.php")
    Call<StudioResponse> getAllStudios();

    @GET("get_studio_details.php")
    Call<StudioResponse> getStudioDetails(@Query("id") String id);

    @POST("add_studio.php")
    Call<StudioResponse> addStudio(@Body Studio studio);

    @POST("register.php")
    Call<UserResponse> register(@Body User user);

    @POST("userlogin.php")
    Call<UserResponse> login(@Body User user);
}
