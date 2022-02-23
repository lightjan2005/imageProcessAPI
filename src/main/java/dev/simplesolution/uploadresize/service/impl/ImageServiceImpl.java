package dev.simplesolution.uploadresize.service.impl;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.simplesolution.uploadresize.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${image.folder}")
    private String imageFolder;

    @Value("${image.size}")
    private Integer imageSize;

    private Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    private File fileToModify;

    @Override
    public void setFile(File sourceFile) {
        fileToModify = sourceFile;
    }

    @Override
    public File getFile() {
        return fileToModify;
    }

    @Override
    public boolean resizeImage(double percent) {
        try {

            BufferedImage inputImage = ImageIO.read(fileToModify);



            int width = (int) (inputImage.getWidth() * percent);
            int height = (int) (inputImage.getHeight() * percent);

            BufferedImage outputImage = new BufferedImage(width,
                    height, inputImage.getType());


            // scales the input image to the output image
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, width, height, null);
            g2d.dispose();


            // writes to output file
            ImageIO.write(outputImage, "jpg", fileToModify);
            outputImage.flush();

            return true;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean flip(int flip){
        try{
            BufferedImage image = ImageIO.read(fileToModify);
            int width = image.getWidth();
            int height = image.getHeight();
            BufferedImage flipped = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

            for(int y= 0; y <height; y++){
                for(int x = 0; x < width;x++){
                    // flip horizontal
                    if(flip == 0){
                        flipped.setRGB((width-1)- x,y,image.getRGB(x,y));
                    }
                    // flip vertical
                    else if(flip == 1){
                        flipped.setRGB(x,(height-1)-y,image.getRGB(x,y));
                    }
                }
            }

            ImageIO.write(flipped, "jpg", fileToModify);
            flipped.flush();
            return true;

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }



    @Override
    public boolean convertGreyscale() {
        try {

            BufferedImage bufferedImage = ImageIO.read(fileToModify);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            for(int y = 0; y < height;y++){
                for(int x = 0; x < width;x++){
                    int p = bufferedImage.getRGB(x,y);

                    int a = (p>>24)&0xff;
                    int r = (p>>16)&0xff;
                    int g = (p>>8)&0xff;
                    int b = p&0xff;

                    int avg = (r+g+b)/3;

                    p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                    bufferedImage.setRGB(x,y,p);
                }
            }

            ImageIO.write(bufferedImage, "jpg", fileToModify);
            bufferedImage.flush();
            return true;

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean rotateDegrees(int degrees) {

        try {
            BufferedImage bufferedImage = ImageIO.read(fileToModify);

            final double rads = Math.toRadians(degrees);
            final double sin = Math.abs(Math.sin(rads));
            final double cos = Math.abs(Math.cos(rads));
            final int w = (int) Math.floor(bufferedImage.getWidth() * cos + bufferedImage.getHeight() * sin);
            final int h = (int) Math.floor(bufferedImage.getHeight() * cos + bufferedImage.getWidth() * sin);
            final BufferedImage rotatedImage = new BufferedImage(w, h, bufferedImage.getType());
            final AffineTransform at = new AffineTransform();
            at.translate(w / 2, h / 2);
            at.rotate(rads,0, 0);
            at.translate(-bufferedImage.getWidth() / 2, -bufferedImage.getHeight() / 2);
            final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            rotateOp.filter(bufferedImage,rotatedImage);

            ImageIO.write(rotatedImage, "jpg", fileToModify);
            bufferedImage.flush();
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }


    }



    @Override
    public boolean modifyImage(double resizePercent, int horVer, int greyscale, int degrees, int rotate90, int generateThumbnail) {


        if(resizePercent != 0){
            resizeImage(resizePercent);
        }
        if(horVer == 0){
            flip(0);
        }
        if(horVer == 1){
            flip(1);
        }
        if(greyscale == 1){
            convertGreyscale();
        }
        if(degrees != 0){
            rotateDegrees(degrees);
        }
        if(rotate90 == 1){
            rotateDegrees(90);
        }
        if(rotate90 == 2){
            rotateDegrees(-90);
        }
        if(generateThumbnail == 1){
            resizeImage(0.2);
        }


        String newFileName = FilenameUtils.getBaseName(fileToModify.getName())
                + "_" + "GreyScaled" + "."
                + FilenameUtils.getExtension(fileToModify.getName());
        Path path = Paths.get(imageFolder,newFileName);
        fileToModify = path.toFile();

        return true;
    }




}