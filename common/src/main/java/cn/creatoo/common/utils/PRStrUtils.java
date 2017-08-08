package cn.creatoo.common.utils;

/**
 * Created by Administrator on 2017/8/3 0003.
 */
public class PRStrUtils {
    public  static boolean  isEmpty(String s){
        if(null==s || s.trim().equals(""))
           return  true;
        return false;
    }
}
