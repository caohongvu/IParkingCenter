package net.cis.exception;

/**
 * Created by Vincent 01/12/2017
 */
public enum CisErrors {

    
    CIS_ITEMS_DUPLIATED(80001),
    CIS_ITEM_REQUIRED_FIELD_MISSED(80002),
    CIS_ITEMS_NOT_FOUND(80003),
    CIS_ITEMS_VOIDED(80004),
    ;

    private Integer code;

    CisErrors(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
