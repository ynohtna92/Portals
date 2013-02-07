package a_dizzle.portals.client;

import java.nio.FloatBuffer;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;

import org.lwjgl.opengl.GL11;

import a_dizzle.portals.common.TileEntityPortalBase;

import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class RenderPortalBase extends TileEntitySpecialRenderer{

    FloatBuffer field_76908_a = GLAllocation.createDirectFloatBuffer(16);

    /**
     * Renders the End Portal.
     */
    public void renderEndPortalTileEntity(TileEntityPortalBase par1TileEntityEndPortal, double par2, double par4, double par6, float par8)
    {
        float var9 = (float)this.tileEntityRenderer.playerX;
        float var10 = (float)this.tileEntityRenderer.playerY;
        float var11 = (float)this.tileEntityRenderer.playerZ;
        GL11.glDisable(GL11.GL_LIGHTING);
        Random var12 = new Random(31100L);
        float var13 = 1.0F;

        for (int var14 = 0; var14 < 16; ++var14)
        {
            GL11.glPushMatrix();
            float var15 = (float)(16 - var14);
            float var16 = 0.0625F;
            float var17 = 1.0F / (var15 + 1.0F);

            if (var14 == 0)
            {
                this.bindTextureByName("/misc/tunnel.png");
                var17 = 0.1F;
                var15 = 65.0F;
                var16 = 0.125F;
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            }

            if (var14 == 1)
            {
                this.bindTextureByName("/misc/particlefield.png");
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                var16 = 0.5F;
            }

            float var18 = (float)(-(par4 + (double)var13));
            float var19 = var18 + ActiveRenderInfo.objectY;
            float var20 = var18 + var15 + ActiveRenderInfo.objectY;
            float var21 = var19 / var20;
            var21 += (float)(par4 + (double)var13);
            GL11.glTranslatef(var9, var21, var11);
            GL11.glTexGeni(GL11.GL_S, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR);
            GL11.glTexGeni(GL11.GL_T, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR);
            GL11.glTexGeni(GL11.GL_R, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR);
            GL11.glTexGeni(GL11.GL_Q, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR);
            GL11.glTexGen(GL11.GL_S, GL11.GL_OBJECT_PLANE, this.func_76907_a(1.0F, 0.0F, 0.0F, 0.0F));
            GL11.glTexGen(GL11.GL_T, GL11.GL_OBJECT_PLANE, this.func_76907_a(0.0F, 0.0F, 1.0F, 0.0F));
            GL11.glTexGen(GL11.GL_R, GL11.GL_OBJECT_PLANE, this.func_76907_a(0.0F, 0.0F, 0.0F, 1.0F));
            GL11.glTexGen(GL11.GL_Q, GL11.GL_EYE_PLANE, this.func_76907_a(0.0F, 1.0F, 0.0F, 0.0F));
            GL11.glEnable(GL11.GL_TEXTURE_GEN_S);
            GL11.glEnable(GL11.GL_TEXTURE_GEN_T);
            GL11.glEnable(GL11.GL_TEXTURE_GEN_R);
            GL11.glEnable(GL11.GL_TEXTURE_GEN_Q);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0F, (float)(Minecraft.getSystemTime() % 700000L) / 700000.0F, 0.0F);
            GL11.glScalef(var16, var16, var16);
            GL11.glTranslatef(0.5F, 0.5F, 0.0F);
            GL11.glRotatef((float)(var14 * var14 * 4321 + var14 * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-0.5F, -0.5F, 0.0F);
            GL11.glTranslatef(-var9, -var11, -var10);
            var19 = var18 + ActiveRenderInfo.objectY;
            GL11.glTranslatef(ActiveRenderInfo.objectX * var15 / var19, ActiveRenderInfo.objectZ * var15 / var19, -var10);
            
            //Top
            Tessellator var24 = Tessellator.instance;
            var24.startDrawingQuads();
            var21 = var12.nextFloat() * 0.5F + 0.1F;
            float var22 = var12.nextFloat() * 0.5F + 0.4F;
            float var23 = var12.nextFloat() * 0.5F + 0.5F;

            if (var14 == 0)
            {
                var23 = 1.0F;
                var22 = 1.0F;
                var21 = 1.0F;
            }

            var24.setColorRGBA_F(var21 * var17, var22 * var17, var23 * var17, 1.0F);
            var24.addVertex(par2, par4 + (double)var13, par6);
            var24.addVertex(par2, par4 + (double)var13, par6 + 1.0D);
            var24.addVertex(par2 + 1.0D, par4 + (double)var13, par6 + 1.0D);
            var24.addVertex(par2 + 1.0D, par4 + (double)var13, par6);
            var24.draw();
            
            //Bottom
            Tessellator bottom = Tessellator.instance;
            bottom.startDrawingQuads();
            float bvar21 = var12.nextFloat() * 0.5F + 0.1F;
            float bvar22 = var12.nextFloat() * 0.5F + 0.4F;
            float bvar23 = var12.nextFloat() * 0.5F + 0.5F;

            if (var14 == 0)
            {
                bvar23 = 1.0F;
                bvar22 = 1.0F;
                bvar21 = 1.0F;
            }

            bottom.setColorRGBA_F(var21 * var17, bvar22 * var17, bvar23 * var17, 1.0F);
            bottom.addVertex(par2, par4 + (double)var13 - 1.0D, par6 + 1.0D);
            bottom.addVertex(par2, par4 + (double)var13 - 1.0D, par6);
            bottom.addVertex(par2 + 1.0D, par4 + (double)var13 - 1.0D, par6);
            bottom.addVertex(par2 + 1.0D, par4 + (double)var13 - 1.0D, par6 + 1.0D);
            bottom.draw();             
            
            //North
            Tessellator north = Tessellator.instance;
            north.startDrawingQuads();
            float nvar21 = var12.nextFloat() * 0.5F + 0.1F;
            float nvar22 = var12.nextFloat() * 0.5F + 0.4F;
            float nvar23 = var12.nextFloat() * 0.5F + 0.5F;

            if (var14 == 0)
            {
                nvar23 = 1.0F;
                nvar22 = 1.0F;
                nvar21 = 1.0F;
            }

            north.setColorRGBA_F(var21 * var17, nvar22 * var17, nvar23 * var17, 1.0F);
            north.addVertex(par2, par4 + (double)var13, par6 + 1.0D);
            north.addVertex(par2, par4 + (double)var13 - 1.0D, par6+ 1.0D);
            north.addVertex(par2 + 1.0D, par4 + (double)var13 - 1.0D, par6 + 1.0D);
            north.addVertex(par2 + 1.0D, par4 + (double)var13, par6 + 1.0D);
            north.draw();
            
            //east
            Tessellator east = Tessellator.instance;
            north.startDrawingQuads();
            float evar21 = var12.nextFloat() * 0.5F + 0.1F;
            float evar22 = var12.nextFloat() * 0.5F + 0.4F;
            float evar23 = var12.nextFloat() * 0.5F + 0.5F;

            if (var14 == 0)
            {
                evar23 = 1.0F;
                evar22 = 1.0F;
                evar21 = 1.0F;
            }

            east.setColorRGBA_F(var21 * var17, evar22 * var17, evar23 * var17, 1.0F);
            east.addVertex(par2, par4 + (double)var13 - 1.0D, par6);
            east.addVertex(par2, par4 + (double)var13 - 1.0D, par6 + 1.0D);
            east.addVertex(par2, par4 + (double)var13, par6 + 1.0D);
            east.addVertex(par2, par4 + (double)var13, par6);
            east.draw();
        
            //west
            Tessellator west = Tessellator.instance;
            north.startDrawingQuads();
            float wvar21 = var12.nextFloat() * 0.5F + 0.1F;
            float wvar22 = var12.nextFloat() * 0.5F + 0.4F;
            float wvar23 = var12.nextFloat() * 0.5F + 0.5F;

            if (var14 == 0)
            {
                wvar23 = 1.0F;
                wvar22 = 1.0F;
                wvar21 = 1.0F;
            }

            west.setColorRGBA_F(var21 * var17, wvar22 * var17, wvar23 * var17, 1.0F);
            west.addVertex(par2 + 1.0D, par4 + (double)var13 - 1.0D, par6 + 1.0D);
            west.addVertex(par2 + 1.0D, par4 + (double)var13 - 1.0D, par6);
            west.addVertex(par2 + 1.0D, par4 + (double)var13, par6);
            west.addVertex(par2 + 1.0D, par4 + (double)var13, par6 + 1.0D);
            west.draw();
            
            //south
            Tessellator south = Tessellator.instance;
            north.startDrawingQuads();
            float svar21 = var12.nextFloat() * 0.5F + 0.1F;
            float svar22 = var12.nextFloat() * 0.5F + 0.4F;
            float svar23 = var12.nextFloat() * 0.5F + 0.5F;

            if (var14 == 0)
            {
                svar23 = 1.0F;
                svar22 = 1.0F;
                svar21 = 1.0F;
            }

            south.setColorRGBA_F(var21 * var17, svar22 * var17, svar23 * var17, 1.0F);
            south.addVertex(par2 + 1.0D, par4 + (double)var13 - 1.0D, par6);
            south.addVertex(par2, par4 + (double)var13 - 1.0D, par6);
            south.addVertex(par2, par4 + (double)var13, par6);
            south.addVertex(par2 + 1.0D, par4 + (double)var13, par6);
            south.draw();
            
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_S);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_T);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_R);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_Q);
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    private FloatBuffer func_76907_a(float par1, float par2, float par3, float par4)
    {
        this.field_76908_a.clear();
        this.field_76908_a.put(par1).put(par2).put(par3).put(par4);
        this.field_76908_a.flip();
        return this.field_76908_a;
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderEndPortalTileEntity((TileEntityPortalBase)par1TileEntity, par2, par4, par6, par8);
    }
	
}
