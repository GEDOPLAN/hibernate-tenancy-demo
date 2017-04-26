package de.gedoplan.showcase.multitenant;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;

public class TenantConnectionProvider implements MultiTenantConnectionProvider, ServiceRegistryAwareService {

  private DataSource dataSource;

  @Override
  public void injectServices(ServiceRegistryImplementor serviceRegistry) {
    Context ctx = null;
    try {

      ctx = new InitialContext();
      this.dataSource = (DataSource) ctx.lookup("java:/jdbc/showcase");

    } catch (NamingException e) {
      throw new RuntimeException("Cannot find datasource", e);
    } finally {
      try {
        ctx.close();
      } catch (Exception e) {
        // ignore
      }
    }

  }

  @Override
  public boolean isUnwrappableAs(Class unwrapType) {
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> unwrapType) {
    return null;
  }

  @Override
  public Connection getAnyConnection() throws SQLException {
    return this.dataSource.getConnection();
  }

  @Override
  public void releaseAnyConnection(Connection connection) throws SQLException {
    connection.close();
  }

  @Override
  public Connection getConnection(String tenantIdentifier) throws SQLException {
    Connection connection = this.dataSource.getConnection();
    connection.createStatement().execute("SET SCHEMA " + tenantIdentifier);
    return connection;
  }

  @Override
  public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
    connection.close();
  }

  @Override
  public boolean supportsAggressiveRelease() {
    return false;
  }

}
