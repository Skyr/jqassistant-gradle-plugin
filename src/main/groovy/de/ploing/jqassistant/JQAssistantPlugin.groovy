package de.ploing.jqassistant

import com.buschmais.jqassistant.core.plugin.api.PluginConfigurationReader
import com.buschmais.jqassistant.core.plugin.api.PluginRepository
import com.buschmais.jqassistant.core.plugin.api.PluginRepositoryException
import com.buschmais.jqassistant.core.plugin.impl.PluginConfigurationReaderImpl
import com.buschmais.jqassistant.core.plugin.impl.PluginRepositoryImpl
import com.buschmais.jqassistant.core.store.api.Store
import com.buschmais.jqassistant.core.store.impl.EmbeddedGraphStore
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.plugins.PluginInstantiationException


class JQAssistantPlugin implements Plugin<Project> {
    static final String DIRNAME = "jqassistant"
    static final Logger logger = Logging.getLogger(JQAssistantPlugin.class)

    protected Project project = null
    protected Store store = null


    static JQAssistantPlugin fromProject(Project project) {
        return project.plugins.getPlugin("de.ploing.jqassistant")
    }

    protected Store createStore(File directory, List<Class<?>> types) {
        logger.debug("Creating JQAssistant store in ${directory}")
        directory.getParentFile().mkdirs()
        Store store = new EmbeddedGraphStore(directory.getAbsolutePath())
        store.start(types)
        return store
    }

    protected Store initStore() {
        // Set up jqassistant plugin
        PluginConfigurationReader confReader = new PluginConfigurationReaderImpl()
        PluginRepository repo = new PluginRepositoryImpl(confReader)

        // Determine descriptor types
        List<Class<?>> descriptorTypes
        try {
            descriptorTypes = repo.getModelPluginRepository().getDescriptorTypes()
        } catch (PluginRepositoryException e) {
            throw new PluginInstantiationException("Cannot determine model types.", e)
        }

        // Determine store directory
        File directory = new File(project.getBuildDir(), DIRNAME)

        return createStore(directory, descriptorTypes)
    }

    synchronized Store getStore() {
        if (store==null) {
            store = initStore()
        }
        return store
    }

    @Override
    void apply(Project target) {
        project = target
        // Create extension
        target.extensions.create(JQAssistantExtension.NAME, JQAssistantExtension)
        JQAssistantExtension ext = target.extensions.getByName(JQAssistantExtension.NAME)
        // Register tasks
        target.task("jQAssistantReset")
    }
}
