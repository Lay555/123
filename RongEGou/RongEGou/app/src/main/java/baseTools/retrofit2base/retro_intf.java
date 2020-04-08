package baseTools.retrofit2base;

import com.doudui.rongegou.LoginAct.AesUtil;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static com.doudui.rongegou.LoginAct.AesUtil.*;

public interface retro_intf {
    //@FieldMap用于post    @Query用于get请求
    @Multipart
    @POST("/Client.aspx?act=upload&token=9BGCJEqDC90JrFRjiBnTpA==" )
    Call<ResponseBody> upload(@Part MultipartBody.Part part);
    @POST("/Client.aspx")
    Call<ResponseBody> getData( @Body RequestBody requestBody);

}