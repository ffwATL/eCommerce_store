package com.ffwatl;

import com.ffwatl.fortest.BLogic;
import com.ffwatl.fortest.ThirdExtendedSecond;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Temp {

    private static final List<Class<? extends BLogic>> BLOGICS = new ArrayList<>();

    static {
        /*BLOGICS.add(FirstBLogic.class);*/
        BLOGICS.add(ThirdExtendedSecond.class);
    }
    @Test
    public void cheat(){

    }

    @Test
    @Ignore
    public void convertData(){
        File file = new File("AJI05.DAT");
        if(!file.exists()) throw new AssertionError("file not exist");
        try {
            DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
            FileWriter writer = new FileWriter("converted__15.txt");
            int i = 0;
            boolean skip = false;

            while (inputStream.available() > 0){
                String s = inputStream.readLine();

                if(i == 700) {
                    skip = true;
                }else if(i == 8192){
                    i = 0;
                    skip = false;
                }

                if(!skip){
                    writeResult(s, writer);
                }
                i++;
            }
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResult(final String s, FileWriter writer) throws IOException {
        String[] arr = s.split("    ");
        int value = Integer.valueOf(arr[0])*15;
        StringBuilder builder = new StringBuilder()
                .append("[")
                .append(value)
                .append(",")
                .append(arr[1])
                .append("],");
        writer.append(builder.toString());
    }

    @Test
    @Ignore
    public void readGson() throws FileNotFoundException {
        int i = 5;
        i = i++;
        System.out.println(i);
        /*JsonReader reader = new JsonReader(new FileReader("item_group_tree.json"));
        ProductCategory i = new Gson().fromJson(reader, ProductCategoryImpl.class);*/
        AtomicInteger aI = new AtomicInteger(2);
      /*  fillParent(null, i);*/

    }

    @Test
    @Ignore
    public void images(){
        String dirName = "c:\\atl_folder\\items\\images\\item_2";
        int count = 1;
        String end = "l.jpg";
        for(File f: finder(dirName, end)){
            f.renameTo(new File(dirName+"\\image"+(count++)+end));
        }
    }

    public void rearrangeImages(String dirName, String[] ends){
        for(String end: ends){
            int count = 0;
            for(File f: finder(dirName, end)){
                f.renameTo(new File(dirName+"\\image"+(count++)+end));
            }
        }
    }

    private File[] finder(String dirName, String endName){
        return new File(dirName).listFiles((dir1, filename) -> {return filename.endsWith(endName);});
    }

    @Test
    public void testMethodNamesUnambiguous() {
        int errors = 0;
        StringBuilder sb = new StringBuilder();

        for (Class<? extends BLogic> clazz : BLOGICS) {
            String expectedPackageName = BLogic.class.getPackage().getName();
            List<Method> ms =/* new ArrayList<>()*/ Arrays.asList(clazz.getMethods());
           /* filterByPackageName(expectedPackageName, clazz, ms);*/
            // iterate all methods _declared_ exactly by this class
            for (Method method1 : ms) {
                for (Method method2 : ms) {
                    if (method1.getName().equals(method2.getName()) && method1 != method2 && !method1.equals(method2) && !method1.getDeclaringClass().equals(Object.class)) {
                        // get_cdm_data() with different agrs looks normal
                        sb.append("Method with same name but ambiguous arguments:\n  ");
                        sb.append(method1).append("\n  ");
                        sb.append(method2).append('\n');
                        sb.append(method1.getDeclaringClass()).append('\n');
                        sb.append(method2.getDeclaringClass()).append('\n').append('\n');
                        errors++;
                    }
                }
            }
        }
        if (errors>0) {
            String msg = "testMethodNamesUnambiguous: ERRORS: " + sb.toString();
            System.err.println(msg);
        }
    }


    private void filterByPackageName(String expectedPackageName, Class<?> clazz, List<Method> methodList) {
        if(expectedPackageName == null) {
            return;
        }

        List<Method> methods = Arrays.asList(clazz.getMethods());
        Class<?>[] in = clazz.getInterfaces();

        if(in.length > 0) {
            for(Class<?> c: in) {
                String interfacePackageName = c.getPackage().getName();
                if(!interfacePackageName.startsWith(expectedPackageName)) {
                    Method[] interfaceMethods = c.getMethods();
                    methods.removeAll(Arrays.asList(interfaceMethods));
                }
            }
        }
        methodList.addAll(methods);

        Class<?> superClass = clazz.getSuperclass();
        String superPackageName = superClass.getPackage().getName();

        if(superPackageName.startsWith(expectedPackageName) && !superClass.equals(Object.class)) {
            filterByPackageName(expectedPackageName, superClass, methodList);
        }
    }

   /* private void fillParent(ProductCategoryImpl parent, ProductCategoryImpl child){
        if(child == null) return;
        for(ProductCategoryImpl i: child.setParent(parent).getChild()){
            fillParent(child, i);
        }
    }*/
}