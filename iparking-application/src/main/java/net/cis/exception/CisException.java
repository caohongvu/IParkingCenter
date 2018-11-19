package net.cis.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent 22/05/2018
 */
public class CisException extends RuntimeException {

    private static final long serialVersionUID = -7440479244526424143L;
    private CisErrors error;
    private Integer code;
    private List<Object> dto;
    private List<String> hints;

    public CisException(CisErrors error) {
        super(error.name());
        this.error = error;
        this.code = error.getCode();
    }

    public CisErrors getError() {
        return error;
    }

    public void setError(CisErrors error) {
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public CisException setCode(Integer code) {
        this.code = code;
        return this;
    }

    public List<Object> getDto() {
        return dto;
    }

    public CisException setDto(Object dto) {
        if (this.dto == null) {
            this.dto = new ArrayList<Object>();
        }
        this.dto.add(dto);
        return this;
    }

    public List<String> getHints() {
        return hints;
    }

    public CisException setHint(String hint) {
        if (this.hints == null) {
            this.hints = new ArrayList<String>();
        }
        this.hints.add(hint);
        return this;
    }
}
