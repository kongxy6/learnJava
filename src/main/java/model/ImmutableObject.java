package model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImmutableObject {

    private final int id;
    public StringBuilder builder;
    public StringBuffer buffer;
    String desc;

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
