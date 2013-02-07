package net.minecraft.client.gui;

import java.util.ArrayList;
import java.util.TreeMap;

import org.lwjgl.opengl.GL11;

import a_dizzle.portals.client.GuiPortalSettings;
import a_dizzle.portals.common.PortalData;
import a_dizzle.portals.common.TileEntityPortalBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;

/**
 * This class is located in net.minecraft.client.gui because variables in GuiScreen are protected.
 * @author Anthony
 *
 */
public class GuiSlotPortals extends GuiSlot{
	
	
    public ArrayList<TileEntityPortalBase> list;

    final GuiPortalSettings portalSettings;
	
	public GuiSlotPortals(GuiPortalSettings portalSettings, World world)
	{
		 super(portalSettings.mc, portalSettings.width, portalSettings.height, 36, portalSettings.height/2, 42);
		 this.portalSettings = portalSettings;
		 this.list = PortalData.getPortalList(world).registeredPortals;
		 this.list.add(portalSettings.pb);
	}

	@Override
	protected int getSize() {
		return list.size();
	}
	
	protected int getContentHeight()
    {
        return this.getSize() * 42;
    }

	@Override
	protected void elementClicked(int var1, boolean var2) {
		GuiPortalSettings.selectElement(this.portalSettings, var1);
	}

	@Override
	protected boolean isSelected(int var1) {
		return var1 == GuiPortalSettings.getSelectedServer(this.portalSettings);
	}

	@Override
	protected void drawBackground() 
	{
		this.portalSettings.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int var1, int var2, int var3, int var4,
			Tessellator var5) {
		 this.portalSettings.drawString(this.portalSettings.fontRenderer, "X: " + list.get(var1).xCoord + " Y: " + list.get(var1).yCoord + " Z: " + list.get(var1).zCoord + " Dimension: " + list.get(var1).worldObj.getWorldInfo().getDimension(), var2 + 2, var3 + 1, 16777215);
		 this.portalSettings.drawString(this.portalSettings.fontRenderer, "Size X: " + list.get(var1).portalSizeY+ " Size Y: " + list.get(var1).portalSizeY, var2 + 2, var3 + 14, 8421504);
		 this.portalSettings.drawString(this.portalSettings.fontRenderer, "Paired: " + list.get(var1).isPaired, var2 + 2, var3 + 27, (list.get(var1).isPaired ?  0xFF3300 : 0x009933));
	}

}
