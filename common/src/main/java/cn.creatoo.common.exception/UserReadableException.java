package cn.creatoo.common.exception;

/**
 * Created by Administrator on 2017/8/3 0003.
 */
public class UserReadableException extends RuntimeException{
private String message;


    public UserReadableException(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
