package bwp.event.impl;

import bwp.event.EventCancelable;

public class KeyPressEvent extends EventCancelable {

    private final int key;

    public KeyPressEvent(int key) {
        this.key = key;
    }
    public int getKey(){
        return key;
    }
}
