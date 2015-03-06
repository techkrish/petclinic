/**
 * DISCLAIMER
 * 
 * The quality of the code is such that you should not copy any of it as best
 * practice how to build Vaadin applications.
 * 
 * @author jouni@vaadin.com
 * 
 */

package com.cloudyle.paasplus.petclinic.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Pet;
import com.cloudyle.paasplus.petclinic.ui.data.PetsContainer;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;


public class ClinicView extends VerticalLayout implements View
{


  private static final long serialVersionUID = 1L;

  Table t;

  Object editableId = null;

  PetsContainer data;

  List<Pet> pets;



  void createNewReportFromSelection()
  {
    ((PetClinicUI) getUI()).openReports(this.t);
  }



  @Override
  public void enter(final ViewChangeEvent event)
  {

    ((PetClinicUI) getUI()).getModel().reload();

    this.pets = ((PetClinicUI) getUI()).getModel().getPets();

    this.data = ((PetClinicUI) getUI()).getModel().getPetTable();

    setSizeFull();
    addStyleName("transactions");

    this.t = new Table()
    {


      @Override
      protected String formatPropertyValue(final Object rowId, final Object colId, final Property<?> property)
      {
        if (colId.equals("Date of Birth"))
        {
          final SimpleDateFormat df = new SimpleDateFormat();
          df.applyPattern("MM/dd/yyyy");
          return df.format(((Date) property.getValue()).getTime());
        }
        return super.formatPropertyValue(rowId, colId, property);
      }
    };
    this.t.setSizeFull();
    this.t.addStyleName("borderless");
    this.t.setSelectable(true);
    this.t.setColumnCollapsingAllowed(true);
    this.t.setColumnReorderingAllowed(true);
    this.data.removeAllContainerFilters();
    this.t.setContainerDataSource(this.data);
    sortTable();

    this.t.setVisibleColumns(new Object[] { "Name", "TypeCode", "Date of Birth", "Owner Name", "Owner Address",
        "Illness" });

    this.t.setFooterVisible(true);

    // Allow dragging items to the reports menu
    this.t.setDragMode(TableDragMode.ROW);
    this.t.setMultiSelect(true);

    this.t.addItemClickListener(new ItemClickListener()
    {


      @Override
      public void itemClick(final ItemClickEvent event)
      {
        if (event.getButton() == MouseButton.LEFT && event.isDoubleClick())
        {
          final Item item = event.getItem();
          if (item != null)
          {
            // TODO: Selection
            // final Pet pat = ClinicView.this.pets.get((int) item
            // .getItemProperty("Id").getValue());
            // ((PetClinicUI) UI.getCurrent()).openPatient(pat);

          }
        }

      }
    });

    final HorizontalLayout toolbar = new HorizontalLayout();
    toolbar.setWidth("100%");
    toolbar.setSpacing(true);
    toolbar.setMargin(true);
    toolbar.addStyleName("toolbar");
    addComponent(toolbar);

    final Label title = new Label("Waiting Room");
    title.addStyleName(ValoTheme.LABEL_H2);
    title.setSizeUndefined();
    toolbar.addComponent(title);
    toolbar.setComponentAlignment(title, Alignment.MIDDLE_LEFT);

    final TextField filter = new TextField();
    filter.addTextChangeListener(new TextChangeListener()
    {


      @Override
      public void textChange(final TextChangeEvent event)
      {
        ClinicView.this.data.removeAllContainerFilters();
        ClinicView.this.data.addContainerFilter(new Filter()
        {


          @Override
          public boolean appliesToProperty(final Object propertyId)
          {
            if (propertyId.equals("Name") || propertyId.equals("Owner Name"))
            {
              return true;
            }
            return false;
          }



          @Override
          public boolean passesFilter(final Object itemId, final Item item) throws UnsupportedOperationException
          {

            if (event.getText() == null || event.getText().equals(""))
            {
              return true;
            }

            return filterByProperty("Name", item, event.getText())
                || filterByProperty("Vorname", item, event.getText());

          }
        });
      }
    });

    filter.setInputPrompt("Filter");
    filter.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null)
    {


      @Override
      public void handleAction(final Object sender, final Object target)
      {
        filter.setValue("");
        ClinicView.this.data.removeAllContainerFilters();
      }
    });
    toolbar.addComponent(filter);
    toolbar.setExpandRatio(filter, 1);
    toolbar.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);

    final Button newReport = new Button("Create Report");
    newReport.addClickListener(new ClickListener()
    {


      @Override
      public void buttonClick(final ClickEvent event)
      {
        createNewReportFromSelection();
      }
    });
    newReport.setEnabled(false);
    newReport.addStyleName("small");
    toolbar.addComponent(newReport);
    toolbar.setComponentAlignment(newReport, Alignment.MIDDLE_LEFT);

    addComponent(this.t);
    setExpandRatio(this.t, 10);

    this.t.addActionHandler(new Handler()
    {


      private final Action report = new Action("Create Report");

      private final Action discard = new Action("Transfer");

      private final Action details = new Action("Details");



      @Override
      public Action[] getActions(final Object target, final Object sender)
      {
        return new Action[] { this.details, this.report, this.discard };
      }



      @Override
      public void handleAction(final Action action, final Object sender, final Object target)
      {
        if (action == this.report)
        {
          createNewReportFromSelection();
        }
        else if (action == this.discard)
        {
          Notification.show("Not implemented in this demo");
        }
        else if (action == this.details)
        {
          final Item item = ((Table) sender).getItem(target);
          if (item != null)
          {
            final Pet pet = (Pet) item.getItemProperty("pet").getValue();
            ((PetClinicUI) UI.getCurrent()).openPet(pet);
            final Window window = new Window("Details");
            final Table table = new Table();
            int rowNumber = 0;
            table.setPageLength(0);
            table.setWidth(50, Unit.PERCENTAGE);
            table.addStyleName("userViewTable");
            table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
            table.addContainerProperty("col1", String.class, "not set");
            table.addContainerProperty("col2", String.class, "not set");
            final Object obj[] = { "Name", pet.getName() };
            table.addItem(obj, ++rowNumber);
            if (pet.getBirthDate() != null)
            {
              final SimpleDateFormat df = new SimpleDateFormat();
              df.applyPattern("MM/dd/yyyy");
              final Object birthDate[] = { "BirthDate", df.format(pet.getBirthDate()) };
              table.addItem(birthDate, ++rowNumber);
            }
            window.setContent(table);
            window.center();
            window.setWidth("400px");
            window.setHeight("250px");
            window.setResizable(false);
            window.setClosable(true);
            window.setModal(true);
            UI.getCurrent().addWindow(window);
          }
        }
      }
    });

    this.t.addValueChangeListener(new ValueChangeListener()
    {


      @Override
      public void valueChange(final ValueChangeEvent event)
      {
        final Object val2 = ClinicView.this.t.getValue();
        if (ClinicView.this.t.getValue() instanceof Set)
        {
          final Set<Object> val = (Set<Object>) ClinicView.this.t.getValue();
          newReport.setEnabled(val.size() > 0);

          for (final Object id : val)
          {
            final Item item = ClinicView.this.t.getItem(id);
            // TODO: Selection Handling
            // if (item != null) {
            // final Pet pat = ClinicView.this.pets.get((int) item
            // .getItemProperty("Name").getValue());
            // ((PetClinicUI) UI.getCurrent()).getModel()
            // .setSelectedPet(pat);
            // }
          }
        }
        else
        {
        }
      }
    });
    this.t.setImmediate(true);

  }



  private boolean filterByProperty(final String prop, final Item item, final String text)
  {
    if (item == null || item.getItemProperty(prop) == null || item.getItemProperty(prop).getValue() == null)
    {
      return false;
    }
    final String val = item.getItemProperty(prop).getValue().toString().trim().toLowerCase();
    if (val.startsWith(text.toLowerCase().trim()))
    {
      return true;
    }
    // String[] parts = text.split(" ");
    // for (String part : parts) {
    // if (val.contains(part.toLowerCase()))
    // return true;
    //
    // }
    return false;
  }



  private void sortTable()
  {
    this.t.sort(new Object[] { "Name" }, new boolean[] { true });
  }

}
