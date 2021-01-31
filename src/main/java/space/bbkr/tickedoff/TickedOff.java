package space.bbkr.tickedoff;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class TickedOff implements ModInitializer {
	public static final String MODID = "tickedoff";
	public static final Logger logger = LogManager.getLogger();

	@Override
	public void onInitialize() { }

	public static void handleTick(World world, BlockPos pos, BlockState state, BlockEntity be) {
		if (be instanceof TickedOffTickable) {
			((TickedOffTickable) be).tickOff(world, pos, state, be);
		}
	}
}
