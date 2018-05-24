package com.buuz135.industrial.api.conveyor;

import com.buuz135.industrial.proxy.block.Cuboid;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ConveyorUpgrade {
    private IConveyorContainer container;
    private ConveyorUpgradeFactory factory;
    private EnumFacing side;

    public static Cuboid EMPTY_BB = new Cuboid(0, 0, 0, 0, 0, 0);

    public ConveyorUpgrade(IConveyorContainer container, ConveyorUpgradeFactory factory, EnumFacing side) {
        this.container = container;
        this.factory = factory;
        this.side = side;
    }

    public IConveyorContainer getContainer() {
        return container;
    }

    public World getWorld() {
        return getContainer().getConveyorWorld();
    }

    public BlockPos getPos() {
        return getContainer().getConveyorPosition();
    }

    public ConveyorUpgradeFactory getFactory() {
        return factory;
    }

    public EnumFacing getSide() {
        return side;
    }

    public abstract void update();

    public Cuboid getBoundingBox() {
        return EMPTY_BB;
    }
}