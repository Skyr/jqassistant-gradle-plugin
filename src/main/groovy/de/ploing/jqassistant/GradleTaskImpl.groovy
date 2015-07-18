package de.ploing.jqassistant

import com.buschmais.jqassistant.core.plugin.api.PluginRepository
import com.buschmais.jqassistant.core.plugin.api.PluginRepositoryException
import com.buschmais.jqassistant.core.plugin.api.ScannerPluginRepository
import com.buschmais.jqassistant.core.plugin.api.ScopePluginRepository
import com.buschmais.jqassistant.core.scanner.api.Scanner
import com.buschmais.jqassistant.core.scanner.api.ScannerContext
import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin
import com.buschmais.jqassistant.core.scanner.impl.ScannerContextImpl
import com.buschmais.jqassistant.core.scanner.impl.ScannerImpl
import com.buschmais.jqassistant.core.store.api.Store
import org.gradle.api.Project
import org.gradle.api.tasks.TaskInstantiationException


class GradleTaskImpl {
    static void jQAssistantReset(Project project) {
        JQAssistantPlugin.fromProject(project).store.reset()
    }

    static void jQAssistantScan(Project project) {
        Store store = JQAssistantPlugin.fromProject(project).store
        PluginRepository pluginRepositoryProvider = JQAssistantPlugin.fromProject(project).pluginRepositoryProvider

        List<ScannerPlugin<?, ?>> scannerPlugins;
        ScannerContext scannerContext = new ScannerContextImpl(store);
        ScannerPluginRepository scannerPluginRepository = pluginRepositoryProvider.getScannerPluginRepository();
        try {
            scannerPlugins = scannerPluginRepository.getScannerPlugins(scannerContext, getPluginProperties());
        } catch (PluginRepositoryException e) {
            throw new TaskInstantiationException("Cannot determine scanner plugins.", e);
        }
        ScopePluginRepository scopePluginRepository = pluginRepositoryProvider.getScopePluginRepository();
        Scanner scanner = new ScannerImpl(scannerContext, scannerPlugins, scopePluginRepository.getScopes());
        store.beginTransaction();
        try {
            // scanner.scan(mavenProject, mavenProject.getFile().getAbsolutePath(), MavenScope.PROJECT);
        } finally {
            store.commitTransaction();
        }
    }
}
