package baseTools.retrofit2base;

import android.text.TextUtils;

import com.doudui.rongegou.BuildConfig;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class retrofit_single {
    private volatile static retrofit_single instence;

    public static retrofit_single getInstence() {
        if (instence == null) {
            synchronized (retrofit_single.class) {
                if (instence == null) {
                    instence = new retrofit_single();
                }
            }
        }
        return instence;
    }

    public retro_intf getserivce(int baseUrlType) {
        retro_intf service;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(300, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = builder.addInterceptor(loggingInterceptor).build();
// https://api.rongegou.net  http://csapi.rongegou.net
        if (baseUrlType == 1) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASEURL) //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(ScalarsConverterFactory.create()) //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(retro_intf.class);
        } else {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASEURL) //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(ScalarsConverterFactory.create()) //增加返回值为Gson的支持(以实体类返回)
                    .client(okHttpClient)

                    .addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(retro_intf.class);

        }
        return service;
    }

    public Map retro_postParameter() {//post的参数
        HashMap<String, String> maps = new HashMap<>();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        maps.put("timestamp", time);
        maps.put("sign", md5("vLr*1AdZvCC" + time));
        maps.put("openid", "123123423424324");
//        maps.put("uid", "");
//        maps.put("jpush_registration_id", "");
//        maps.put("app_type", "android");
//        maps.put("channel", "yyh");
        return maps;
    }

    public String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
