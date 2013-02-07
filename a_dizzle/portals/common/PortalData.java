package a_dizzle.portals.common;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class PortalData extends WorldSavedData{

	public ArrayList<TileEntityPortalBase> registeredPortals = new ArrayList<TileEntityPortalBase>();
	public static final String PORTAL_LIST_FILENAME = "PortalsMod-PortalsEntityData";
	public World world;
	public ArrayList<TileEntityCloneData> storedList = new ArrayList<TileEntityCloneData>();
	
	public PortalData(String par1Str) {
		super(par1Str);
	}
		
	public PortalData(String par1Str, World world) {
		super(par1Str);	
		this.world = world;
	}
	
	public static PortalData getPortalList(World world)
	{
        PortalData pl = (PortalData)world.mapStorage.loadData(PortalData.class, PORTAL_LIST_FILENAME);
        if(pl != null)
        {
        		pl.world = world;
        		loadPortalList(pl);
                return pl;
        }
 
        pl = new PortalData(PORTAL_LIST_FILENAME, world);
        world.mapStorage.setData(PORTAL_LIST_FILENAME, pl);
        return pl;
	}
	
	public static void loadPortalList(PortalData pl)
	{
		for(int i = 0; i < pl.storedList.size() ; i++)
		{
			TileEntity te = pl.world.getBlockTileEntity(pl.storedList.get(i).x, pl.storedList.get(i).y, pl.storedList.get(i).z);
			if(te instanceof TileEntityPortalBase)
			{
				if(!pl.registeredPortals.contains(te))
					pl.registeredPortals.add((TileEntityPortalBase) te);
			}
		}
	}
	
	public void storePortalCoords(int x, int y, int z)
	{
		System.out.println("x: "+ x + " y: "+ y +" z: " + z);
		storedList.add(new TileEntityCloneData(x,y,z));
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagc) {
		System.out.println("hi");
		NBTTagList var2 = nbttagc.getTagList("portals");
		for(int i = 0; i < var2.tagCount(); i++)
		{
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(i);
			storePortalCoords(var4.getInteger("x"),var4.getInteger("y"),var4.getInteger("z"));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagc) {
		NBTTagList var2 = new NBTTagList();
		for(int i = 0; i < registeredPortals.size(); i++)
		{
			NBTTagCompound p = new NBTTagCompound();
			p.setInteger("x",registeredPortals.get(i).xCoord);
			p.setInteger("y",registeredPortals.get(i).yCoord);
			p.setInteger("z",registeredPortals.get(i).zCoord);
			var2.appendTag(p);
		}
		nbttagc.setTag("portals", var2);
	}
}
