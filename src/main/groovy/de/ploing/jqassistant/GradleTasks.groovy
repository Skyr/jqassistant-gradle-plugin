package de.ploing.jqassistant

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


class GradleTasks extends DefaultTask {
    @TaskAction
    void jQAssistantReset() {
        println "Hello!"
    }
}
