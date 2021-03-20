package com.course.server.enums;

public enum  SectionChargeEnum {
    CHARGVE("C","收费"),
    FREE("F","免费");
    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    SectionChargeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SectionChargeEnum getEnum(String value) {

        SectionChargeEnum sectionChargeEnum = null;

        SectionChargeEnum[] enumAry = SectionChargeEnum.values();

        for (int i = 0; i < enumAry.length; i++) {

            if (enumAry[i].getCode().equals(value)) {
                sectionChargeEnum = enumAry[i];
                break;
            }
        }
        return sectionChargeEnum;
    }
}
