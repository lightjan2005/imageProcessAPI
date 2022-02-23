package dev.simplesolution.uploadresize.controller;

import java.io.File;

import actionsPackage.Actions;
import dev.simplesolution.uploadresize.service.impl.ActionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.simplesolution.uploadresize.service.FileUploadService;
import dev.simplesolution.uploadresize.service.ImageService;

@Controller
public class ImageUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ActionsService actionsService;

    @GetMapping("")
    public String uploadImage() {
        return "uploadImage";
    }


    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
        if(imageFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please choose file to upload.");
            return "redirect:/";
        }

        // upload image file
        File file = fileUploadService.upload(imageFile);
        if(file == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Upload failed.");
            return "redirect:/";

        }

        // set file
        imageService.setFile(file);
        Actions actions = actionsService.getAllActions();

        // apply actions
        boolean modifyImage = imageService.modifyImage(actions.getResizePercent(),actions.getFlip(),actions.getGreyscale(),actions.getRotateDegrees(), actions.getRotate90(), actions.getGenerateThumbnail());
        if(!modifyImage) {
            redirectAttributes.addFlashAttribute("errorMessage", "Convert Greyscale failed.");
            return "redirect:/";
        }


        redirectAttributes.addFlashAttribute("successMessage", "File upload successfully.");
        return "redirect:/";
    }

}