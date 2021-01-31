package space.bbkr.tickedoff.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import space.bbkr.tickedoff.TickedOff;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.world.World;

@Mixin(BarrelBlock.class)
public abstract class MixinBarrelBlock extends BlockWithEntity {

	public MixinBarrelBlock(Settings settings) {
		super(settings);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return world.isClient? null : checkType(type, BlockEntityType.BARREL, TickedOff::handleTick);
	}
}
