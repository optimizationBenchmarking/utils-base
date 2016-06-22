package org.optimizationBenchmarking.utils.config;

import java.security.PrivilegedAction;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;

/** the configuration loader */
final class _ConfigurationLoader
    implements PrivilegedAction<Configuration> {

  /** the command line arguments */
  private final String[] m_args;

  /**
   * create the configuration loader
   *
   * @param args
   *          the loader
   */
  _ConfigurationLoader(final String[] args) {
    this.m_args = ((args != null) ? args : EmptyUtils.EMPTY_STRINGS);
  }

  /** {@inheritDoc} */
  @Override
  public final Configuration run() {
    final _ConfigMap configMap;
    String string;
    char firstChar, secondChar;
    Object object;

    try (
        final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder(
            null, false)) {

      configurationBuilder.putMap(System.getenv());
      configurationBuilder.putMap(System.getProperties());

      try {
        configMap = configurationBuilder.m_data.m_data;

        findEnv: if (!(configMap.containsKey(Configuration.PARAM_PATH))) {
          for (firstChar = '!'; firstChar <= '&'; firstChar++) {
            for (secondChar = '!'; secondChar <= '&'; secondChar++) {
              string = Configuration.PARAM_PATH;
              if (firstChar != '"') {
                string = (firstChar + string);
              }
              if (secondChar != '"') {
                string = (string + secondChar);
              }
              object = configMap.remove(string);
              if (object != null) {
                configMap.put(Configuration.PARAM_PATH, object);
                break findEnv;
              }
            }
          }
        }

        configurationBuilder._configure(this.m_args);
      } catch (final Throwable tt) {
        ErrorUtils.logError(
            configurationBuilder.m_data
                .getLogger(Configuration.PARAM_LOGGER, null), //
            "Severe error during setup.", //$NON-NLS-1$
            tt, false, RethrowMode.AS_RUNTIME_EXCEPTION);
      }
      return configurationBuilder.compile();
    }
  }
}
