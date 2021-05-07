package fz.cs.daoyun.domain;

public class DIctWrapper {
    private Dict dict;
    private DictInfo info;

    @Override
    public String toString() {
        return "DIctWrapper{" +
                "dict=" + dict +
                ", info=" + info +
                '}';
    }

    public Dict getDict() {
        return dict;
    }

    public void setDict(Dict dict) {
        this.dict = dict;
    }

    public DictInfo getInfo() {
        return info;
    }

    public void setInfo(DictInfo info) {
        this.info = info;
    }
}
