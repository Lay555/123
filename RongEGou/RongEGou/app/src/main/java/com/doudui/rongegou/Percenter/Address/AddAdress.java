package com.doudui.rongegou.Percenter.Address;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import baseTools.BaseActivity_;
import baseTools.DaoHang_top;
import baseTools.configParams;
import baseTools.paramsDataBean;
import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAdress extends BaseActivity_ implements quyussq.setSSq {

    @BindView(R.id.addadded_name)
    EditText addaddedName;
    @BindView(R.id.addadded_pho)
    EditText addaddedPho;
    @BindView(R.id.addadded_idcard)
    EditText addaddedIdcard;
    @BindView(R.id.addaddte_dizhi)
    TextView addaddteDizhi;
    @BindView(R.id.addaddlin_dizhi)
    LinearLayout addaddlinDizhi;
    @BindView(R.id.addadded_dizhi)
    EditText addaddedDizhi;
    @BindView(R.id.addaddte_teadd)
    TextView addaddteTeadd;
    @BindView(R.id.addaddzdy_dh)
    DaoHang_top addaddzdyDh;

    String userid = "", areainfo = "", address = "", addressdeta = "", tel_phone = "", idcardno = "", addressid = "", name = "", isadd = "0";
    quyussq quyussq = new quyussq();

    @Override
    protected void AddView() {
        userid = getSharePre("user_id", this);
        Intent i = getIntent();
        String tit = i.getStringExtra("name");
        if (tit.equals("添加地址"))
            addaddzdyDh.settext_("添加收货地址");
        else {
            addaddzdyDh.settext_("编辑收货地址");
//            i.putExtra("value", data.getId() + "," + data.getName() + "," + data.getPho() + "," + data.getSfz() + ","
//                    + data.address + "," + data.getAddressDetail() + "," + data.getAddressbhao());
            String[] data = i.getStringExtra("value").split(",");
            address = data[4];
            addressdeta = data[5];
            areainfo = data[6];
            tel_phone = data[2];
            idcardno = data[3];
            addressid = data[0];
            name = data[1];
            addaddedName.setText(name);
            addaddedPho.setText(tel_phone);
            addaddedIdcard.setText(idcardno);
            addaddedDizhi.setText(addressdeta);
            addaddteDizhi.setText(address);
            isadd = "1";
            addaddteTeadd.setText("修改地址");
        }

    }

    @Override
    protected void SetViewListen() {

        addaddteTeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(addaddedName.getText().toString())) {
                    toaste_ut(AddAdress.this, "请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(addaddedPho.getText().toString()) || addaddedPho.getText().toString().length() < 11) {
                    toaste_ut(AddAdress.this, "请输入11位正确的手机号");
                    return;
                }
//                if ((addaddedIdcard.getText().toString().length() == 15) || (addaddedIdcard.getText().toString().length() == 18)) {
                if (addaddteDizhi.getText().toString().equals("所在地区")) {
                    toaste_ut(AddAdress.this, "请选择所在区域");
                    return;
                }
                if (TextUtils.isEmpty(addaddedDizhi.getText().toString())) {
                    toaste_ut(AddAdress.this, "请输入详细地址");
                    return;
                }
                getdata();
//                } else toaste_ut(AddAdress.this, "请输入正确的身份证号码");

            }
        });
        addaddteDizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!quyussq.isAdded()) {
                    quyussq.show(getFragmentManager(), "ssq");
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_adress;
    }

    @OnClick(R.id.addaddte_teadd)
    public void onViewClicked() {

    }

    public void getdata() {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "adrd");
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", userid);
            if (isadd.equals("1"))
                jsonObject1.put("address_id", addressid);
            jsonObject1.put("Receiver", addaddedName.getText().toString());
            jsonObject1.put("areainfo", addaddteDizhi.getText().toString());
            jsonObject1.put("address", addaddedDizhi.getText().toString());
            jsonObject1.put("areano", areainfo);
            jsonObject1.put("tel_phone", addaddedPho.getText().toString());
            jsonObject1.put("idcardno", addaddedIdcard.getText().toString());
            jsonObject.put("Data", jsonObject1);
            if (isadd.equals("0"))
                jsonObject.put("MethodName", "AddAddress");
            else
                jsonObject.put("MethodName", "EditAddress");
            jsonObject.put("ModuleName", "UserAccount");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        System.out.println(jsonObject.toString());
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString());
        Call<ResponseBody> call = serivce.getData(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (jdt.isAdded()) jdt.dismiss();
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("Status").equals("1")) {
                        paramsDataBean databean = new paramsDataBean();
                        databean.setMsg(configParams.dizhilist);
                        EventBus.getDefault().post(databean);
                        finish();
                        overridePendingTransition(R.anim.push_right_out,
                                R.anim.push_right_in);
                        //
                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata();
                    } else
                        Toast.makeText(AddAdress.this, jsonObject.getString("Details"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (jdt.isAdded()) jdt.dismiss();
            }
        });
    }

    public boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    @Override
    public void setSSq(String ids, String ssqstr) {
        //获取省市区id与字符串
        addaddteDizhi.setText(ssqstr);
        areainfo = ids.split(",")[2];

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
