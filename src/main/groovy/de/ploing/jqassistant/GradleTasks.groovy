package de.ploing.jqassistant

import com.buschmais.jqassistant.core.store.api.Store
import com.buschmais.jqassistant.core.store.impl.EmbeddedGraphStore
import com.buschmais.jqassistant.neo4j.backend.bootstrap.EmbeddedNeo4jServer
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskInstantiationException


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


    static void jQAssistantServer(Project project) {
        Store store = JQAssistantPlugin.fromProject(project).store

        EmbeddedGraphStore embeddedGraphStore = (EmbeddedGraphStore) store
        EmbeddedNeo4jServer server = embeddedGraphStore.getServer()
        server.start("localhost", 7474)
        println("Running server for project ${project.name} running at ${server.baseUri()}")
        println("Press <Enter> to finish.")
        try {
            System.in.read()
        } catch (IOException e) {
            throw new TaskInstantiationException("Cannot read from System.in.", e)
        } finally {
            server.stop()
        }
    }


    @TaskAction
    void jQAssistantServer() {
        jQAssistantServer(project)
    }
}
