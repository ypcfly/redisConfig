package com.ypc.learn.redis.constant;

/**
 * @Author: ypcfly
 * @Date: 19-5-18 19:54
 * @Description: 语言类型枚举类
 */
public enum LanguageTypeEnum {

    SIMPLIFIED_CHINESE("SIMPLIFIED_CHINESE",1),
    TRADITIONAL_CHINESE("TRADITIONAL_CHINESE",2),
    ENGLISH("ENGLISH",3);

    private String name;

    private Integer value;

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    LanguageTypeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getNameByValue(Integer value) {
        if (value != null) {
            for (LanguageTypeEnum typeEnum : LanguageTypeEnum.values()) {
                if (typeEnum.value.equals(value)) {
                    return typeEnum.name;
                }
            }
        }
        return null;
    }

}

