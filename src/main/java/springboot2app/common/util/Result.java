package springboot2app.common.util;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable {

    @SuppressWarnings("unused")
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Result.class);

    private static final long serialVersionUID = -1802122468331526708L;
    private int status = -1;
    private String message = "待处理";
    private Map<String, Object> data = new HashMap<String, Object>();

    public Result() {
    }

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static Result of(Page pagination, Integer page) {
        Result ins = new Result(200, "ok");
        ins.data.put("page", page);
        ins.data.put("list", pagination.getContent());
        ins.data.put("size", pagination.getSize());
        ins.data.put("total", pagination.getTotalElements());
        return ins;
    }

    public static Result of(String key, Object data) {
        Result ins = new Result(200, "ok");
        ins.putData(key, data);
        return ins;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void putData(String key, Object value) {
        data.put(key, value);
    }

    public void removeData(String key) {
        data.remove(key);
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
