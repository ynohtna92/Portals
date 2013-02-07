package a_dizzle.portals.common;

import java.util.logging.Level;

//import a_dizzle.portals.client.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod( modid = "PortalsMod", name = "Portals Mod", version = "0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"portals"}, packetHandler = PacketHandler.class)
public class PortalsMod {
	
	public static Block portalBlock;
	
	public static int portalBlockID;
	
	public static int maxPortals;
	public static int maxPortalSize;
	
	public static boolean DEBUG = false;
	
	@Instance("PortalsMod")
	public static PortalsMod instance;
	
	@SidedProxy(clientSide = "a_dizzle.portals.client.ClientProxyPortalsMod", serverSide = "a_dizzle.portals.common.CommonProxyPortalsMod")
	public static CommonProxyPortalsMod proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit();
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			config.load(); //load configs from file
			DEBUG = (config.get(Configuration.CATEGORY_GENERAL, "DEBUG", false).getBoolean(false));
			maxPortals = (config.get(Configuration.CATEGORY_GENERAL, "MaxPortals", 20).getInt());
			maxPortalSize = (config.get(Configuration.CATEGORY_GENERAL, "MaxPortalSize", 10).getInt());
			portalBlockID = (config.get(Configuration.CATEGORY_BLOCK, "PortalBlockID", 3025).getInt());	
		}
		catch(Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Portals Mod failed to load configurations");
			FMLLog.severe(e.getMessage());
		}
		finally
		{
			config.save();//Configs saved to its file
		}
	}
	
	@Init
	public void load(FMLInitializationEvent event)
	{
		//Blocks
		portalBlock = (new BlockPortalBase(portalBlockID)).setBlockName("portalBase").setCreativeTab(CreativeTabs.tabMisc);
		
		//Items
		
		//Language Registry
		LanguageRegistry.addName(portalBlock, "Portal");
		
		//Game Registry
		GameRegistry.registerBlock(portalBlock,"Portal");
		
		//NetWork Registry
		//NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		
		proxy.registerRenderThings();
	}
	
	@PostInit
	public static void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
