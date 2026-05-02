package com.waymee.overdrive.backend;

import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ModShapes {

    public static VoxelShape cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
        return net.minecraft.world.level.block.Block.box(x1, y1, z1, x2, y2, z2);
    }

    public static Builder shape(VoxelShape shape) {
        return new Builder(shape);
    }

    public static Builder shape(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new Builder(cuboid(x1, y1, z1, x2, y2, z2));
    }

    // Cockpit shape with base direction NORTH
    public static final VoxelShaper


            COCKPIT =
            shape(1, 0, 5, 15, 5, 16)
            .add(2, 5, 5, 14, 12, 14)
            .forHorizontal(Direction.NORTH);





    public static class Builder {
        private VoxelShape shape;

        public Builder(VoxelShape shape) {
            this.shape = shape;
        }

        public Builder add(VoxelShape shape) {
            this.shape = net.minecraft.world.phys.shapes.Shapes.or(this.shape, shape);
            return this;
        }

        public Builder add(double x1, double y1, double z1, double x2, double y2, double z2) {
            return add(cuboid(x1, y1, z1, x2, y2, z2));
        }

        public VoxelShaper forHorizontal(Direction direction) {
            return VoxelShaper.forHorizontal(shape, direction);
        }

        public VoxelShaper forDirectional(Direction direction) {
            return VoxelShaper.forDirectional(shape, direction);
        }

        public VoxelShape build() {
            return shape;
        }
    }
}
