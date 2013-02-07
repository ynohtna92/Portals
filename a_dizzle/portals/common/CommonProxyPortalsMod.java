package a_dizzle.portals.common;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxyPortalsMod {
	
	public void registerRenderThings()
	{
		//Register TileEntities
		GameRegistry.registerTileEntity(TileEntityPortalBase.class, "TileEntityPortalBase");
	}
	
	public void preInit()
	{
		
	}
}
