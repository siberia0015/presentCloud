package fz.cs.daoyun.domain;

public class Param {
    private Integer id;

    private String keyEng;

    private String keyName;

    public String getKeyEng() {
        return keyEng;
    }

    public void setKeyEng(String keyEng) {
        this.keyEng = keyEng;
    }

    private Integer value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName == null ? null : keyName.trim();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
