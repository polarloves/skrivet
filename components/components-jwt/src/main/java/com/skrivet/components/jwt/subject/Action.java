package com.skrivet.components.jwt.subject;

public enum  Action {
    CREATE("create"),DELETE("delete"),UPDATE("update");
    String name;
    Action(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
