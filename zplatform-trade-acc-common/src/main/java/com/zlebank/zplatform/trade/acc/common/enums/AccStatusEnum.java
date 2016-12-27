package com.zlebank.zplatform.trade.acc.common.enums;


public enum AccStatusEnum {
    /**
     * 00-处理完成，01-未划拨，02-划拨完成，等待账务处理，03-账户处理中，04-账务处理失败
     */
    
    waitToTransfer("01"),
    waitToAccounting("02"),
    Accounting("03"),
    AccountingFail("04"),
    Finish("00"),
    UNKNOW("99");
    private String code;
    
    private AccStatusEnum(String code){
        this.code = code;
    }
    
    public static AccStatusEnum fromValue(String value) {
        for(AccStatusEnum status:values()){
            if(status.code==value){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public String getCode(){
        return code;
    }
}
