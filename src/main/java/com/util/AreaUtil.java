package com.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/4/28.
 */
public class AreaUtil {

    /**
     *
     * @param log   精度
     * @param lat   维度
     * @return
     */
    public static Map getAdd(String log, String lat ){
        //lat 小  log  大
        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+log+"&type=010";
        String res = "";
        System.out.println(res);
        Map data = JsonUtils.toObject(res,Map.class);
        List<Map> addrList = (List) data.get("addrList");
        String admName = String.valueOf(addrList.get(0).get("admName"));
        String[] split = admName.split(",");
        Map result = new HashMap();
        result.put("province",split[0]);
        result.put("city",split[1]);
        result.put("area",split[2]);
        return result;
    }
}
