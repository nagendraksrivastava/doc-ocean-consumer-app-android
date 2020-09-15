package customer.dococean.com.patient.network.api;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import customer.dococean.com.patient.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public class RetrofitProvider {

    private final int READ_TIMEOUT = 20;
    private final int CONNECTION_TIMEOUT = 20;
    private final String mBaseURl = BuildConfig.DEBUG ? "http://139.59.33.221" : "http://dococean.com";
    private DocOceanRestApi mDocOceanRestApi;
    private static RetrofitProvider ourInstance = new RetrofitProvider();

    public static RetrofitProvider getInstance() {
        return ourInstance;
    }

    private RetrofitProvider() {
        buildRestApi();
    }

    public DocOceanRestApi getRestApi() {
        return mDocOceanRestApi;
    }

    private void buildRestApi() {
        OkHttpClient.Builder okHttpClient = getOkHttpBuilder();
        okHttpClient.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(mBaseURl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient.build());
        mDocOceanRestApi = builder.build().create(DocOceanRestApi.class);
    }

    @NonNull
    private OkHttpClient.Builder getOkHttpBuilder() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(logging);
        }
        return okHttpClient;
    }

}
