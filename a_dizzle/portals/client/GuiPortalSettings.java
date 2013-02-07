package a_dizzle.portals.client;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.network.PacketDispatcher;
import a_dizzle.portals.common.PortalsMod;
import a_dizzle.portals.common.TileEntityPortalBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlotPortals;
import net.minecraft.client.settings.EnumOptions;
import net.minecraft.network.packet.Packet250CustomPayload;

public class GuiPortalSettings extends GuiScreen{
	
	//Portal Settings
	public TileEntityPortalBase pb;
	private float portalFrequency;
	private float portalSizeX;
	private float portalSizeY;
	private boolean redstoneEnabled;
	private boolean activated;
	private boolean isPaired;
	private TileEntityPortalBase partner;
	
	//Portal Menu Buttons
	private GuiButton buttonPair;
	private GuiButton buttonCancel;
	private GuiButton buttonDone;
	private GuiButton buttonRedStone;
	private GuiButton buttonActivate;
	private GuiSlider sliderSizeX;
	private GuiSlider sliderSizeY;
	private GuiSlider sliderFreq;
	private GuiSlotPortals portalsList;
	
	public int selectedElement = -1;
	
	public GuiPortalSettings(TileEntityPortalBase PB)
	{
		this.pb = PB;
		this.portalFrequency = this.pb.portalFrequency;
		this.portalSizeX = this.pb.portalSizeX;
		this.portalSizeY = this.pb.portalSizeY;
		this.redstoneEnabled = this.pb.redstoneEnabled;
		this.activated = this.pb.state;
		this.partner = this.pb.pbPartner;
		this.isPaired = this.pb.isPaired;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		//buttons	
		this.controlList.add(this.buttonPair = new GuiButton(0, this.width / 2 - 100, this.height/2 + 60, 98, 20, "Pair Portal"));
		this.controlList.add(this.buttonDone = new GuiButton(1, this.width / 2 + 2, this.height/2 + 90, 98, 20, "Done"));
		this.controlList.add(this.buttonCancel = new GuiButton(2, this.width / 2 - 100, this.height/2 + 90, 98, 20, "Cancel"));
		this.controlList.add(this.sliderFreq = new GuiSlider(4, this.width / 2 - 75, this.height, "Frequency", portalFrequency/PortalsMod.maxPortals, PortalsMod.maxPortals));
		this.controlList.add(this.sliderSizeX = new GuiSlider(5, this.width / 2 - 155, this.height/2 + 5, "Size X", portalSizeX/PortalsMod.maxPortalSize, PortalsMod.maxPortalSize));
		this.controlList.add(this.sliderSizeY = new GuiSlider(6, this.width / 2 + 5, this.height/2 + 5, "Size Y", portalSizeY/PortalsMod.maxPortalSize, PortalsMod.maxPortalSize));
		this.controlList.add(this.buttonRedStone = new GuiButton(7, this.width / 2 - 65, this.height/2 + 35, 130, 20, "Redstone Enabled: " + (this.redstoneEnabled ? "Yes" : "No")));
		this.controlList.add(this.buttonActivate = new GuiButton(8, this.width /2 + 2, this.height/2 + 60, 98, 20, "Activated: " + (this.activated ? "Yes" : "No")));
		this.buttonActivate.enabled = false;
		if(this.isPaired)
			this.buttonPair.displayString = "Unpair Portal";
		else
			this.buttonPair.enabled = false;
		this.portalsList = new GuiSlotPortals(this, this.pb.worldObj);
		this.portalsList.registerScrollButtons(this.controlList, 9, 10);
		System.out.println(this.pb.worldObj);
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 0)
			{
				if(this.isPaired == false)
				{
					this.isPaired = true;
					par1GuiButton.displayString = "Unpair Portal";
					if(!this.redstoneEnabled)
						this.buttonActivate.enabled = true;
					this.partner = this.portalsList.list.get(selectedElement);
				}else
				{
					this.isPaired = false;
					par1GuiButton.displayString = "Pair Portal";
					this.buttonActivate.enabled = false;
				}
			}
			if (par1GuiButton.id == 1)
			{
				sendToServer();
				this.mc.displayGuiScreen((GuiScreen)null);	
			}
			if (par1GuiButton.id == 2)
			{
				this.mc.displayGuiScreen((GuiScreen)null);		
			}
			if(par1GuiButton.id == 7)
			{
				this.redstoneEnabled = !this.redstoneEnabled;
				par1GuiButton.displayString = "Redstone Enabled: " + (this.redstoneEnabled ? "Yes" : "No");
				if(this.redstoneEnabled)
				{
					this.buttonActivate.enabled = false;
				}else
				{
					this.buttonActivate.enabled = true;
				}
			}
			if(par1GuiButton.id == 8)
			{
				this.activated = !this.activated;
				par1GuiButton.displayString = "Activated: " + (this.activated ? "Yes" : "No");
			}
		}
	}

	@Override
    public void drawScreen(int par1, int par2, float par3)
    {
		//"Paired With X: " + this.partner.xCoord + " Y: " + this.partner.yCoord +" Z: " + this.partner.zCoord
		this.portalsList.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, "Portal Settings", this.width / 2, 10, 16777215);
        String s =  "(X: " + this.pb.xCoord + " Y: "+ this.pb.yCoord +" Z: " + this.pb.zCoord + ") " + (this.isPaired? ("yes") : "");
        this.drawCenteredString(this.fontRenderer, s, this.width / 2, 25, 8421504);
        super.drawScreen(par1, par2, par3);
    }
	
	@Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
	
    public static int getSelectedServer(GuiPortalSettings guips)
    {
        return guips.selectedElement;
    }
	
	public static void selectElement(GuiPortalSettings portalSettings, int var1) {
		portalSettings.selectedElement = var1;
		if(var1 != -1 && !portalSettings.pb.isPaired)
		{
			portalSettings.buttonPair.enabled = true;
		}else
		{
			portalSettings.buttonPair.enabled = false;
		}
	}
	
	public void sendToServer()
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(pb.worldObj.getWorldInfo().getDimension());
			outputStream.writeInt(pb.xCoord);
			outputStream.writeInt(pb.yCoord);
			outputStream.writeInt(pb.zCoord);
			outputStream.writeInt((int)(sliderFreq.sliderValue * sliderFreq.sliderMaxValue));
			outputStream.writeInt((int)(sliderSizeX.sliderValue * sliderSizeX.sliderMaxValue));
			outputStream.writeInt((int)(sliderSizeY.sliderValue * sliderSizeY.sliderMaxValue));
			outputStream.writeBoolean(this.redstoneEnabled);
			if(this.isPaired){
				outputStream.writeInt(partner.xCoord);
				outputStream.writeInt(partner.yCoord);
				outputStream.writeInt(partner.zCoord);
			}else
			{
				outputStream.writeInt(0);
				outputStream.writeInt(0);
				outputStream.writeInt(0);
			}
			outputStream.writeBoolean(this.isPaired);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "portals";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		PacketDispatcher.sendPacketToServer(packet);
		
		System.out.println("PacketSent");
	}
}
