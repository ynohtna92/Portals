package a_dizzle.portals.common;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPortalBase extends TileEntity
{
	public int portalFrequency = 1;
	public int portalSizeX = 1;
	public int portalSizeY = 1;
	
	public int orientation;	
	public boolean redstoneEnabled = false;
	public boolean state = false;	
	
	public TileEntityPortalBase pbPartner;
	public boolean isPaired = false;
	public int partnerWorld;
	public int partnerX;
	public int partnerY;
	public int partnerZ;
		
	public TileEntityPortalBase()
	{
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagc)
	{
		super.writeToNBT(nbttagc);
		nbttagc.setInteger("freq", portalFrequency);
		nbttagc.setInteger("sizeX", portalSizeX);
		nbttagc.setInteger("sizeY", portalSizeY);
		nbttagc.setBoolean("redstone", redstoneEnabled);
		nbttagc.setInteger("partnerX", partnerX);
		nbttagc.setInteger("partnerY", partnerY);
		nbttagc.setInteger("partnerZ", partnerZ);
		nbttagc.setBoolean("isPaired", isPaired);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagc)
	{
		super.readFromNBT(nbttagc);
		this.portalFrequency = nbttagc.getInteger("freq");
		this.portalSizeX = nbttagc.getInteger("sizeX");
		this.portalSizeY = nbttagc.getInteger("sizeY");
		this.redstoneEnabled = nbttagc.getBoolean("redstone");
		this.partnerX = nbttagc.getInteger("partnerX");
		this.partnerY = nbttagc.getInteger("partnerY");
		this.partnerZ = nbttagc.getInteger("partnerZ");
		this.isPaired = nbttagc.getBoolean("isPaired");
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		this.writeToNBT(var1);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 4, var1);
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		this.readFromNBT(pkt.customParam1);
	}
	
	/**
	 * Sets the frequency this portal will listen on for other partners
	 * 
	 * @param freq Frequency of portal
	 */
	public void setFreq(int freq)
	{
		this.portalFrequency = freq;
	}
	
	public void setSizeX(int x)
	{
		this.portalSizeX = x;
	}
	
	public void setSizeY(int y)
	{
		this.portalSizeY = y;
	}
	
	public void setRedStoneEnabled(boolean r)
	{
		this.redstoneEnabled = r;
	}
	
	public void setOpen()
	{
		this.state = true;
	}
	
	public void setClose()
	{
		this.state = false;		
	}
	
	public void setPartner(TileEntityPortalBase pb)
	{
		this.isPaired = true;
		this.partnerX = pb.xCoord;
		this.partnerY = pb.yCoord;
		this.partnerZ = pb.zCoord;
		this.pbPartner = pb;
		pb.setPartner(this);
	}
	
	public void setPartner(int x, int y, int z)
	{
		this.partnerX = x;
		this.partnerY = y;
		this.partnerZ = z;
		this.pbPartner = (TileEntityPortalBase)this.worldObj.getBlockTileEntity(x, y, z);
		this.isPaired = true;
	}
	
	public void delPartner()
	{
	}
	
	/**
	 * Called On onBlockAdded
	 */
	public void registerPortal()
	{
		PortalData pd = PortalData.getPortalList(worldObj);
		if(pd.registeredPortals.contains(this))
		{
			System.out.println("Portal TE already Registered.");
		}else
		{
			pd.registeredPortals.add(this);
			System.out.println("Portal TE Registered. There is " + pd.registeredPortals.size() + " registered portal(s).");
			pd.markDirty();
		}
	}
	
	/**
	 * Called On breakBlock
	 */
	public void deregisterPortal()
	{
		PortalData pd = PortalData.getPortalList(worldObj);
		if(pd.registeredPortals.contains(this))
		{
			int i = pd.registeredPortals.indexOf(this);
			pd.registeredPortals.remove(i);
			pd.markDirty();
			System.out.println("Portal TE Deregistered."); 
		}else
		{
			System.out.println("Portal TE does not exist.");
		}
	}
}