package top.vchar.demo.spring.exception;

/**
 * <p> 自定义异常 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/10 21:45
 */
public class MyException  extends RuntimeException{

    private String code;
    private String message;

    public MyException(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
