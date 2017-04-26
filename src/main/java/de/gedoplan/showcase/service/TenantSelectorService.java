package de.gedoplan.showcase.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

/**
 * Tenant-Auswahl-Service.
 *
 * Dieser Service symbolisiert die aktuelle Tenant-Auswahl durch den User. Der aktuell ausgewählte Tenat ist in der Property currentTenant
 * enthalten. Da der Service SessionScoped ist, hat jeder User seinen eigenen Tenant.
 *
 * Die verfügbaren Tenants sind hier fest programmiert, könnten im echten Leben aber dynamisch ermittelt werden.
 *
 * @author dw
 */
@SessionScoped
public class TenantSelectorService implements Serializable {
  private List<String> tenants = new ArrayList<>();

  private String currentTenant;

  @PostConstruct
  void postConstruct() {
    this.tenants.add("HUGO");
    this.tenants.add("OTTO");
    this.tenants.add("WILLY");

    this.currentTenant = this.tenants.get(0);
  }

  public String getCurrentTenant() {
    return this.currentTenant;
  }

  public void setCurrentTenant(String currentDbUrl) {
    this.currentTenant = currentDbUrl;
  }

  public List<String> getTenants() {
    return this.tenants;
  }
}
