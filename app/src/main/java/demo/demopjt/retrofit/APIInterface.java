package demo.demopjt.retrofit;

import android.database.Observable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import demo.demopjt.ModelClass.CategoryMain;
import demo.demopjt.ModelClass.ProductlistMain;
import demo.demopjt.ModelClass.SimpleMessageStatusResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface APIInterface {



    @GET("backend/api/api/CategoryLists")
    Call<CategoryMain> getCategorylist();

    @FormUrlEncoded
    @POST("backend/api/api/ProductList")
    Call<ProductlistMain> getProductList(@FieldMap Map<String, String> map);

    @Multipart
    @POST("backend/api/api/storevisit")
    Call<SimpleMessageStatusResponse> addPhoto(@Part("nSalesPersonId") RequestBody id, @Part MultipartBody.Part image);

    @PUT("/api/internal/labels/{id}")
    Call<SimpleMessageStatusResponse> updateLabel(@Header("Authorization") String sessionIdAndToken, @Body HashMap<String, Object> map, @Path("id") String id);

}