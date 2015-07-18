package de.ploing.jqassistant

import com.buschmais.jqassistant.core.store.api.Store
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before

import static org.junit.Assert.*
import org.junit.Test


class ResetTest {
    Project project

    @Before
    void setupProject() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'de.ploing.jqassistant'
    }

    @Test
    void resetTaskIsPresent() {
        assertNotNull(project.tasks.findByPath("jQAssistantReset"))
    }

    @Test
    void resetTaskClearsDatabase() {
        Store store = JQAssistantPlugin.fromProject(project).store
        GradleTaskImpl.jQAssistantReset(project)
    }
}
