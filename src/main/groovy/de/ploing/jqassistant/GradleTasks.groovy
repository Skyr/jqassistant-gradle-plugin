package de.ploing.jqassistant

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction


class GradleTasks extends DefaultTask {
    @TaskAction
    void jQAssistantReset() {
        JQAssistantPlugin.fromProject(project).store.reset()
    }
}
