package edu.mills.openstudio;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Interface for GET and POST API calls.
 */
interface ApiInterface {
    /**
     * Name of a column in the studios table and users table that holds the studio or user name.
     */
    String NAME = "name";
    /**
     * Name of the column in the studios table that holds the studio owner's name.
     */
    String OWNER = "owner";
    /**
     * Name of the column in the studios table that holds the studio's type.
     */
    String TYPE = "type";
    /**
     * Name of the column in the studios table that holds the studio's address.
     */
    String ADDRESS = "address";
    /**
     * Name of the column in the studios table that holds the studio's contact info (email).
     */
    String CONTACT_INFO = "contact info";
    /**
     * Name of the column in the studios table the holds the studio's availability.
     */
    String AVAILABILITY = "availability";
    /**
     * Name of the column in the studios table that holds the studio's accessibility details.
     */
    String ACCESSIBILITY = "accessibility";
    /**
     * Name of the column in the studios table that holds the studio's description.
     */
    String DESCRIPTION = "description";
    /**
     * Name of the column in the studios table that holds the studio location's latitude
     */
    String LAT = "lat";
    /**
     * Name of the column in the studios table that holds the studio location's longitude.
     */
    String LNG = "lng";
    /**
     * Name of the column in the users table that holds the user's hashed password.
     */
    String PASSWORD = "password";
    /**
     * Name of the column in the studios table that holds the unique row identifier.
     */
    String ID = "id";
    /**
     * Name of the column in the users table that holds the user's email.
     */
    String EMAIL = "email";

    @GET("get_all_studios.php")
    Call<StudioResponse> getAllStudios();

    @GET("get_studio_details.php")
    Call<StudioResponse> getStudioDetails(@Query(ID) String id);

    @FormUrlEncoded
    @POST("add_studio.php")
    Call<StudioResponse> addStudio(
            @Field(NAME) String name,
            @Field(OWNER) String owner,
            @Field(TYPE) String type,
            @Field(ADDRESS) String address,
            @Field(CONTACT_INFO) String contactInfo,
            @Field(AVAILABILITY) String availability,
            @Field(ACCESSIBILITY) String accessibility,
            @Field(DESCRIPTION) String description,
            @Field(LAT) String lat,
            @Field(LNG) String lng);

    @FormUrlEncoded
    @POST("register.php")
    Call<UserResponse> register(
            @Field(NAME) String name,
            @Field(EMAIL) String email,
            @Field(PASSWORD) String password);

    @FormUrlEncoded
    @POST("userlogin.php")
    Call<UserResponse> login(@Field(EMAIL) String email,
                             @Field(PASSWORD) String password);
}