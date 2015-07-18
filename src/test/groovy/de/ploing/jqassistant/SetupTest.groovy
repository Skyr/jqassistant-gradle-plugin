package de.ploing.jqassistant

import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue


class SetupTest {
    Project project

    @Before
    void setupProject() {
        project = ProjectBuilder.builder().build()
        project.logging.standardOutputCaptureLevel = LogLevel.INFO
        project.pluginManager.apply 'de.ploing.jqassistant'
    }

    @Test
    void extensionIsRegistered() {
        assertTrue(project.extensions.getByName(JQAssistantExtension.NAME) instanceof JQAssistantExtension)
        JQAssistantExtension ext = project.extensions.getByName(JQAssistantExtension.NAME)
        assertNotNull(ext)
    }

    @Test
    void dbIsSetUp() {
        JQAssistantExtension ext = project.extensions.getByName(JQAssistantExtension.NAME)
        assertNotNull(ext.store)
    }
}
