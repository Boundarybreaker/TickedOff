package space.bbkr.tickedoff.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.bbkr.tickedoff.DummyEntity;
import space.bbkr.tickedoff.TickedOffTickable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ChestBlockEntity.class)
public abstract class MixinChestBlockEntity extends LootableContainerBlockEntity implements TickedOffTickable {
	private DummyEntity dummy;

	protected MixinChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	@Inject(method = "<init>(Lnet/minecraft/block/entity/BlockEntityType;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At("RETURN"))
	private void cacheDumyEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state, CallbackInfo info) {
		this.dummy = new DummyEntity(world);
		dummy.setPos(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public void tickOff(World world, BlockPos pos, BlockState state, BlockEntity be) {
		for (int i = 0; i < size(); i++) {
			ItemStack stack = getStack(i);
			if (!stack.isEmpty()) {
				stack.inventoryTick(this.world, dummy, i, false);
			}
		}
		if (!dummy.getBlockPos().equals(pos)) {
			dummy.setPos(pos.getX(), pos.getY(), pos.getZ());
		}
	}
}
