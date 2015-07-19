package de.ploing.jqassistant

import com.buschmais.jqassistant.plugin.java.api.scanner.JavaScope
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder


class RunServer {
    public static void main(String[] args) {
        Project project
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'de.ploing.jqassistant'
        ScanTask scan = new ScanTask(project)
        scan.jQAssistantScan { scanner ->
            scanner.scan(ResetTest.class, null, JavaScope.CLASSPATH)
        }
        GradleTasks.jQAssistantServer(project)
    }
}
