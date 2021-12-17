package io.appq.checkstyle.checks.common;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringConcatenationCheckTest {

    private static final int AMOUNT_OF_COLUMN_ANNOTATION_ERRORS = 3;

    @Test
    public void checkStringConcatenation() throws Exception {
        Checker checker = prepareCheckStyleChecker();
        List<File> files = prepareFilesToBeChecked();
        int numberOfErrors = checker.process(files);
        assertThat(numberOfErrors, is(AMOUNT_OF_COLUMN_ANNOTATION_ERRORS));
    }

    private Checker prepareCheckStyleChecker() throws CheckstyleException {
        Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(prepareConfiguration());
        return checker;
    }

    private DefaultConfiguration prepareConfiguration() {
        DefaultConfiguration checks = new DefaultConfiguration("Checks");
        DefaultConfiguration treeWalker = new DefaultConfiguration("TreeWalker");
        DefaultConfiguration columnAnnotationCheck = new DefaultConfiguration(StringConcatenationCheck.class.getCanonicalName());
        checks.addChild(treeWalker);
        treeWalker.addChild(columnAnnotationCheck);
        return checks;
    }

    private List<File> prepareFilesToBeChecked() {
        String testFileName = "TestClassWithStringConcatenation.java";
        URL testFileUrl = getClass().getResource(testFileName);
        File testFile = new File(testFileUrl.getFile());
        List<File> files = new ArrayList<File>();
        files.add(testFile);
        return files;
    }
}
