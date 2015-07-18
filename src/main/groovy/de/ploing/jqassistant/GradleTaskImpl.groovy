package de.ploing.jqassistant

import org.gradle.api.Project


class GradleTaskImpl {
    static void jQAssistantReset(Project project) {
        JQAssistantPlugin.fromProject(project).store.reset()
    }
}
