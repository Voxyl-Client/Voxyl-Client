package optifine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import bwp.cosmetics.impl.CapeBoolean;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FilenameUtils;

public class CapeUtils
{
    public static void downloadCape(final AbstractClientPlayer player)
    {

        String username = player.getNameClear();

        if (username != null && !username.isEmpty())
        { //You may replace "CapeBoolean" with your classname if you created one with another name for the boolean
            if(CapeBoolean.Cape == true) {				//Edit this to your cape image's file location
                final ResourceLocation capeLocation = new ResourceLocation("bwp/bwp-cape-1/image-0.png");
                if(capeLocation !=null) {
                    player.setLocationOfCape(capeLocation);
                }
            }
        }
        else {
            String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
            String mptHash = FilenameUtils.getBaseName(ofCapeUrl);
            //TODO - HOOK IN WITH MYSQL AND MAKE SETTINGS
            final ResourceLocation resourcelocation = new ResourceLocation("bwp/bwp-cape-1/image-0.png");
            TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            ITextureObject tex = texturemanager.getTexture(resourcelocation);

            if (tex != null && tex instanceof ThreadDownloadImageData)
            {
                ThreadDownloadImageData thePlayer = (ThreadDownloadImageData)tex;

                if (thePlayer.imageFound != null)
                {
                    if (thePlayer.imageFound.booleanValue())
                    {
                        player.setLocationOfCape(resourcelocation);
                    }

                    return;
                }
            }


            IImageBuffer iimagebuffer = new IImageBuffer()
            {
                ImageBufferDownload ibd = new ImageBufferDownload();
                public BufferedImage parseUserSkin(BufferedImage image)
                {
                    return CapeUtils.parseCape(image);
                }
                public void skinAvailable()
                {
                    player.setLocationOfCape(resourcelocation);
                }
            };
            ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData((File)null, username, (ResourceLocation)null, iimagebuffer);
            threaddownloadimagedata1.pipeline = true;
            texturemanager.loadTexture(resourcelocation, threaddownloadimagedata1);
        }
    }

    public static BufferedImage parseCape(BufferedImage p_parseCape_0_)
    {
        int i = 64;
        int j = 32;
        int k = p_parseCape_0_.getWidth();

        for (int l = p_parseCape_0_.getHeight(); i < k || j < l; j *= 2)
        {
            i *= 2;
        }

        BufferedImage bufferedimage = new BufferedImage(i, j, 2);
        Graphics graphics = bufferedimage.getGraphics();
        graphics.drawImage(p_parseCape_0_, 0, 0, (ImageObserver)null);
        graphics.dispose();
        return bufferedimage;
    }
}
