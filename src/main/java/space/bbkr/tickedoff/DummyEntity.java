package space.bbkr.tickedoff;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DummyEntity extends Entity {

	public DummyEntity(World world) {
		super(EntityType.ITEM, world);
	}

	@Override
	public boolean isFireImmune() {
		return super.isFireImmune();
	}

	@Override
	public boolean canUsePortals() {
		return false;
	}

	@Override
	public boolean hasVehicle() {
		return false;
	}

	@Override
	protected boolean canStartRiding(Entity entity) {
		return false;
	}

	@Override
	protected boolean canAddPassenger(Entity passenger) {
		return false;
	}

	@Override
	public boolean isTouchingWater() {
		return world.getFluidState(getBlockPos()).isIn(FluidTags.WATER);
	}

	@Override
	public boolean isInvulnerable() {
		return true;
	}

	@Override
	public boolean isSpectator() {
		return true;
	}

	@Override
	public void move(MovementType type, Vec3d movement) { }

	@Override
	public boolean isSubmergedInWater() {
		return super.isSubmergedInWater();
	}

	@Override
	public boolean isImmuneToExplosion() {
		return true;
	}

	@Override
	public boolean canBeSpectated(ServerPlayerEntity spectator) {
		return false;
	}

	@Override
	protected void initDataTracker() { }

	@Override
	protected void readCustomDataFromTag(CompoundTag tag) { }

	@Override
	protected void writeCustomDataToTag(CompoundTag tag) { }

	@Override
	public Packet<?> createSpawnPacket() {
		return null;
	}
}
