package com.wzx.service;

/**
 * @author arthurwang
 * @date 2019-08-01
 */
public interface MsgService {

    String setMsg(String key, String msg);

    String getMsg(String key);
}
