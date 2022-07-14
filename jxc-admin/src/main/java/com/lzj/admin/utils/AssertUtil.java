package com.lzj.admin.utils;

import com.lzj.admin.exceptions.ParamsException;

public class AssertUtil {
    /**
     * 判断
     * @param flag
     * @param msg
     */
    public static void isTrue(Boolean flag, String msg) {
        if (flag) {
            throw new ParamsException(msg);
        }
    }

}
