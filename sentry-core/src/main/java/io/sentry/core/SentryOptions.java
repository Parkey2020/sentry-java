package io.sentry.core;

import io.sentry.core.util.NonNull;
import java.util.ArrayList;
import java.util.List;

public class SentryOptions {
  static final SentryLevel DEFAULT_DIAGNOSTIC_LEVEL = SentryLevel.DEBUG;

  private List<EventProcessor> eventProcessors = new ArrayList<>();
  private List<Integration> integrations = new ArrayList<>();

  private String dsn;
  private long shutdownTimeoutMills;
  private boolean debug;
  private @NonNull ILogger logger = NoOpLogger.getInstance();
  private SentryLevel diagnosticLevel = DEFAULT_DIAGNOSTIC_LEVEL;
  private ISerializer serializer;
  private String sentryClientName;
  private BeforeSecondCallback beforeSend;
  private String cacheDirPath;

  public void addEventProcessor(EventProcessor eventProcessor) {
    eventProcessors.add(eventProcessor);
  }

  public List<EventProcessor> getEventProcessors() {
    return eventProcessors;
  }

  public void addIntegration(Integration integration) {
    integrations.add(integration);
  }

  public List<Integration> getIntegrations() {
    return integrations;
  }

  public String getDsn() {
    return dsn;
  }

  public void setDsn(String dsn) {
    this.dsn = dsn;
  }

  public boolean isDebug() {
    return debug;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public @NonNull ILogger getLogger() {
    return logger;
  }

  public void setLogger(ILogger logger) {
    this.logger = logger == null ? NoOpLogger.getInstance() : new DiagnosticLogger(this, logger);
  }

  public SentryLevel getDiagnosticLevel() {
    return diagnosticLevel;
  }

  public void setDiagnosticLevel(SentryLevel diagnosticLevel) {
    if (diagnosticLevel == null) {
      diagnosticLevel = DEFAULT_DIAGNOSTIC_LEVEL;
    }
    this.diagnosticLevel = diagnosticLevel;
  }

  public ISerializer getSerializer() {
    return serializer;
  }

  public void setSerializer(ISerializer serializer) {
    this.serializer = serializer;
  }

  public long getShutdownTimeout() {
    return shutdownTimeoutMills;
  }

  public void setShutdownTimeout(long shutdownTimeoutMills) {
    this.shutdownTimeoutMills = shutdownTimeoutMills;
  }

  public String getSentryClientName() {
    return sentryClientName;
  }

  public void setSentryClientName(String sentryClientName) {
    this.sentryClientName = sentryClientName;
  }

  public BeforeSecondCallback getBeforeSend() {
    return beforeSend;
  }

  public void setBeforeSend(BeforeSecondCallback beforeSend) {
    this.beforeSend = beforeSend;
  }

  public String getCacheDirPath() {
    return cacheDirPath;
  }

  public void setCacheDirPath(String cacheDirPath) {
    this.cacheDirPath = cacheDirPath;
  }

  public interface BeforeSecondCallback {
    SentryEvent execute(SentryEvent event);
  }

  public SentryOptions() {
    integrations.add(new UncaughtExceptionHandlerIntegration());
  }
}