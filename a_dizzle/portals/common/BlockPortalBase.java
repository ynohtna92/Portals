package a_dizzle.portals.common;

import a_dizzle.portals.client.GuiPortalSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPortalBase extends BlockContainer{

    protected BlockPortalBase(int par1)
    {
        super(par1, Material.portal);
    }
    
    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        if(par1World.getBlockTileEntity(par2, par3, par4) instanceof TileEntityPortalBase)
        {
        	TileEntityPortalBase pb = (TileEntityPortalBase) par1World.getBlockTileEntity(par2, par3, par4);
        	pb.registerPortal();
        }
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if(par1World.getBlockTileEntity(par2, par3, par4) instanceof TileEntityPortalBase)
        {
        	TileEntityPortalBase pb = (TileEntityPortalBase) par1World.getBlockTileEntity(par2, par3, par4);
        	pb.deregisterPortal();
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public TileEntity createNewTileEntity(World war1)
    {
    	try
    	{
    		return new TileEntityPortalBase();
    	}
    	catch(Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par1, float par2, float par3, float par4){
    	TileEntity tileEntity = world.getBlockTileEntity(x,y,z);
    	if(tileEntity == null || player.isSneaking()){
    			return false;
    	}
    	ModLoader.openGUI(player, new GuiPortalSettings((TileEntityPortalBase)tileEntity));
    	expandPortal(world, x,y,z);
    	return true;
    }
    
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var6 == 0)
        {
        	//South
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
        }

        if (var6 == 1)
        {
        	//West
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5);
        }

        if (var6 == 2)
        {
        	//North
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
        }

        if (var6 == 3)
        {
        	//East
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4);
        }
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public int getRenderType()
    {
        return -1;
    }
    
    public void expandPortal(World world, int x, int y, int z)
    {
    	TileEntity te = world.getBlockTileEntity(x, y, z);
    	int meta = world.getBlockMetadata(x, y,z);
    	if(te instanceof TileEntityPortalBase)
    	{
    		TileEntityPortalBase pb = (TileEntityPortalBase)te;
    		int orientation = pb.orientation;
    		int sizeX = pb.portalSizeX;
    		int sizeY = pb.portalSizeY;
    		System.out.println("sizeX: "+ sizeX + " sizeY: "+ sizeY);
    		switch(meta)
    		{
    			case 2:
    	    		for(int i = 0; i < sizeX; i++)
    	    		{
    	    			for(int b = 0; b < sizeY; b++)
    	    			{
    	    				if((x+i != x) || (y+b != y))
    	    				{
    	    	 				world.setBlockAndMetadataWithUpdate(x-i, y+b, z, 1, meta,true);
    	    				}
    	    			}
    	    		}
    	    		break;
    			case 3:
    	    		for(int i = 0; i < sizeX; i++)
    	    		{
    	    			for(int b = 0; b < sizeY; b++)
    	    			{
    	    				if((x+i != x) || (y+b != y))
    	    				{
    	    	 				world.setBlockAndMetadataWithUpdate(x+i, y+b, z, 1, meta,true);
    	    				}
    	    			}
    	    		}
    	    		break;
    			case 4:
    	    		for(int i = 0; i < sizeX; i++)
    	    		{
    	    			for(int b = 0; b < sizeY; b++)
    	    			{
    	    				if((x+i != x) || (y+b != y))
    	    				{
    	    	 				world.setBlockAndMetadataWithUpdate(x, y+b, z+i, 1, meta,true);
    	    				}
    	    			}
    	    		}
    	    		break;
    			case 5:
    	    		for(int i = 0; i < sizeX; i++)
    	    		{
    	    			for(int b = 0; b < sizeY; b++)
    	    			{
    	    				if((x+i != x) || (y+b != y))
    	    				{
    	    	 				world.setBlockAndMetadataWithUpdate(x, y+b, z-i, 1, meta,true);
    	    				}
    	    			}
    	    		}
    	    		break;
    		}
    	}
    }

}
