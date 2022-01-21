package io.leaderli.litil.generate;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import io.vavr.control.Try;
import org.junit.Assert;
import org.junit.Test;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author leaderli
 * @since 2022/1/21 12:26 PM
 */
public class GenerateLitil {


    public void writeToFile(String packageSuffix, boolean test, TypeSpec typeSpec) {

        String test_classes_path = Objects.requireNonNull(this.getClass().getResource("/")).getFile();
        String project_path = test_classes_path.split("target")[0];
        String source_path = project_path + (test ? "src/test/java" : "src/main/java/");
        try {
            String packageName = "io.leaderli.litil." + packageSuffix;
            JavaFile.builder(packageName, typeSpec).build().writeToFile(new File(source_path));
            File file = new File(packageName.replace(".", "/"));
            Assert.assertTrue(file.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWriteToFile() throws Throwable {

//        writeToFile("util", false, TypeSpec.classBuilder("Test").addModifiers(Modifier.PUBLIC).build());
//        writeToFile("util", true, TypeSpec.classBuilder("Test").addModifiers(Modifier.PUBLIC).build());

        System.out.println(new File("123").delete());

    }
}
