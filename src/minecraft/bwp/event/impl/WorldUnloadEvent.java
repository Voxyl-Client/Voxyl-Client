package bwp.event.impl;

import bwp.event.Event;
import net.minecraft.world.World;

public class WorldUnloadEvent extends Event {

    private final World world;

    public WorldUnloadEvent(World world){
        this.world = world;
    }
    public World getWorld(){
        return world;
    }

}
