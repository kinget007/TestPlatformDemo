package com.king.yyl.service.utils.verify.check;

import com.jayway.jsonpath.JsonPath;
import com.king.yyl.domain.apis.custom.CheckMethod;
import com.king.yyl.domain.apis.custom.Check;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/10/9.
 */
public class CheckRuleImpl {

    private Check check;

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public CheckRuleImpl(){}

    public CheckRuleImpl(Check check){
        this.check = check;
    }

    public void getResult(String response){
        String fieldName = check.getField();
        List list = new ArrayList<>();
        try{
            JSONObject jsonObject = JSONObject.fromObject(response);
//            JSONObject jsonObject = new JSONObject(response);
            list = JsonPath.read(response, "$.."+fieldName);
        }catch (Exception e){
            list.add(response);
        }

        if(check.getCheckMethod().equals(CheckMethod.Save)){
            if(list.size()==0){
                check.setExpectValue("");
            }else{
                check.setExpectValue(list.get((int)(Math.random()*list.size())).toString());
            }
        }else if(check.getCheckMethod().equals(CheckMethod.Contain)){
            for(int i=0;i<list.size();i++){
                if(list.get(i).toString().contains(check.getExpectValue())){
                    check.setStatus("OK");
                }else{
                    check.setStatus("KO");
                }
            }

        }else if(check.getCheckMethod().equals(CheckMethod.IsNotContain)){
            for(int i=0;i<list.size();i++){
                if(list.get(i).toString().contains(check.getExpectValue())){
                    check.setStatus("KO");
                }else{
                    check.setStatus("OK");
                }
            }

        }else if(check.getCheckMethod().equals(CheckMethod.Equal)){
            for(int i=0;i<list.size();i++){
                if(list.get(i).toString().equals(check.getExpectValue())){
                    check.setStatus("OK");
                }else{
                    check.setStatus("KO");
                }
            }

        }else if(check.getCheckMethod().equals(CheckMethod.IsNull)){
            for(int i=0;i<list.size();i++){
                if(list.get(i) == null){
                    check.setStatus("OK");
                }else{
                    check.setStatus("KO");
                }
            }

        }else if(check.getCheckMethod().equals(CheckMethod.HasItem)){
            if(list.size()==0){
                check.setStatus("KO");
            }else{
                check.setStatus("OK");
            }

        }else if(check.getCheckMethod().equals(CheckMethod.DoNotHasItem)){
            if(list.size()==0){
                check.setStatus("OK");
            }else{
                check.setStatus("KO");
            }
        }
    }
}
