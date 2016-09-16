package com.ffwatl.util;


import com.ffwatl.domain.i18n.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sfm.csv.CsvParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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

}