package de.ploing.jqassistant

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*


class ScanTest {
    Project project

    @Before
    void setupProject() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'de.ploing.jqassistant'
    }

    @Test
    void scanTaskIsPresent() {
        assertNotNull(project.tasks.findByPath("jQAssistantScan"))
    }

    @Test
    void scannerPluginsFound() {
        ScanTask task = new ScanTask(project)
        assertNotEquals(0, task.scannerPlugins.size())
    }

    @Test
    void runScanTask() {
        // GradleTasks.jQAssistantScan(project)
    }
}
