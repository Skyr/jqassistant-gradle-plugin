package de.ploing.jqassistant

import com.buschmais.jqassistant.core.plugin.api.PluginRepository
import com.buschmais.jqassistant.core.plugin.api.PluginRepositoryException
import com.buschmais.jqassistant.core.plugin.api.ScannerPluginRepository
import com.buschmais.jqassistant.core.plugin.api.ScopePluginRepository
import com.buschmais.jqassistant.core.scanner.api.Scanner
import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin
import com.buschmais.jqassistant.core.scanner.impl.ScannerImpl
import com.buschmais.jqassistant.core.store.api.Store
import org.gradle.api.Project
import org.gradle.api.tasks.TaskInstantiationException


class ScanTask {
    Store store
    PluginRepository pluginRepositoryProvider
    private List<ScannerPlugin<?, ?>> scannerPlugins = null


    ScanTask(Project project) {
        store = JQAssistantPlugin.fromProject(project).store
        pluginRepositoryProvider = JQAssistantPlugin.fromProject(project).pluginRepositoryProvider
    }


    protected Map<String, Object> getPluginProperties() {
        Map<String, Object> properties = new HashMap<>()

        /*
         * Specifies a list of directory names relative to the root module
         * containing additional rule files.
        @Parameter(property = "jqassistant.scan.includes")
        protected List<ScanInclude> scanIncludes;

         * Specifies properties to be passed to the scanner plugins.
        @Parameter(property = "jqassistant.scan.properties")
        private Map<String, Object> scanProperties;

        if (scanProperties != null) {
            properties.putAll(scanProperties);
        }
        properties.put(ScanInclude.class.getName(), scanIncludes);
        */

        return properties
    }


    List<ScannerPlugin<?, ?>> getScannerPlugins() {
        if (scannerPlugins==null) {
            ScannerPluginRepository scannerPluginRepository = pluginRepositoryProvider.getScannerPluginRepository()
            try {
                scannerPlugins = scannerPluginRepository.getScannerPlugins(getPluginProperties());
            } catch (PluginRepositoryException e) {
                throw new TaskInstantiationException("Cannot determine scanner plugins.", e);
            }
        }
        return scannerPlugins
    }


    void jQAssistantScan(Closure scannerCalls) {
        ScopePluginRepository scopePluginRepository = pluginRepositoryProvider.getScopePluginRepository();
        Scanner scanner = new ScannerImpl(store, getScannerPlugins(), scopePluginRepository.getScopes());
        store.beginTransaction();
        try {
            scannerCalls(scanner)
        } finally {
            store.commitTransaction();
        }
    }


    void jQAssistantScan() {
        jQAssistantScan { scanner ->
            // scanner.scan(mavenProject, mavenProject.getFile().getAbsolutePath(), MavenScope.PROJECT);
        }
    }
}
