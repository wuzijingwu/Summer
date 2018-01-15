package text.bwei.com.summer.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by z on 2018/1/11.
 */

public class RetrofitUtils {
    private static volatile RetrofitUtils instance;
    private ApiService apiService;

    public RetrofitUtils(String baseUrl){
        OkHttpClient build = new OkHttpClient.Builder().build();
        Retrofit retrofit=new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(build)
                .build();
        apiService = retrofit.create(ApiService.class);
    }
    public static RetrofitUtils getInstance(String baseUrl){
        if(instance==null){
            synchronized (RetrofitUtils.class){
                if(null==instance){
                    instance=new RetrofitUtils(baseUrl);
                }
            }
        }
        return instance;
    }
    public ApiService getApiService(){
        return apiService;
    }
}
