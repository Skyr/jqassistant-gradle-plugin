package de.ploing.jqassistant

import org.gradle.api.Plugin
import org.gradle.api.Project


class JQAssistantPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        target.extensions.create(JQAssistantExtension.NAME, JQAssistantExtension)
        target.task("jQAssistantReset")
    }
}
