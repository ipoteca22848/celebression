package ru.method.celebression.utils.render;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import ru.method.celebression.Celebression;

public final class GifTexture {
    private final List<class_2960> frameTextures = new ArrayList<class_2960>();
    private final List<Integer> delays = new ArrayList<Integer>();
    private final class_2960 gifResource;
    private final String framePrefix;
    private int currentFrame;
    private long lastFrameTime;
    private volatile boolean loaded;

    public GifTexture(class_2960 class_29602) {
        this.gifResource = class_29602;
        this.framePrefix = "dynamic/gif_" + Math.abs(class_29602.toString().hashCode());
        CompletableFuture.runAsync(this::loadFrames);
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public class_2960 getCurrentFrameTexture() {
        if (this.frameTextures.isEmpty()) {
            return null;
        }
        return this.frameTextures.get(Math.floorMod(this.currentFrame, this.frameTextures.size()));
    }

    public void update() {
        int n;
        if (!this.loaded || this.frameTextures.isEmpty()) {
            return;
        }
        long l = System.currentTimeMillis();
        if (l - this.lastFrameTime >= (long)this.delays.get(n = Math.floorMod(this.currentFrame, this.delays.size())).intValue()) {
            this.currentFrame = (n + 1) % this.frameTextures.size();
            this.lastFrameTime = l;
        }
    }

    private void loadFrames() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return;
        }
        Optional optional = class_3102.method_1478().method_14486(this.gifResource);
        if (optional.isEmpty()) {
            Celebression.LOGGER.warn("GIF resource not found: {}", (Object)this.gifResource);
            return;
        }
        ArrayList<int[]> arrayList = new ArrayList<int[]>();
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        try (InputStream inputStream = ((class_3298)optional.get()).method_14482();
             ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);){
            Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("gif");
            if (!iterator.hasNext()) {
                return;
            }
            ImageReader imageReader = iterator.next();
            imageReader.setInput(imageInputStream, false);
            int n = imageReader.getWidth(0);
            int n2 = imageReader.getHeight(0);
            int n3 = imageReader.getNumImages(true);
            int[] nArray = new int[n * n2];
            int[] nArray2 = null;
            for (int i = 0; i < n3; ++i) {
                int n4;
                int n5;
                int n6;
                int n7;
                int n8;
                IIOMetadataNode iIOMetadataNode = (IIOMetadataNode)imageReader.getImageMetadata(i).getAsTree("javax_imageio_gif_image_1.0");
                IIOMetadataNode iIOMetadataNode2 = (IIOMetadataNode)iIOMetadataNode.getElementsByTagName("GraphicControlExtension").item(0);
                IIOMetadataNode iIOMetadataNode3 = (IIOMetadataNode)iIOMetadataNode.getElementsByTagName("ImageDescriptor").item(0);
                String string = iIOMetadataNode2 != null ? iIOMetadataNode2.getAttribute("disposalMethod") : "none";
                int n9 = iIOMetadataNode2 != null ? Math.max(Integer.parseInt(iIOMetadataNode2.getAttribute("delayTime")) * 10, 20) : 100;
                int n10 = iIOMetadataNode3 != null ? Integer.parseInt(iIOMetadataNode3.getAttribute("imageLeftPosition")) : 0;
                int n11 = n8 = iIOMetadataNode3 != null ? Integer.parseInt(iIOMetadataNode3.getAttribute("imageTopPosition")) : 0;
                if ("restoreToPrevious".equals(string)) {
                    nArray2 = (int[])nArray.clone();
                }
                BufferedImage bufferedImage = imageReader.read(i);
                int n12 = bufferedImage.getWidth();
                int n13 = bufferedImage.getHeight();
                for (n7 = 0; n7 < n13; ++n7) {
                    for (n6 = 0; n6 < n12; ++n6) {
                        int n14;
                        n5 = n6 + n10;
                        n4 = n7 + n8;
                        if (n5 >= n || n4 >= n2 || ((n14 = bufferedImage.getRGB(n6, n7)) >> 24 & 0xFF) <= 0) continue;
                        nArray[n4 * n + n5] = n14;
                    }
                }
                arrayList.add((int[])nArray.clone());
                arrayList2.add(n9);
                if ("restoreToBackgroundColor".equals(string)) {
                    for (n7 = 0; n7 < n13; ++n7) {
                        for (n6 = 0; n6 < n12; ++n6) {
                            n5 = n6 + n10;
                            n4 = n7 + n8;
                            if (n5 >= n || n4 >= n2) continue;
                            nArray[n4 * n + n5] = 0;
                        }
                    }
                    continue;
                }
                if (!"restoreToPrevious".equals(string) || nArray2 == null) continue;
                nArray = nArray2;
            }
            imageReader.dispose();
            class_3102.execute(() -> this.registerFrames(class_3102, n, n2, arrayList, arrayList2));
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("Failed to load GIF {}", (Object)this.gifResource, (Object)exception);
        }
    }

    private void registerFrames(class_310 class_3102, int n, int n2, List<int[]> list, List<Integer> list2) {
        this.frameTextures.clear();
        this.delays.clear();
        for (int i = 0; i < list.size(); ++i) {
            int[] nArray = list.get(i);
            class_1011 class_10112 = new class_1011(n, n2, true);
            for (int j = 0; j < n2; ++j) {
                for (int k = 0; k < n; ++k) {
                    class_10112.method_61941(k, j, nArray[j * n + k]);
                }
            }
            class_2960 class_29602 = class_2960.method_60655((String)"celebression", (String)(this.framePrefix + "/" + i));
            class_1043 class_10432 = new class_1043(class_10112);
            class_10432.method_4527(true, false);
            class_3102.method_1531().method_4616(class_29602, (class_1044)class_10432);
            this.frameTextures.add(class_29602);
            this.delays.add(list2.get(i));
        }
        this.currentFrame = 0;
        this.lastFrameTime = System.currentTimeMillis();
        this.loaded = !this.frameTextures.isEmpty();
    }
}

