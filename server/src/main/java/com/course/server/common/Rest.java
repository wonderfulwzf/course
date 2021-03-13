package com.course.server.common;

public class Rest<T> {


    private boolean success=true;
    private T data;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Rest<T> resultSuccessInfo(){
        this.setData(null);
        this.setMessage("操作成功");
        this.setSuccess(true);
        return this;
    }

    public Rest<T> resultSuccessInfo(String message,Integer code){
        this.setData(null);
        this.setMessage(message);
        this.setSuccess(true);
        return this;
    }

    public Rest<T> resultSuccessInfo(T t){
        this.setData(t);
        this.setMessage("查询成功");
        this.setSuccess(true);
        return this;
    }
    /**
     * 操作成功，逻辑没走通，返回提示信息
     * @param message
     * @return
     */
    public static <T> Rest<T> optResultSuccessInfo(String message){
        Rest<T> result = new Rest<>();
        result.setData(null);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }

    public Rest<T> resultFailInfo(){
        this.setData(null);
        this.setMessage("查询失败");
        this.setSuccess(false);
        return this;
    }

    public Rest<T> optResultFailInfo(){
        this.setData(null);
        this.setMessage("操作失败");
        this.setSuccess(false);
        return this;
    }

    public Rest<T> resultRequesRefuse(){
        this.setData(null);
        this.setMessage("请求无效");
        this.setSuccess(false);
        return this;
    }

    public Rest<T> resultRequesRefuse(String message){
        this.setData(null);
        this.setMessage(message);
        this.setSuccess(false);
        return this;
    }

    /**sucess 单例
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Rest<T> success(T t) {
        Rest<T> result = new Rest<>();
        result.setMessage("查询成功");
        result.setData(t);
        result.setSuccess(true);
        return result;
    }

    /**
     * fail 单例
     * @param <T>
     * @return
     */
    public static <T> Rest<T> resultFail(){
        Rest<T> rest=new Rest<>();
        rest.setData(null);
        rest.setMessage("查询失败");
        rest.setSuccess(false);
        return rest;
    }

    /** 请求拒绝
     *
     * @param <T>
     * @return
     */
    public static <T> Rest<T> resultRefuse(){
        Rest<T> rest=new Rest<>();
        rest.setData(null);
        rest.setMessage("请求拒绝");
        rest.setSuccess(false);
        return rest;
    }

    /**手动构建Rest 以便后面方法配合使用
     *
     * @param <T>
     * @return
     */
    public static <T>Rest<T> build() {

        return new Rest<>();
    }

    /**配合 build 使用
     *
     * @param message
     * @return
     */
    public Rest<T> msg(String message){
        this.setMessage(message);
        return this;
    }


    /**
     * 配合 build 使用
     * @param data
     * @return
     */
    public Rest<T> loadData(T data){
        this.setData(data);
        return this;
    }

    /**
     *
     * @param success
     * @return
     */
    public Rest<T> checkSuccess(boolean success){
        this.setSuccess(success);
        return this;
    }


    public Rest<T> resultSuccess(String message,T data){
        this.setData(data);
        this.setMessage(message);
        this.setSuccess(true);
        return this;
    }

    public Rest<T> resultSuccess(String message){
        this.setData(null);
        this.setMessage(message);
        this.setSuccess(true);
        return this;
    }

    public Rest<T> resultFail(String message,T data){
        this.setData(data);
        this.setMessage(message);
        this.setSuccess(false);
        return this;
    }

    public Rest<T> resultFail(String message){
        this.setData(null);
        this.setMessage(message);
        this.setSuccess(false);
        return this;
    }

}
