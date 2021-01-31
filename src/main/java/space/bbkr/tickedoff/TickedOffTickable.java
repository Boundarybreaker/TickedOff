package space.bbkr.tickedoff;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface TickedOffTickable {
	void tickOff(World world, BlockPos pos, BlockState state, BlockEntity be);
}
