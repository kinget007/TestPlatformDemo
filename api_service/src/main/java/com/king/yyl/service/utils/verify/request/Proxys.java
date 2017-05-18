package com.king.yyl.service.utils.verify.request;

import com.king.yyl.domain.apis.custom.Proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 16/10/12.
 */
public class Proxys {

    public Map<String, Proxy> getProxys() {
        return proxys;
    }

    public void setProxys(Map<String, Proxy> proxys) {
        this.proxys = proxys;
    }

    protected   Map<String,Proxy> proxys = new HashMap<>();

    public Proxys(){
        proxys.put("host",new Proxy("host",8080,"",""));
    }

}
