package de.ploing.jqassistant

import com.buschmais.jqassistant.core.store.api.Store
import com.buschmais.jqassistant.plugin.java.api.scanner.JavaScope
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

    long nodeCountInStore(Store store) {
        return store.executeQuery("start n=node(*) match n return count(n)").first().get("count(n)", Long)
    }

    @Test
    void resetTaskClearsDatabase() {
        Store store = JQAssistantPlugin.fromProject(project).store
        assertEquals("Precondition: Store is empty", 0, nodeCountInStore(store))
        ScanTask scan = new ScanTask(project)
        scan.jQAssistantScan { scanner ->
            scanner.scan(ResetTest.class, null, JavaScope.CLASSPATH)
        }
        assertNotEquals("Precondition: Store contains at least one node", 0, nodeCountInStore(store))
        GradleTasks.jQAssistantReset(project)
        assertEquals(0, nodeCountInStore(store))
    }
}
