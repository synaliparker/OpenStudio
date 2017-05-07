package edu.mills.openstudio;

import java.util.Map;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("get_all_studios.php")
    Call<StudioResponse> getAllStudios();

    @GET("get_studio_details.php")
    Call<StudioResponse> getStudioDetails(@Query("id") String id);

    @FormUrlEncoded
    @POST("add_studio.php")
    Call<StudioResponse> addStudio(
            @Field("name") String name,
            @Field("owner") String owner,
            @Field("type") String type,
            @Field("address") String address,
            @Field("contact_info") String contactInfo,
            @Field("availability") String availability,
            @Field("accessibility") String accessibility,
            @Field("description") String description,
            @Field("lat") String lat,
            @Field("lng") String lng);

    @FormUrlEncoded
    @POST("register.php")
    Call<UserResponse> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
            //@Body Object user)
    );

    @FormUrlEncoded
    @POST("userlogin.php")
    Call<UserResponse> login(@Body User user);
}
