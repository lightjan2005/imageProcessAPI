package dev.simplesolution.uploadresize.service.impl;

import actionsPackage.Actions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ActionsService {

    private Actions actions = new Actions();

    public void setActions(int flip,int rotateDegrees,double resizePercent, int thumbnail, int rotate,int greyscale){
        actions.setFlip(flip);
        actions.setRotateDegrees(rotateDegrees);
        actions.setResizePercent(resizePercent);
        actions.setGenerateThumbnail(thumbnail);
        actions.setRotate90(rotate);
        actions.setGreyscale(greyscale);
    }

    public Actions getAllActions(){
        return actions;
    }



}
