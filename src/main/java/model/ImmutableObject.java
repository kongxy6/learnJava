package model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImmutableObject {

    public StringBuilder builder;
    public StringBuffer buffer;
    String desc;
    private int id;

    public ImmutableObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ImmutableObject setId(int i) {
        return new ImmutableObject(i);
    }
}
