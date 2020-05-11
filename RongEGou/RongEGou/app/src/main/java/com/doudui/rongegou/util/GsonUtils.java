package com.doudui.rongegou.util;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gson解析json的封装类
 * Created by Administrator on 2020/5/9.
 */
public class GsonUtils {
    private static final String TAG = GsonUtils.class.getSimpleName();

    public static <T> T jsonToBean(String json, Class<T> cls) {
        Gson gson = new Gson();
        T t = gson.fromJson(json, cls);
        return t;
    }

    /**
     * 将对象转化为json
     *
     * @return
     */
    public static <T> String beanToJson(T t) {
        if (t == null) {
            return "";
        }
        Gson g = new Gson();
        return g.toJson(t);
    }

    //将list集合转化为字符串，这样使用在7.0及以上手机会出错
    private static <T> String listToJson1(List<T> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<T>>() {
        }.getType(); // 指定集合对象属性
        String beanListToJson = gson.toJson(list, type);
        return beanListToJson;
    }

    public static <T> String listToJson(List<T> list) {
        if (list == null) {
            return "[]";
        }
        StringBuilder beanListToJson = new StringBuilder();
        beanListToJson.append("[");
        int count = list.size();
        for (int i = 0; i < count; i++) {
            beanListToJson.append(beanToJson(list.get(i)));
            if (i != count - 1) {
                beanListToJson.append(",");
            }
        }
        beanListToJson.append("]");
        return beanListToJson.toString();
    }

    /**
     * new TypeToken<List<T>>(){}.getType()运用到了java反射机制
     * 下面方法有问题，原因是泛型在编译期类型被擦除导致的，所以jsonToList()这个不能用，而使用fromJsonArray()方法
     * 解决方法：http://www.zhihu.com/question/27216298
     *
     * @param jsonString
     * @param cls
     * @return
     */
    private <T> List<T> jsonFromList(String jsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }

    /**
     * 成功解决了泛型在编译期类型被擦除导致的问题
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        List<T> lst = new ArrayList<>();
        try {
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                lst.add(new Gson().fromJson(elem, clazz));
            }
        } catch (Exception e) {
        }
        return lst;
    }

    //Result<T>类里面泛型字段的名称
    private static String tName = "Data";
    /**
     * 解析实体类型为Result<T>类型的数据</>
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
//        public static <T> Result<T> jsonToResult(String json, Class<T> clazz) {
//            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
//            Result<T> tResult = new Result<>();
//            tResult.reCode = jsonObject.get("reCode").getAsString();
//            tResult.codeTxt = jsonObject.get("codeTxt").getAsString();
//            if (tResult.reCode.equals(Config.SUCCESS_CODE)) {//判断返回的值是否正确，否者不向下继续解析,因为在某些情况下继续往下解析会出错
//                Gson gson = new Gson();
//                T t = gson.fromJson(jsonObject.get("data"), clazz);
//                tResult.data = t;
//            }
//            return tResult;
//        }
    /**
     * 解析实体类型为Result<T>类型的数据</>
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
//        public static <T> Result<List<T>> jsonToResultList(String json, Class<T> clazz) {
//            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
//            Result<List<T>> tResult = new Result<>();
//            tResult.reCode = jsonObject.get("reCode").getAsString();
//            tResult.codeTxt = jsonObject.get("codeTxt").getAsString();
//            List<T> lst = new ArrayList<>();
//            for (JsonElement elem : jsonObject.get("data").getAsJsonArray()) {
//                lst.add(new Gson().fromJson(elem, clazz));
//            }
//            tResult.data = lst;
//            return tResult;
//        }
}
