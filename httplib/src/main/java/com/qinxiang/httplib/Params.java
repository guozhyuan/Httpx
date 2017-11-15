package com.qinxiang.httplib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 请求参数构造对象
 * @author yanbin
 */
public class Params implements IParams {

    private HashMap<String, String> paramsMap;

    public Params()
    {
        paramsMap = new HashMap<>();
    }

    @Override
    public IParams put(String key, Object value)
    {
        paramsMap.put(key, value.toString());
        return this;
    }

    @Override
    public String appendToUrl(String url)
    {
        StringBuffer sb = new StringBuffer(url).append("?");
        Iterator it = paramsMap.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String)entry.getKey();
            String val = (String)entry.getValue();
            sb.append(key).append("=").append(val).append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }


    @Override
    public HashMap<String, String> getKeyValuePair() {
        return paramsMap;
    }

    public String get(String key ){
        return paramsMap.get(key);
    }

}
