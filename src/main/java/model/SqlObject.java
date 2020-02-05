package model;

public class SqlObject {

    public final String desc;
    private int id;

    private String field;

    {
        desc = "NM$L";
        // 只要不向前引用，就可以初始化
    }

    public SqlObject() {

    }

    public SqlObject(int id, String field) {
        this.id = id;
        this.field = field;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
