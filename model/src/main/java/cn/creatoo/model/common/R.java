package cn.creatoo.model.common;

import java.util.HashMap;

public class R extends HashMap<String,Object>{

    private final static String RESULT_STATUS_KEY = "status";
    private final static String RESULT_OK_VAL = "ok";
    private final static String RESULT_ERROR_VAL = "error";
    private final static String RESULT_UNLOGIN_VAL = "unlogin";


    public static R ok() {
        R rs = new R();
        rs.put(RESULT_STATUS_KEY, RESULT_OK_VAL);
        return rs;
    }
    public static R error() {
        R rs = new R();
        rs.put(RESULT_STATUS_KEY, RESULT_ERROR_VAL);
        return rs;
    }
    public static R Unlogin() {
         R rs = new R();
         rs.put(RESULT_STATUS_KEY, RESULT_UNLOGIN_VAL);
        return rs;
    }

    public R  setDataVal(Object val){
        if(!this.containsKey(RSKeyEnum.data.name())){
            this.put(RSKeyEnum.data.name(), val);
        }else{
            throw  new RuntimeException("key："+RSKeyEnum.data.name()+"已存在");
        }
        return this;
    }

    public R  setPageVal(Object val){
        if(!this.containsKey(RSKeyEnum.page.name())){
            this.put(RSKeyEnum.page.name(), val);
        }else{
            throw  new RuntimeException("key："+RSKeyEnum.page.name()+"已存在");
        }
        return this;
    }

    public R setMsgVal(Object val){
        if(!this.containsKey(RSKeyEnum.msg.name())){
            this.put(RSKeyEnum.msg.name(), val);
        }else{
            throw  new RuntimeException("key："+RSKeyEnum.msg.name()+"已存在");
        }
        return this;
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }







}
