package vazkii.botania.client.integration.jei.elventrade;

import com.google.common.collect.ImmutableList;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.recipe.RecipeElvenTrade;

import javax.annotation.Nonnull;
import java.util.List;

public class ElvenTradeRecipeWrapper implements IRecipeWrapper {

    private final List input;
    private final ItemStack output;

    public ElvenTradeRecipeWrapper(RecipeElvenTrade recipe) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Object o : recipe.getInputs()) {
            if (o instanceof ItemStack) {
                builder.add(o);
            }
            if (o instanceof String) {
                builder.add(OreDictionary.getOres(((String) o)));
            }
        }
        input = builder.build();
        output = recipe.getOutput();
    }

    @Override
    public List getInputs() {
        return input;
    }

    @Override
    public List<ItemStack> getOutputs() {
        return ImmutableList.of(output);
    }

    @Override
    public List<FluidStack> getFluidInputs() {
        return ImmutableList.of();
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return ImmutableList.of();
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight) {}

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight) {}

}