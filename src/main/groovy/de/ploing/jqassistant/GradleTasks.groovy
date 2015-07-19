package de.ploing.jqassistant

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction


class GradleTasks extends DefaultTask {
    static void jQAssistantReset(Project project) {
        JQAssistantPlugin.fromProject(project).store.reset()
    }


    @TaskAction
    void jQAssistantReset() {
        jQAssistantReset(project)
    }


    static void jQAssistantScan(Project project) {
        ScanTask task = new ScanTask(project)
        task.jQAssistantScan()
    }


    @TaskAction
    void jQAssistantScan() {
        jQAssistantScan(project)
    }
}
