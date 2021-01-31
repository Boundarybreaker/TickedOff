package space.bbkr.tickedoff.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.bbkr.tickedoff.DummyEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class MixinShulkerBoxBlockEntity extends BlockEntity {

	private DummyEntity dummy;

	public MixinShulkerBoxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Inject(method = "<init>(Lnet/minecraft/util/DyeColor;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At("RETURN"))
	private void cacheDumyEntity(DyeColor color, BlockPos pos, BlockState state, CallbackInfo info) {
		this.dummy = new DummyEntity(world);
		dummy.setPos(pos.getX(), pos.getY(), pos.getZ());
	}

	@Inject(method = "<init>(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At("RETURN"))
	private void cacheDumyEntity(BlockPos pos, BlockState state, CallbackInfo info) {
		this.dummy = new DummyEntity(world);
		dummy.setPos(pos.getX(), pos.getY(), pos.getZ());
	}

	@Inject(method = "tick", at = @At("RETURN"))
	private static void tickOff(World world, BlockPos pos, BlockState state, ShulkerBoxBlockEntity be, CallbackInfo info) {
		for (int i = 0; i < be.size(); i++) {
			ItemStack stack = be.getStack(i);
			if (!stack.isEmpty()) {
				stack.inventoryTick(be.getWorld(), ((MixinShulkerBoxBlockEntity) (Object) be).dummy, i, false);
			}
		}
		if (!((MixinShulkerBoxBlockEntity) (Object) be).dummy.getBlockPos().equals(pos)) {
			((MixinShulkerBoxBlockEntity) (Object) be).dummy.setPos(pos.getX(), pos.getY(), pos.getZ());
		}
	}
}
