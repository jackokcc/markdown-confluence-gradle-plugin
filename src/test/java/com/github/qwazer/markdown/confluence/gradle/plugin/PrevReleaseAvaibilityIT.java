package com.github.qwazer.markdown.confluence.gradle.plugin;

import org.apache.commons.io.IOUtils;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static com.github.qwazer.markdown.confluence.gradle.plugin.TestHelperUtil.writeFile;
import static org.gradle.testkit.runner.TaskOutcome.FAILED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Anton Reshetnikov on 16 Nov 2016.
 */
public class PrevReleaseAvaibilityIT {

    @Rule public final TemporaryFolder testProjectDir = new TemporaryFolder();
    private File buildFile;
    private File readmeFile;


    @Before
    public void setup() throws IOException {
        buildFile = testProjectDir.newFile("build.gradle");
        readmeFile = testProjectDir.newFile("README.md");
    }


    @Test
    @Ignore
    public void testPrevReleaseAvailability() throws Exception {
        String content = IOUtils.toString(this.getClass().getResource("/gradle_builds/prev_release_availability.gradle")) ;
        writeFile(buildFile, content);
        writeFile(readmeFile, "==hello");
        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("confluence", "--info", "--stacktrace")
                .withDebug(true)
                .buildAndFail();
        System.out.println("result.getOutput() = " + result.getOutput());
        assertTrue(result.getOutput().contains("I/O error on GET request for"));
        assertEquals(result.task(":confluence").getOutcome(), FAILED);

    }


}