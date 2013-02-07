package a_dizzle.portals.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class PacketHandler implements IPacketHandler{

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		
		if(packet.channel.equals("portals"))
		{
			if(side == Side.SERVER)
				handlerServerPacket(packet);
		}
		
	}
	
	void handlerServerPacket(Packet250CustomPayload packet)
	{
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		int dimension;
		int x;
		int y;
		int z;
		int freq;
		int sizeX;
		int sizeY;
		boolean powered;
		//Partners
		int pX;
		int pY;
		int pZ;
		boolean isPaired;
		
		try
		{
			dimension = inputStream.readInt();
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();
			freq = inputStream.readInt();
			sizeX = inputStream.readInt();
			sizeY = inputStream.readInt();
			powered = inputStream.readBoolean();
			pX = inputStream.readInt();
			pY = inputStream.readInt();
			pZ = inputStream.readInt();
			isPaired = inputStream.readBoolean();
		}
		catch(IOException e)
		{
			System.out.println("Failed Reading Custom Payload on channel 'portals'");
			e.printStackTrace();
			return;
		}
		WorldServer world = DimensionManager.getWorld(dimension);
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityPortalBase)
		{
			TileEntityPortalBase tepb = ((TileEntityPortalBase) te);
			tepb.setFreq(freq);
			tepb.setSizeX(sizeX);
			tepb.setSizeY(sizeY);
			tepb.setRedStoneEnabled(powered);
			if(isPaired)
				tepb.setPartner(pX, pY, pZ);
			tepb.worldObj.markBlockForUpdate(x, y, z);
			System.out.println("Processed TileEntityPortalBase Packet. freq: " + freq + " x: "+ sizeX + " y: "+ sizeY + " powered: " + powered);
		}
	}
}
