package space.bbkr.tickedoff.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.bbkr.tickedoff.TickedOff;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.world.World;

@Mixin(ChestBlock.class)
public abstract class MixinChestBlock extends BlockWithEntity {
	protected MixinChestBlock(Settings settings) {
		super(settings);
	}

	@Inject(method = "getTicker", at = @At("HEAD"), cancellable = true)
	private <T extends BlockEntity> void injectItemTicker(World world, BlockState state, BlockEntityType<T> type, CallbackInfoReturnable<BlockEntityTicker<T>> info) {
		if (!world.isClient) info.setReturnValue(checkType(type, BlockEntityType.CHEST, TickedOff::handleTick));
	}
}
