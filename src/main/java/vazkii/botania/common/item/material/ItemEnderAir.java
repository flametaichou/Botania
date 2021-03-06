/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.item.material;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.dimension.EndDimension;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

import vazkii.botania.common.entity.EntityEnderAirBottle;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.LibMisc;

import javax.annotation.Nonnull;

import java.util.List;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class ItemEnderAir extends Item {
	public ItemEnderAir(Properties props) {
		super(props);
	}

	@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent.RightClickItem event) {
		ItemStack stack = event.getItemStack();
		World world = event.getWorld();

		if (!stack.isEmpty() && stack.getItem() == Items.GLASS_BOTTLE && world.getDimension() instanceof EndDimension) {
			List<AreaEffectCloudEntity> list = world.getEntitiesWithinAABB(AreaEffectCloudEntity.class,
					event.getPlayer().getBoundingBox().grow(3.5D),
					entity -> entity != null && entity.isAlive()
							&& entity.getParticleData().getType() == ParticleTypes.DRAGON_BREATH);
			if (!list.isEmpty()) {
				return;
			}

			if (!world.isRemote) {
				ItemStack enderAir = new ItemStack(ModItems.enderAirBottle);
				ItemHandlerHelper.giveItemToPlayer(event.getPlayer(), enderAir);
				stack.shrink(1);
				world.playSound(null, event.getPos(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.NEUTRAL, 0.5F, 1F);
			}

			event.setCanceled(true);
			event.setCancellationResult(ActionResultType.SUCCESS);
		}
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!player.abilities.isCreativeMode) {
			stack.shrink(1);
		}

		world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote) {
			EntityEnderAirBottle b = new EntityEnderAirBottle(player, world);
			b.shoot(player, player.rotationPitch, player.rotationYaw, 0F, 1.5F, 1F);
			world.addEntity(b);
		} else {
			player.swingArm(hand);
		}
		return ActionResult.resultSuccess(stack);
	}
}
