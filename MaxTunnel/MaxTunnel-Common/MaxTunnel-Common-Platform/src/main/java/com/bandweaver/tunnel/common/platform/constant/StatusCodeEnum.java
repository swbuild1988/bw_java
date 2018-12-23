package com.bandweaver.tunnel.common.platform.constant;

public enum StatusCodeEnum {

    /*
     * 一般状态
     */
	/**请求成功*/
    S_200("200", "请求成功"),
    /**登录成功*/
    S_10002("10002", "登录成功"),
    /**登出成功*/
    S_10003("10003", "登出成功"),

    /*
     * 错误信息
     * */
    /**请求处理异常，请稍后再试*/
    E_400("400", "请求处理异常，请稍后再试"),
    /**请求方式有误,请检查 GET/POST*/
    E_500("500", "请求方式有误,请检查 GET/POST"),
    /**请求路径不存在*/
    E_501("501", "请求路径不存在"),
    /**权限不足*/
    E_502("502", "权限不足"),
    /**未登录*/
    E_503("503", "未登录"),

    /**用户名不存在，请检查*/
    E_20006("20006", "用户名不存在，请检查"),
    /**密码错误，请检查*/
    E_20007("20007", "密码错误，请检查"),
    /**角色删除失败,尚有用户属于此角色*/
    E_20008("20008", "角色删除失败,尚有用户属于此角色"),
    /**账户已存在*/
    E_20009("20009", "账户已存在"),

    /**登陆已过期,请重新登陆*/
    E_20011("20011", "登陆已过期,请重新登陆"),
    /**缺少必填参数*/
    E_90003("90003", "缺少必填参数"),
	
	
    
	/**操作数据库失败*/
	DB_FAILED("ERR-001","操作数据库失败"),
	/**IO异常*/
	IO_EXP("ERR-002","properties配置文件读取失败"),
	/**尚未登录*/
	NOT_LOGIN("ERR-003","尚未登录");

	
    private String Code;

    private String Msg;

    StatusCodeEnum() {
    }

    StatusCodeEnum(String code, String msg) {
        this.Code = code;
        this.Msg = msg;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        this.Code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        this.Msg = msg;
    }
}
