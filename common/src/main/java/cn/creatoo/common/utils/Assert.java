package cn.creatoo.common.utils;

import cn.creatoo.common.exception.UserReadableException;

/**
 * Created by Administrator on 2017/8/3 0003.
 */
public class Assert {

    public  static void notNull(Object obj,String msg){
        if(obj==null){
            throw  new UserReadableException(msg);
        }
    }
    public  static void notEmpty(String obj,String msg){
        if(obj==null ||obj.trim().equals("")){
            throw  new UserReadableException(msg);
        }
    }

}
