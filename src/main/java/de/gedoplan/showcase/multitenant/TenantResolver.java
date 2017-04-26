package de.gedoplan.showcase.multitenant;

import de.gedoplan.showcase.service.TenantSelectorService;

import javax.enterprise.inject.spi.CDI;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class TenantResolver implements CurrentTenantIdentifierResolver {

  TenantSelectorService tenantSelectorService;

  @Override
  public String resolveCurrentTenantIdentifier() {
    if (this.tenantSelectorService == null) {
      this.tenantSelectorService = CDI.current().select(TenantSelectorService.class).get();
    }

    return this.tenantSelectorService.getCurrentTenant();
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return false;
  }

}
