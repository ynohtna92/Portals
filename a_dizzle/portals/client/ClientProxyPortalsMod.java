package a_dizzle.portals.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.src.ModLoader;
import a_dizzle.portals.common.CommonProxyPortalsMod;
import a_dizzle.portals.common.TileEntityPortalBase;

public class ClientProxyPortalsMod extends CommonProxyPortalsMod {

	@Override
	public void registerRenderThings(){
		//Renders
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPortalBase.class, new RenderPortalBase());
		
		//TileEntity
		ModLoader.registerTileEntity(TileEntityPortalBase.class, "TileEntityPortalBase", new RenderPortalBase());
	}
	
	public void preInit()
	{
		//TODO Sounds And textures register, if any.
	}
}
