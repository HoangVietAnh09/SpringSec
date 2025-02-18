package com.example.springSec.Controller;


import com.fasterxml.uuid.Generators;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UploadFileController {

    private static final String upload_foldder = "D:/Spring/springSec/springSec/src/main/resources/uploads/";
    private static String randomPath = "";


    @GetMapping("/images")
    public String listImages(Model model) {
        File folder = new File(upload_foldder);
        List<String> images = Arrays.stream(folder.listFiles())
                .filter(File::isFile)
                .map(File::getName)
                .collect(Collectors.toList());

        model.addAttribute("images", images);
        return "image_list";
    }

    @GetMapping("/view")
    public String viewImage(@RequestParam("name") String name, Model model) {
        model.addAttribute("imageName", name);
        return "image_view";
    }

    @GetMapping("/any")
    public String index() {
        System.out.println("test1");
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            System.out.println("test2");
            redirectAttributes.addFlashAttribute("message", "Please select a file");
            return "redirect:/file/status";
        }

        try{
            String fileName  = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            UUID uuid = Generators.timeBasedGenerator().generate();
            randomPath = upload_foldder + uuid + suffix;
            System.out.println(randomPath);
            byte[] bytes = file.getBytes();
            Path path = Paths.get(upload_foldder + file.getOriginalFilename());
            Files.write(path, bytes);


            redirectAttributes.addFlashAttribute("message", "File uploaded successfully");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            log.error(e.getMessage());
        }
        return "redirect:/file/status";

    }

    @PostMapping("/upload/image")
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            return "Please select a file";
        }

        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf("."));
        String mimeType = file.getContentType();
        String filePath = upload_foldder + filename;
        File excelFile = convert(file);
        String[] whiteList = {".jpg", ".png", ".jpeg", ".gif", ".bmp", ".ico"};
        boolean suffixFlag = false;
        for(String i : whiteList){
            if(ext.toLowerCase().equals(i)){
                suffixFlag = true;
                break;
            }
        }
        if(!suffixFlag){
            log.error("[-] Suffix error: " + ext);
            deleteFile(filePath);
            return "Upload failed. Illeagl picture.";
        }

        String[] mimeTypeBlackList = {
                "application/java-archive",
                "application/java-vm",
                "application/x-java-jnlp-file",
                "application/java-serialized-object",
                "text/x-java-source,java"
        };

        for(String i : mimeTypeBlackList){
            if(escapeIME(mimeType).toLowerCase().contains(i)){
                log.error("[-] MimeType error: " + mimeType);
                deleteFile(filePath);
                return "Upload failed. Illeagl picture.";
            }
        }

        boolean isImage = isImage(excelFile);
        deleteFile(randomPath);

        if(!isImage){
            log.error("[-] Image error");
            deleteFile(filePath);
            return "Upload failed. Illeagl picture.";
        }

        try{
               byte[] bytes = file.getBytes();
               Path path = Paths.get(upload_foldder + file.getOriginalFilename());
               Files.write(path, bytes);
        }catch (IOException e){
            log.error(e.toString());
            deleteFile(filePath);
            return "Upload failed. Illeagl picture.";

        }

        log.info("[+] Safe file. Suffix: {}, MIME: {}", ext, mimeType);
        log.info("[+] Successfully uploaded {}", filePath);
        return String.format("You successfully uploaded '%s'", filePath);
    }


    @GetMapping("/status")
    public String uploadStatus(){
        return "uploadStatus";
    }


    private File convert(MultipartFile file) throws IOException {
        String fileName  = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        UUID uuid = Generators.timeBasedGenerator().generate();
        randomPath = upload_foldder + uuid + suffix;
        File convertFile = new File(randomPath);
        boolean ret = convertFile.createNewFile();
        if(!ret){
            return null;
        }

        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;
    }

    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if(file.isFile() && file.exists()){
            if(file.delete()){
                log.info("[-] File deleted successfully");
                return;
            }
        }
        log.info(filePath + " deleted failed");
    }

    public String escapeIME(String s){
        StringBuilder sb = new StringBuilder();
        s = s.toLowerCase();
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || c == '/' || c == '.' || c == '-'){

                sb.append(c);
            }
            return sb.toString();
        }
        if(!s.matches("^[a-z0-9/.\\-]+$")) return "Hack";
        return s;
    }

    private boolean isImage(File file) throws IOException {
        BufferedImage sb = ImageIO.read(file);
        return sb != null;
    }
}


