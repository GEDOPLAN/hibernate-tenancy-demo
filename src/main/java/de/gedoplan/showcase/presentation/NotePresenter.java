package de.gedoplan.showcase.presentation;

import de.gedoplan.showcase.entity.Note;
import de.gedoplan.showcase.persistence.NoteRepository;
import de.gedoplan.showcase.service.TenantSelectorService;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Presentationklasse für Demo-GUI.
 *
 * @author dw
 */
@Model
public class NotePresenter {
  /*
   * 1. Teil: Bereitstellung eines Texttes, der auf die Entity Note mit der u. a. abgebildet wird. Der Text wird pro Request neu
   * gelesen und kann mittels store() gespeichert werden.
   */
  private static final String ID = "DEMO";
  private String text;

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Inject
  NoteRepository noteRepository;

  @PostConstruct
  void postConstruct() {
    Note note = this.noteRepository.findById(ID);
    if (note != null) {
      this.text = note.getText();
    } else {
      this.text = "<new>";
    }
  }

  @Transactional
  public void store() {
    Note note = this.noteRepository.findById(ID);
    if (note != null) {
      note.setText(this.text);
    } else {
      this.noteRepository.persist(new Note(ID, this.text));
    }
  }

  /**
   * 2. Teil: Auswahl eines Tents.
   */
  @Inject
  TenantSelectorService tenantSelectorService;

  public String getCurrentTenant() {
    return this.tenantSelectorService.getCurrentTenant();
  }

  public void setCurrentTenant(String currentTenant) {
    this.tenantSelectorService.setCurrentTenant(currentTenant);
  }

  public List<String> getTenants() {
    return this.tenantSelectorService.getTenants();
  }

  public String refresh() {
    return FacesContext.getCurrentInstance().getViewRoot().getViewId() + "?faces-redirect=true";
  }
}
