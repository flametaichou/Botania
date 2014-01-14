/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Jan 14, 2014, 6:48:05 PM (GMT)]
 */
package vazkii.botania.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.LexiconCategory;
import vazkii.botania.client.gui.button.GuiButtonInvisible;
import vazkii.botania.client.lib.LibResources;
import vazkii.botania.common.item.ModItems;

public class GuiLexicon extends GuiScreen {

	public static final ResourceLocation texture = new ResourceLocation(LibResources.GUI_LEXICON);
	
	int guiWidth = 146;
	int guiHeight = 180;
	int left, top;
	
	@Override
	public void initGui() {
		super.initGui();
		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;
		
		if(isIndex()) {
			buttonList.clear();
			int x = 18;
			for(int i = 0; i < 12; i++) {
				int y = 16 + i * 12;
				buttonList.add(new GuiButtonInvisible(i, left + x, top + y, 110, 10, "lorem ipsum"));
			}
			populateIndex();
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
		drawCenteredString(fontRenderer, getTitle(), left + guiWidth / 2, top - 12, 0x00FF00);
		
		super.drawScreen(par1, par2, par3);
	}
	
	String getTitle() {
		return ModItems.lexicon.getItemDisplayName(null);
	}
	
	boolean isIndex() {
		return true;
	}
	
	void populateIndex() {
		List<LexiconCategory> categoryList = BotaniaAPI.getAllCategories();
		for(int i = 0; i < 12; i++) {
			GuiButtonInvisible button = (GuiButtonInvisible) buttonList.get(i);
			LexiconCategory category = i >= categoryList.size() ? null : categoryList.get(i);
			if(category != null)
				button.displayString = StatCollector.translateToLocal(category.getUnlocalizedName());
			else button.displayString = "";
		}
	}
	
}
