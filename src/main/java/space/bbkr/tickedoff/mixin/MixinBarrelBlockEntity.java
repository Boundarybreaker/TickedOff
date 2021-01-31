package space.bbkr.tickedoff.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.bbkr.tickedoff.DummyEntity;
import space.bbkr.tickedoff.TickedOffTickable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BarrelBlockEntity.class)
public abstract class MixinBarrelBlockEntity extends LootableContainerBlockEntity implements TickedOffTickable {
	@Shadow public abstract int size();

	private DummyEntity dummy;

	protected MixinBarrelBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void cacheDumyEntity(BlockPos pos, BlockState state, CallbackInfo info) {
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
