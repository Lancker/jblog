package jblog.guohai.org.model;

import lombok.Data;


@Data
public class Result<T> {

    private boolean status;
    private boolean message;
    private T data;

    /**
     *
     */
    public Result(boolean status, T data) {
        this.status = status;
        this.data = data;
    }

    public Result() {
        this.status = false;
    }
    
    public static Result<String> Fail(){
    	
    	return new Result<>(false,null);
    }
}
