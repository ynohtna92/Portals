
/*
package a_dizzle.portals.client;

import a_dizzle.portals.common.TileEntityPortalBase;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler{
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityPortalBase){
			return new TileEntityPortalBase();
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x,y,z);
		if(tileEntity instanceof TileEntityPortalBase)
		{
			return new GuiPortalSettings();
		}
		return null;
	}
}

*/