package com.ffwatl.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("loggedInUser")
public class AddNewItemController {

    /*@Autowired
    private ProductAttributeService sizeService;
    @Autowired
    private ClothesItemService clothesItemService;
    @Autowired
    private Settings settings;

    private static final String[] ENDS = new String[]{"s.jpg","m.jpg","l.jpg","xl_.jpg"};


    private static final Logger logger = LogManager.getLogger("com.ffwatl.admin.web.controller.AddNewItemController");

    @RequestMapping(value = "/admin/new/item", method = RequestMethod.GET)
    public String addNewItem(){
        return "admin/new/newItem";
    }

    @RequestMapping(value = "/admin/new/item/clothes", method = RequestMethod.POST)
    public String addClothesItem(@RequestParam("files[]") List<MultipartFile> file, HttpServletRequest request,
                                 ModelMap model, @RequestParam String item) throws IOException {
        ClothesItemPresenter clothesItem;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String dir;
        long id;
        try {
            clothesItem = new ObjectMapper().readValue(item, ClothesItemPresenter.class);
            logger.info(clothesItem);
            id = clothesItemService.save(Optional.of(clothesItem), email);
            dir = settings.getPhotoDir() + "item_" + id;
            if(clothesItem.isEdit()) {
                for(int i: clothesItem.getRemovedImgs()){
                    WebUtil.deleteImagesByEnds(dir, i + "s.jpg");
                    WebUtil.deleteImagesByEnds(dir, i + "m.jpg");
                    WebUtil.deleteImagesByEnds(dir, i + "l.jpg");
                    WebUtil.deleteImagesByEnds(dir, i + "xl_.jpg");
                }
                WebUtil.rearrangeImages(dir, ENDS);
            }
            proceedImages(dir, file, clothesItem.isEdit());
            if(clothesItem.isEdit()) {
                sizeService.removeById(clothesItem.getOldSizes());
                logger.info("Product with id = " + clothesItem.getId() + " was UPDATED by user: " + email);
            }
            else logger.info("New item was ADDED by user: " + email + ", item name: " +
                    clothesItem.getProductName().getLocale_en()+", id = "+id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            *//*throw e;*//*
            model.addAttribute("isError", true);
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/new/result";
        }
        Cookie[] cookies = request.getCookies();
        String cookie = LocaleContextHolder.getLocale().getDisplayLanguage();
        if(cookies != null){
            for(Cookie c: cookies){
                if(c.getName().equals("app")) cookie = c.getValue();
            }
        }
        model.addAttribute("itemId", id);
        model.addAttribute("isError", false);
        model.addAttribute("itemName", clothesItem.getProductName().getValue(cookie));
        return "admin/new/result";
    }

    private void proceedImages(String dirPath, List<MultipartFile> file, boolean editMode) throws IOException {
        if(!editMode) WebUtil.createFolder(dirPath);
        int count = WebUtil.finder(dirPath, "xl_.jpg").length + 1;
        for(MultipartFile f: file){
            resizeAndSave(f, dirPath, count++);
        }
    }

    private void resizeAndSave(MultipartFile f, String dirPath, int count) throws IOException {
        if (f == null || f.getInputStream() == null) return;
        BufferedImage image = ImageIO.read(f.getInputStream());
        if(image == null) return;
        try(OutputStream os = new FileOutputStream(new File(dirPath + "\\"+"image"+count+"xl_.jpg"))) {
            ImageIO.write(Scalr.resize(image,115), "jpeg", new File(dirPath + "\\image"+count+"s.jpg"));
            ImageIO.write(Scalr.resize(image,230), "jpeg", new File(dirPath + "\\image"+count+"m.jpg"));
            ImageIO.write(Scalr.resize(image,370), "jpeg", new File(dirPath + "\\image"+count+"l.jpg"));
            os.write(f.getBytes());
        }catch (IOException e){
            logger.error("Error while saving item images");
            logger.error(e.getMessage());
            throw e;
        }
    }*/
}