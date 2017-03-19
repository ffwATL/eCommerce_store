package com.ffwatl.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffwatl.admin.catalog.service.ClothesItemPresenter;
import com.ffwatl.admin.i18n.domain.I18n;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sfm.csv.CsvParser;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Utility class for different routine work to simplify developer's life.
 * All the methods are static.
 */
public final class WebUtil {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Service method for conversion *min.css files into normal readable format.
     * @param input file to be converted (*min.css);
     * @param output output file to save result(normal css);
     * @throws IOException
     */
    public static void cssMinToNormal(File input, File output) throws IOException {
        if(input == null) throw new IOException("Input file can't be null!!");
        FileWriter writer = new FileWriter(output);
        logger.trace("Read CSS min file: " + input.getName());
        String out = new String(Files.readAllBytes(input.toPath()));
        for(char c: out.toCharArray()){
            if(c == '}'){
                writer.append('\n');
                writer.append(c);
                writer.append('\n');
            }else writer.append(c);
            if(c == '{' || c == ';'){
                writer.append('\n');
                writer.append('\t');
            }
        }
        writer.flush();
        writer.close();
        logger.trace("CSS min successfully converted to normal.");
    }

    public static List<I18n> getI18nFromCSV(File file){
        List<I18n> list = new ArrayList<>();
        try (FileReader reader = new FileReader(file)) {
            CsvParser
                    .separator(',').quote('\'')
                    .mapTo(I18n.class)
                    .stream(reader)
                    .forEach(list::add);
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        return list;
    }

    public static List<String> getBrandFromCSV(File file){
        List<String> list = new ArrayList<>();
        try (FileReader reader = new FileReader(file)) {
            CsvParser
                    .separator(',').quote('\'')
                    .mapTo(String.class)
                    .stream(reader)
                    .forEach(list::add);
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        return list;
    }

    /**
     * Converts clothes items from UTF8 JSON file to POJO and returns a List of ClothesItemPresenter;
     * @param file JSON file with clothes items data. File should have the following structure:
     *             [
     *                  {clothesItemPresenter object 1},
     *                  {clothesItemPresenter object 2}
     *             ]
     * @return Optional object with List<ClothesItemPresenter> inside
     * @throws IOException
     */
    public static Optional<List<ClothesItemPresenter>> importItemsFromJsonUTF8(@NotNull File file) throws IOException {
        if(!file.exists() || !file.getName().endsWith(".json")) throw new IllegalArgumentException("Wrong input file");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"))){
            char[] buf = new char[(int) file.length()];
            in.read(buf);
            String json = new String(buf);
            ObjectMapper mapper = new ObjectMapper();
            return Optional.of(mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(
                    List.class, ClothesItemPresenter.class)));
        }catch (Exception e){
            logger.error("Error on import items from JSON in UTF8. " + e.getCause().getMessage());
            throw e;
        }
    }

    public static void exportItemsToJsonUTF8(@NotNull File output, @NotNull Optional<List<ClothesItemPresenter>> items)
            throws IOException {
        try(OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(output), "UTF-8")){
            if(items.isPresent()) writer.write(new ObjectMapper().writeValueAsString(items.get()));
            writer.flush();
        }catch (IOException e){
            logger.error("Error on export items to JSON in UTF8. " + e.getCause().getMessage());
            throw e;
        }
    }

    public static void deleteImagesByEnds(String dirPath, String ends){
        for(File f: finder(dirPath, ends)){
            String name = f.getName();
            try {
                Files.deleteIfExists(f.toPath());
                logger.trace(name + " DELETED");
            } catch (IOException e) {
                logger.error("Error on delete file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public synchronized static void createFolder(String dirPath) throws IOException {
        File file = new File(dirPath);
        if(file.exists()){
            FileUtils.cleanDirectory(file);
        } else Files.createDirectory(Paths.get(dirPath).toAbsolutePath());
    }

    public static void rearrangeImages(String dirName, String... ends){
        for(String end: ends){
            int count = 1;
            for(File f: finder(dirName, end)){
                f.renameTo(new File(dirName+"\\image"+(count++)+end));
            }
        }
    }

    public static File[] finder(String dirName, String endName){
        return new File(dirName).listFiles((dir1, filename) -> {return filename.endsWith(endName);});
    }
}