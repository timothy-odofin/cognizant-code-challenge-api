package cognizant.com.codechallenge.dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author OdofinTimoty
 * @param <T>
 */

public class ApiResultSet<T> {
    private String message;
    private String code;
    private T data;
    private Map<String, Object> meta = new HashMap<>();

    public ApiResultSet() {
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public ApiResultSet addMeta(String key, Object value){
        meta.put(key, value);
        return this;
    }
    public ApiResultSet(String message, T data) {
        this.message = message;
        this.data = data;
    }
    public ApiResultSet(String message, String code, T data) {
        this.message = message;
        this.data = data;
        this.code = code;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}