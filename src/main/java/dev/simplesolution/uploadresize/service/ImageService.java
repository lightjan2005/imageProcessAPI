package dev.simplesolution.uploadresize.service;

import actionsPackage.Actions;

import java.io.File;

public interface ImageService {

    void setFile(File sourceFile);
    File getFile();
    boolean resizeImage(double percent);
    boolean flip(int flip);
    boolean convertGreyscale();
    boolean rotateDegrees(int degrees);

    boolean modifyImage(double resize,int flip, int greyscale, int rotate, int rotate90, int generateThumbnail);


}
