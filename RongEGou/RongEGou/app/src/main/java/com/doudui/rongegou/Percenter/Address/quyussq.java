package com.doudui.rongegou.Percenter.Address;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class quyussq extends DialogFragment implements addyhkssqadapter.getSSQidx {
    View view;
    @BindView(R.id.addyhkssq_tetit)
    TextView addyhkssqTetit;
    @BindView(R.id.addyhkssq_imclose)
    ImageView addyhkssqTeok;
    @BindView(R.id.addyhkssq_recyc)
    RecyclerView addyhkssqRecyc;
    Unbinder unbinder;

    addyhkssqadapter addyhkssqadapter;
    addyhkssqadapter addyhkssqadapter1;
    addyhkssqadapter addyhkssqadapter2;
    List<addyhkdata> list = new ArrayList<>();
    List<addyhkdata> list1 = new ArrayList<>();
    List<addyhkdata> list2 = new ArrayList<>();

    //    Retro_Intf serivce;
    String ShenId = "000000", ShiId = "000000", QuId = "000000";
    String ssqstr1 = "", ssqstr = "", ssqstr2 = "";
    @BindView(R.id.addyhkssq_teshen)
    TextView addyhkssqTeshen;
    @BindView(R.id.addyhkssq_teshi)
    TextView addyhkssqTeshi;
    @BindView(R.id.addyhkssq_tequ)
    TextView addyhkssqTequ;
    @BindView(R.id.addyhkssq_recyc1)
    RecyclerView addyhkssqRecyc1;
    @BindView(R.id.addyhkssq_recyc2)
    RecyclerView addyhkssqRecyc2;
//    int posSSQ = 0;//0表示省1表示市2表示区

    int[] posselect = {-1, -1, -1};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        view = inflater.inflate(R.layout.addyhkssq, container, false);
        unbinder = ButterKnife.bind(this, view);
        setrecycData();
        setviewlisten();
        return view;
    }

    private void setviewlisten() {
        addyhkssqTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        addyhkssqTeshen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addyhkssqRecyc.setVisibility(View.VISIBLE);
                addyhkssqRecyc1.setVisibility(View.GONE);
                addyhkssqRecyc2.setVisibility(View.GONE);

                addyhkssqTeshen.setSelected(true);
                addyhkssqTeshi.setSelected(false);
                addyhkssqTequ.setSelected(false);
            }
        });
        addyhkssqTeshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addyhkssqRecyc.setVisibility(View.GONE);
                addyhkssqRecyc1.setVisibility(View.VISIBLE);
                addyhkssqRecyc2.setVisibility(View.GONE);

                addyhkssqTeshen.setSelected(false);
                addyhkssqTeshi.setSelected(true);
                addyhkssqTequ.setSelected(false);
            }
        });
        addyhkssqTequ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addyhkssqRecyc.setVisibility(View.GONE);
                addyhkssqRecyc1.setVisibility(View.GONE);
                addyhkssqRecyc2.setVisibility(View.VISIBLE);

                addyhkssqTeshen.setSelected(false);
                addyhkssqTeshi.setSelected(false);
                addyhkssqTequ.setSelected(true);
            }
        });
    }

    private void setrecycData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);


        addyhkssqadapter = new addyhkssqadapter(list, getActivity());
        addyhkssqadapter.setSSQidInterface(this);
        addyhkssqRecyc.setLayoutManager(layoutManager);
        addyhkssqRecyc.setAdapter(addyhkssqadapter);

        addyhkssqadapter1 = new addyhkssqadapter(list1, getActivity());
        addyhkssqadapter1.setSSQidInterface(this);
        addyhkssqRecyc1.setLayoutManager(layoutManager1);
        addyhkssqRecyc1.setAdapter(addyhkssqadapter1);

        addyhkssqadapter2 = new addyhkssqadapter(list2, getActivity());
        addyhkssqadapter2.setSSQidInterface(this);
        addyhkssqRecyc2.setLayoutManager(layoutManager2);
        addyhkssqRecyc2.setAdapter(addyhkssqadapter2);

