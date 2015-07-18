package de.ploing.jqassistant

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import static org.junit.Assert.*
import org.junit.Test


class ResetTest {
    @Test
    void resetTaskIsPresent() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'de.ploing.jqassistant'

        assertTrue(project.jqassistant instanceof JQAssistantExtension)
        assertNotNull(project.tasks.findByPath("jQAssistantReset"))
    }
}
