package dev.simplesolution.uploadresize.controller;
import actionsPackage.Actions;
import dev.simplesolution.uploadresize.service.impl.ActionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@RestController
public class ActionsController {

    @Autowired
    private ActionsService actionsService;

    @RequestMapping("/actions")
    public Actions getAllActions(){
        return actionsService.getAllActions();
    }

    @RequestMapping(method = RequestMethod.POST, value ="/actions")
    public void addAction(@RequestBody Actions action){
        actionsService.setActions(action.getFlip(),action.getRotateDegrees(),action.getResizePercent(),action.getGenerateThumbnail(),action.getRotate90(), action.getGreyscale());
    }



}
