package demo.demopjt.retrofit;

import java.util.Map;

import demo.demopjt.ModelClass.CategoryMain;
import demo.demopjt.ModelClass.ProductlistMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {



    @GET("backend/api/api/CategoryLists")
    Call<CategoryMain> getCategorylist();

    @FormUrlEncoded
    @POST("backend/api/api/ProductList")
    Call<ProductlistMain> getProductList(@FieldMap Map<String, String> map);
}