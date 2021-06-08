package fz.cs.daoyun.domain;

import java.util.List;

public class DictInfo  {
    private Integer id;

    private String dictEng;

    public String getDictEng() {
        return dictEng;
    }

    public void setDictEng(String dictEng) {
        this.dictEng = dictEng;
    }

    private String itemKey;

    private String itemValue;

    private Boolean isdefault;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey == null ? null : itemKey.trim();
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue == null ? null : itemValue.trim();
    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }
}