//        System.out.println(ShenId + "  " + ShiId + "   " + QuId);
        if (ShenId.equals("000000")) {
            addyhkssqTeshen.setVisibility(View.VISIBLE);

            addyhkssqTeshen.setSelected(true);
            addyhkssqTeshi.setSelected(false);
            addyhkssqTequ.setSelected(false);

            addyhkssqTeshi.setVisibility(View.GONE);
            addyhkssqTequ.setVisibility(View.GONE);
            addyhkssqRecyc.setVisibility(View.VISIBLE);
            addyhkssqRecyc1.setVisibility(View.GONE);
            addyhkssqRecyc2.setVisibility(View.GONE);
            getdata(ShenId, ShiId, "0");

        } else {
            addyhkssqRecyc.setVisibility(View.GONE);
            addyhkssqRecyc1.setVisibility(View.VISIBLE);
            addyhkssqRecyc2.setVisibility(View.GONE);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getSelect().equals("1")) {
                    addyhkssqTeshen.setText(list.get(i).getText());
                    posselect[0] = i;
                    break;
                }
            }
            if (ShiId.equals("000000")) {
                addyhkssqTeshen.setSelected(false);
                addyhkssqTeshi.setSelected(true);
                addyhkssqTequ.setSelected(false);
                addyhkssqTeshi.setVisibility(View.VISIBLE);
                addyhkssqTequ.setVisibility(View.GONE);
                getdata(ShenId, ShiId, "1");

            } else {
                addyhkssqRecyc.setVisibility(View.GONE);
                addyhkssqRecyc1.setVisibility(View.GONE);
                addyhkssqRecyc2.setVisibility(View.VISIBLE);
                for (int i = 0; i < list1.size(); i++) {
                    if (list1.get(i).getSelect().equals("1")) {
                        addyhkssqTeshi.setText(list1.get(i).getText());
                        posselect[1] = i;
                        break;
                    }

                }
                if (QuId.equals("000000")) {

                    addyhkssqTeshen.setSelected(false);
                    addyhkssqTeshi.setSelected(false);
                    addyhkssqTequ.setSelected(true);

                    addyhkssqTeshi.setVisibility(View.VISIBLE);
                    addyhkssqTequ.setVisibility(View.VISIBLE);
                    getdata(ShenId, ShiId, "2");

                } else {
                    addyhkssqTeshen.setSelected(false);
                    addyhkssqTeshi.setSelected(false);
                    addyhkssqTequ.setSelected(true);
                    for (int i = 0; i < list2.size(); i++) {
                        if (list2.get(i).getSelect().equals("1")) {
                            addyhkssqTequ.setText(list2.get(i).getText());
                            posselect[2] = i;
                            break;
                        }
                    }
                    addyhkssqTeshen.setVisibility(View.VISIBLE);
                    addyhkssqTeshi.setVisibility(View.VISIBLE);
                    addyhkssqTequ.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));

    }

    private int getPixelsFromDp(int size) {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;

    }

    public interface setSSq {
        void setSSq(String ids, String ssqstr);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getSSQid(String id, String ssqStr, String type) {
        if (type.equals("0")) {
            ShenId = id;
            addyhkssqTeshen.setText(ssqStr);
            addyhkssqTeshi.setVisibility(View.VISIBLE);
            addyhkssqTequ.setVisibility(View.GONE);
            addyhkssqRecyc.setVisibility(View.VISIBLE);
            addyhkssqRecyc1.setVisibility(View.GONE);
            addyhkssqRecyc2.setVisibility(View.GONE);
            ssqstr = ssqStr;
            ShiId = "000000";

            addyhkssqTeshen.setSelected(false);
            addyhkssqTeshi.setSelected(true);
            addyhkssqTequ.setSelected(false);
            addyhkssqTeshi.setText("请选择");
            addyhkssqTequ.setText("请选择");

            getdata(ShenId, ShiId, "1");
        } else if (type.equals("1")) {
            ShiId = id;
            addyhkssqTeshi.setText(ssqStr);
            addyhkssqTequ.setVisibility(View.VISIBLE);
            addyhkssqRecyc.setVisibility(View.GONE);
            addyhkssqRecyc1.setVisibility(View.VISIBLE);
            addyhkssqRecyc2.setVisibility(View.GONE);

            ssqstr1 = ssqStr;
            QuId = "000000";

            addyhkssqTeshen.setSelected(false);
            addyhkssqTeshi.setSelected(false);
            addyhkssqTequ.setSelected(true);
            addyhkssqTequ.setText("请选择");

            getdata(ShenId, ShiId, "2");

        } else if (type.equals("2")) {
            if (QuId.equals("000000")) {
                QuId = id;

                addyhkssqRecyc.setVisibility(View.GONE);
                addyhkssqRecyc1.setVisibility(View.GONE);
                addyhkssqRecyc2.setVisibility(View.VISIBLE);
                addyhkssqTequ.setText(ssqStr);
                ssqstr2 = ssqStr;

                setSSq ssq = (setSSq) getActivity();
                ssq.setSSq(ShenId + "," + ShiId + "," + QuId, ssqstr + "-" + ssqstr1 + "-" + ssqstr2);
                dismiss();
            } else {
                QuId = id;
                addyhkssqTequ.setText(ssqStr);
                ssqstr2 = ssqStr;
                setSSq ssq = (setSSq) getActivity();
                ssq.setSSq(ShenId + "," + ShiId + "," + QuId, ssqstr + "-" + ssqstr1 + "-" + ssqstr2);
                dismiss();
            }
        }
    }

    public void getdata(final String shenId, final String shiId, final String type) {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("province", shenId);
            jsonObject1.put("city", shiId);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetAreainfo");
            jsonObject.put("ModuleName", "DataAccess");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString());
        Call<ResponseBody> call = serivce.getData(requestBody);
        call.enqueue(new Callback<ResponseBody>()

        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("Status").equals("1")) {
                        if (type.equals("0")) {
                            list.removeAll(list);
                            addyhkssqRecyc.setVisibility(View.VISIBLE);
                            addyhkssqRecyc1.setVisibility(View.GONE);
                            addyhkssqRecyc2.setVisibility(View.GONE);
                        } else if (type.equals("1")) {
                            list1.removeAll(list1);
                            addyhkssqRecyc.setVisibility(View.GONE);
                            addyhkssqRecyc1.setVisibility(View.VISIBLE);
                            addyhkssqRecyc2.setVisibility(View.GONE);
                        } else if (type.equals("2")) {
                            list2.removeAll(list2);
                            addyhkssqRecyc.setVisibility(View.GONE);
                            addyhkssqRecyc1.setVisibility(View.GONE);
                            addyhkssqRecyc2.setVisibility(View.VISIBLE);
                        }
                        JSONArray jsa = jsonObject.getJSONArray("Value");
                        for (int i = 0; i < jsa.length(); i++) {
                            JSONObject jso = jsa.getJSONObject(i);
                            if (type.equals("0")) {
                                list.add(new addyhkdata(jso.getString("Provinces"), jso.getString("XZMC"), "0", "0"));
                                addyhkssqadapter.notifyDataSetChanged();
                            } else if (type.equals("1")) {
                                list1.add(new addyhkdata(jso.getString("City"), jso.getString("XZMC"), "1", "0"));
                                addyhkssqadapter1.notifyDataSetChanged();
                            } else if (type.equals("2"))
                                list2.add(new addyhkdata(jso.getString("District"), jso.getString("XZMC"), "2", "0"));
                            addyhkssqadapter2.notifyDataSetChanged();
                        }
                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata(shenId, shiId, type);
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
