package com.doudui.rongegou.Percenter.Address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addressAdapter extends RecyclerView.Adapter<addressAdapter.viewholder> {
    Context context;
    List<AddressData> list;
    itemoncl itemoncl;
    deteladdressit deteladdressit;
    String userid = "";
    SharedPreferences preferences;

    public addressAdapter(Context context,
                          List<AddressData> list) {
        this.context = context;
        this.list = list;
    }


    public void setdetelonc(deteladdressit deteladdressit){
        this.deteladdressit = deteladdressit;
    }
    public void setoncl(itemoncl itemoncl) {
        this.itemoncl = itemoncl;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.addressadapter, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {
        final AddressData data = list.get(position);

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        userid = preferences.getString("user_id", userid);
        holder.addressitTename.setText(data.getName());
        holder.addressitTepho.setText(data.getPho());
        holder.addressitTeaddress.setText(data.getAddress() + "  " + data.getAddressDetail());

        if (!data.getViscz().equals("0"))
            holder.lin_cz.setVisibility(View.GONE);

        if (data.getIsMoren().equals("1"))
            holder.dhImmoren.setSelected(true);
        else holder.dhImmoren.setSelected(false);

        holder.addressitTemoren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.dhImmoren.isSelected()) {
                    getdata(data.getId(), userid, position);
                }
            }
        });
        holder.dhImmoren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.dhImmoren.isSelected()) {
                    getdata(data.getId(), userid, position);
                }
            }
        });
        holder.addressitTebianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddAdress.class);
                i.putExtra("value", data.getId() + "," + data.getName() + "," + data.getPho() + "," + data.getSfz() + ","
                        + data.address + "," + data.getAddressDetail() + "," + data.getAddressbhao());
                i.putExtra("name", "编辑地址");
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        holder.dhImbianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddAdress.class);
                i.putExtra("value", data.getId() + "," + data.getName() + "," + data.getPho() + "," + data.getSfz() + ","
                        + data.address + "," + data.getAddressDetail() + "," + data.getAddressbhao());
                i.putExtra("name", "编辑地址");
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        holder.dhIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deteladdressit.deteladdressit(data.getId(), userid, position);
            }
        });
        holder.addressitTe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deteladdressit.deteladdressit(data.getId(), userid, position);
            }
        });
        holder.lin_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemoncl.itemoncl(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public interface deteladdressit{
        void deteladdressit(String id,  String user_id,  int pos);
    }


    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.addressit_tename)
        TextView addressitTename;
        @BindView(R.id.addressit_tepho)
        TextView addressitTepho;
        @BindView(R.id.addressit_teaddress)
        TextView addressitTeaddress;
        @BindView(R.id.dh_immoren)
        ImageView dhImmoren;
        @BindView(R.id.addressit_temoren)
        TextView addressitTemoren;
        @BindView(R.id.dh_imbianji)
        ImageView dhImbianji;
        @BindView(R.id.addressit_tebianji)
        TextView addressitTebianji;
        @BindView(R.id.dh_imsc)
        ImageView dhIm;
        @BindView(R.id.addressit_tesc)
        TextView addressitTe;
        @BindView(R.id.addressit_linall)
        LinearLayout lin_bot;
        @BindView(R.id.dh_lincz)
        LinearLayout lin_cz;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface itemoncl {
        void itemoncl(int pos);
    }


    public void getdata(final String id, final String user_id, final int pos) {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", user_id);
            jsonObject1.put("address_id", id);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "SetDefaultAddress");
            jsonObject.put("ModuleName", "UserAccount");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString());
        Call<ResponseBody> call = serivce.getData(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
//                    System.out.println(jsonObject);
                    if (jsonObject.getString("Status").equals("1")) {
                        Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < list.size(); i++) {
                            if (i == pos) {
                                list.get(pos).setIsMoren("1");
                            } else {
                                if (list.get(i).getIsMoren().equals("1")) {
                                    list.get(i).setIsMoren("0");
                                }
                            }
                        }
                        notifyDataSetChanged();
                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata(id, user_id, pos);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
