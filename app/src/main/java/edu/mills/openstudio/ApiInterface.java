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
    public static final String ALL_STUDIOS_PHP = "get_all_studios.php";
    public static final String STUDIO_DETAILS_PHP = "get_studio_details.php";
    public static final String ADD_STUDIO_PHP = "add_studio.php";
    public static final String NAME = "name";
    public static final String OWNER = "owner";
    public static final String TYPE = "type";
    public static final String ADDRESS = "address";
    public static final String CONTACT_INFO = "contact info";
    public static final String AVAILABILITY = "availability";
    public static final String ACCESSIBILITY = "accessibility";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lng";
    public static final String REGISTER_PHP = "register.php";
    public static final String PASSWORD = "password";
    public static final String USER_LOGIN_PHP = "userlogin.php";
    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String EMAIL = "email";

    @GET(ALL_STUDIOS_PHP)
    Call<StudioResponse> getAllStudios();

    @GET(STUDIO_DETAILS_PHP)
    Call<StudioResponse> getStudioDetails(@Query(ID) String id);

    @FormUrlEncoded
    @POST(ADD_STUDIO_PHP)
    Call<StudioResponse> addStudio(
            @Field(NAME) String name,
            @Field(OWNER) String owner,
            @Field(TYPE) String type,
            @Field(ADDRESS) String address,
            @Field(CONTACT_INFO) String contactInfo,
            @Field(AVAILABILITY) String availability,
            @Field(ACCESSIBILITY) String accessibility,
            @Field(DESCRIPTION) String description,
            @Field(LATITUDE) String lat,
            @Field(LONGITUDE) String lng);

    @FormUrlEncoded
    @POST(REGISTER_PHP)
    Call<UserResponse> register(
            @Field(NAME) String name,
            @Field(EMAIL) String email,
            @Field(PASSWORD) String password);

    @FormUrlEncoded
    @POST(USER_LOGIN_PHP)
    Call<UserResponse> login(@Field(EMAIL) String email,
                             @Field(PASSWORD) String password);
}
