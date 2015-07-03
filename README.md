petclinic
=========

PetClinic Tutorial for OSGi

See http://devcenter.cloudyle.com/display/PAAS/Pet+Clinic+Tutorial for detailed description

## Introduction

This tutorial shows the basic usage of the PaaS+ platform and the Enterprise API.

This tutorial is based on the well known pet clinic tutorial from spring framework and shows how a similar application would be created with OSGi and the help of the PaaS+ Enterprise API.
Pet Clinic Overview

The application requirement is for an information system that is accessible through a web browser. The users of the application are employees of the clinic who in the course of their work need to view and manage information regarding the veterinarians, the clients, and their pets. The sample application supports the following:
Use Cases

    Secure Login
    View a list of pets in clinic
    View a list of veterinarians and their specialties
    View information pertaining to a pet owner
    Update the information pertaining to a pet owner
    Add a new pet owner to the system
    View information pertaining to a pet
    Update the information pertaining to a pet
    Add a new pet to the system
    View information pertaining to a pet's visitation history
    Add information pertaining to a visit to the pet's visitation history

In the first version of the tutorial only the login and pet list is implemented in the UI.

## Business Rules

    An owner may not have multiple pets with the same case-insensitive name.

## Database

The data is stored in a MongoDB and consists of the following collections:

    owner : Person data of pet owners
    pet : Information on a pet
    vet : Person data of veterinarians
    visit : Information regarding a visit in pet clinic

The MongoDB collections are mapped as JPA entities using the EclipseLink NoSQL extensions included with the PaaS+ Enterprise API.

In addition there are two catalogs managed by PaaS+ Catalog Service :

    Specialities : for veterinarian specialities
    PetTypes : for different pet types

## Project Structure

The petclinic sources are separated in several modules. The build and dependencies are managed with Maven.

    persistence : The NoSQL persistence unit implementing the entities. The persistence is based on the PaaS+ Persistence Service
    api : The interface of the OSGi ClinicService for providing the business logic
    provider : Implementation of the ClinicService, uses PersistenceService, CatalogService and creates some sample data
    gui : Uses the PaaS+ Basic GUI Framework for providing the UI
    feature : Creates a Apache Karaf feature for deployment
    deployment : Creates a Karaf Archive (kar) file that will automatically be deployed to your app on PaaS+

## Prerequisites

You should have installed on your machine a git version control client and an Eclipse or Netbeans IDE.

The following description assumes an Eclipse IDE.

Also you should have ssh installed, on Windows you should install a ssh client like Putty
Preparation

First you should register for an account at Cloudyle PaaS+.

When you have an account you should open the PaaS+ Broker to create a new karaf application.

## Get the Sources

Get the petclinic sources from Github using:
git clone https://github.com/Cloudyle/petclinic.git

Add your PaaS+ application repository as new remote to the petclinic repository. Check the application details page of your application in the PaaS+ Broker for full git-url.
cd petclinic
git remote add paasplus ssh://<instance-id>@<appname>-<domainame>.paasplus.com/~/git/<appname>.git

Pull the remote repository:
git pull paasplus master
First build

Now you are ready to push the petclinic sources to the Cloud which will automatically trigger a build.
git push paasplus master

You should then see output like this:
Counting objects: 183, done.
Delta compression using up to 4 threads.
Compressing objects: 100% (82/82), done.
Writing objects: 100% (176/176), 123.69 KiB | 0 bytes/s, done.
Total 176 (delta 24), reused 173 (delta 23)
remote: Not stopping cartridge karaf because hot deploy is enabled
remote: Not stopping cartridge mongodb because hot deploy is enabled
remote: something else
remote: Building git ref 'master', commit 24a8cef
remote: something else
remote: something else
remote: Using Maven mirror /var/lib/openshift/53bc0acbf6b994135d000244/app-root/runtime/repo//.openshift/config/settings.paasplus.xml
remote: Apache Maven 3.0.3 (r1075437; 2011-06-20 13:22:37-0400)
remote: Maven home: /etc/alternatives/maven-3.0
remote: Java version: 1.7.0_55, vendor: Oracle Corporation
remote: Java home: /usr/lib/jvm/java-1.7.0-openjdk-1.7.0.55.x86_64/jre
remote: Default locale: en_US, platform encoding: ANSI_X3.4-1968
remote: OS name: "linux", version: "2.6.32-431.17.1.el6.x86_64", arch: "amd64", family: "unix"

After a successful build, the application is deployed directly on your PaaS+ application. This may take some moments. You can

When the deployment is ready, you can access the petclinic ui via http in your browser:
http://<appname>-<domainame>.paasplus.com/petclinic

The login screen will open up and you can login.

The username is Dover with password dover
Icon

The configuration for the authentication is found in the shiro.ini in the gui project (in folder resources/WEB-INF/classes).
You may change this to modify user access. You can also add additional Shiro realms to access an LDAP or other user store.

 

 

## Check the code

The pet clinic code contains a lot of examples how the  PaaS+ Enterprise API can be used.

The best to do this is to import the project into your IDE.

For Eclipse Open File/Import and select Maven/Existing Maven Projects.

Select the folder where you checked out the sources. You should get the following screen:

Clicking Finish will start the import.

Then there should be six new projects imported in your workspace.

See the following chapters for finding out what you can check in each project.

## Root Project

The project com.cloudyle.paasplus.samples.petclinic is the root that contains all other projects as modules
Nexus Access

In the pom.xml you can see the needed configuration to access the PaaS+ Nexus repository
<repositories>
   <!--  This is our Maven repository server.
         It should always be on top in the list of repositories.
         External respositories will be accessed only if an API hasn't been found in our repository.
 
         Any external repository used by us should be proxied by our internal server.     -->
   <repository>
     <id>paasplus</id>
     <name>Public Paas+ Repositories</name>
     <url>https://nexus.paasplus.com/nexus/content/groups/public/</url>
     <releases>
       <enabled>true</enabled>
       <updatePolicy>always</updatePolicy>
     </releases>
     <snapshots>
       <enabled>true</enabled>
       <updatePolicy>interval:10080</updatePolicy>
     </snapshots>
   </repository>
 </repositories>

## API

The API project contains an OSGi service interface for ClinicService that handles the business logic for the Petclinic.

For more on OSGi Services please see the OSGi Core Specification.

## Persistence

The persistence bundle provides a JPA persistence unit as OSGi Bundle.
NoSQL JPA Persistence

It uses a MongoDB NoSQL persistence backend.

In the persistence.xml you should define:
<property name="eclipselink.logging.logger" value = "com.cloudyle.paasplus.core.eclipselink.EclipseLinkOSGiSessionLogger"/>
<property name="eclipselink.target-server" value="com.cloudyle.paasplus.core.eclipselink.EclipseLinkOSGiServerPlatform"/>

This tells the Aries JPA provider in the OSGi environment to use the Eclipselink implementation that PaaS+ provides. To tell EclipseLink to use MongoDB set transaction-type="RESOURCE_LOCAL" and add:
<property name="eclipselink.target-database" value="org.eclipse.persistence.nosql.adapters.mongo.MongoPlatform"/>
<property name="eclipselink.nosql.connection-spec" value="org.eclipse.persistence.nosql.adapters.mongo.MongoConnectionSpec"/>

The aries.jpa.persistence.petclinic.nosql.cfg file shows how to include the default mongodb connection parameters using the ConfigAdmin extension for Aries JPA provided by PaaS+ .
include=org.mongodb.connection
Persistence Service

In the blueprint.xml a PaaS+ persistence service is created for the JPA persistence unit. This allows easy use of the JPA pu in your service implementations.
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
 
<blueprint xmlns="http://www.osgi.org/xmlns/blueprint/v1.0.0"
    xmlns:jpa="http://aries.apache.org/xmlns/jpa/v1.0.0"
    xmlns:cm="http://aries.apache.org/blueprint/xmlns/blueprint-cm/v1.1.0"
    xmlns:tx="http://aries.apache.org/xmlns/transactions/v1.0.0"
    default-availability="optional">
 
    <bean id="persistenceUnit"
        class="com.cloudyle.paasplus.services.persistence.impl.ApplicationManagedPersistenceUnit" init-method="init">
        <jpa:unit property="entityManagerFactory" unitname="persistence-petclinic-nosql" />
        <property name="persistenceUnitName" value="persistence-petclinic-nosql" />   
    </bean>
 
    <!-- Paasplus Persistence Service -->
    <bean id="persistenceService"
        class="com.cloudyle.paasplus.services.persistence.impl.PersistenceServiceImpl" >
       <property name="persistenceUnit" ref="persistenceUnit" />
        <tx:transaction method="*" value="Required" />
    </bean>
 
    <!-- Service definition -->
    <!-- Publish the persistenceService as a Service -->
    <service ref="persistenceService">
        <interfaces>
            <value>com.cloudyle.paasplus.services.persistence.IPersistenceService</value>
        </interfaces>
        <!-- depends-on="indexService"> -->
        <service-properties>
            <entry key="persistence-unit" value="persistence-petclinic-nosql" />
        </service-properties>
    </service>
 
</blueprint>

You can check the entity sources for examples how to map JPA entities for NoSQL DB.

## Provider

The provider bundle implements the ClinicService. It shows you how to use the persistence , catalog and report service.

Check the blueprint.xml how to get these services injected and how to access configuration settings via ConfigurationAdmin Service
<blueprint xmlns="http://www.osgi.org/xmlns/blueprint/v1.0.0"
    xmlns:cm="http://aries.apache.org/blueprint/xmlns/blueprint-cm/v1.1.0"
    xmlns:ext="http://aries.apache.org/blueprint/xmlns/blueprint-ext/v1.2.0"
    default-availability="optional">
     
    <!-- Configuration -->
      <cm:property-placeholder id="property-placeholder" persistent-id="com.cloudyle.paasplus.petclinic" update-strategy="reload">
        <cm:default-properties>           
            <cm:property name="dataDir" value="data"/>
            <cm:property name="createSampleData" value="true"/>
        </cm:default-properties>
    </cm:property-placeholder>
     
    <!--  Preference BackingStore Service  -->
    <bean id="clinicServiceBean" class="com.cloudyle.paasplus.petclinic.impl.ClinicServiceImpl" >
        <property name="persistenceService" ref="persistenceService"/>       
        <property name="catalogService" ref="catalogService"/> 
        <property name="reportHelper" ref="reportHelper"/> 
    </bean>
     
    <bean id="reportHelper" class="com.cloudyle.paasplus.petclinic.impl.ReportHelper" >      
        <property name="dataDir" value="${dataDir}"/>
        <property name="reportService" ref="reportService"/> 
    </bean>
 
   <bean id="sampleData" class="com.cloudyle.paasplus.petclinic.impl.SampleData" init-method="createSampleData" >      
        <property name="createSampleData" value="${createSampleData}"/>
        <property name="persistenceService" ref="persistenceService"/>       
        <property name="catalogService" ref="catalogService"/> 
    </bean>
     
    <reference id="persistenceService"
        interface="com.cloudyle.paasplus.services.persistence.IPersistenceService"
        availability="mandatory" filter="(persistence-unit=persistence-petclinic-nosql)">
    </reference>
     
     <reference id="reportService"
        interface="com.cloudyle.paasplus.services.report.IReportService"
        availability="optional" >
    </reference>
     
    <reference id="catalogService"
        interface="com.cloudyle.paasplus.services.catalog.ICatalogService"
        availability="mandatory" >
    </reference>
     
    <service interface="com.cloudyle.paasplus.petclinic.ClinicService" ref="clinicServiceBean"/>
 
 
</blueprint>

For more on blueprint see the blueprint tutorial or this overview of blueprint.

## GUI

The petclinic gui project is an example how to implement your own PaaS+ Web Application by extending the basic UI framework.

 

getModules()

By implementing getModules(), different modules containing views can be added to the application. e.g. clinicModule is added in the demo which contains a view namely ClinicView.
@Override
 protected List<Module> getModules()
 {
   if (this.modules == null)
   {
     this.modules = new ArrayList<>();
     this.clinicModule = new ClinicModule();
     this.modules.add(this.clinicModule);
     setDefaultView("overview");
   }
   return this.modules;
 }

 
getSettingsMenu()

By implementing getSettingsMenu(), MenuBar can be customized to show the profile name, photo or logout button, . e.g. in the demo, first name and second name are showed with an arrow. Clicking on the arrow shows the possibility of logout.
@Override
  protected MenuBar getSettingsMenu()
  {
    if (PaasplusPetClinicSession.getCurrent().isAuthenticated())
    {
      final List<SettingsItem> items = new ArrayList<>();
      final MenuBar.Command logoutCmd = new MenuBar.Command()
      {
        @Override
        public void menuSelected(final MenuBar.MenuItem selectedItem)
        {
          logout();
        }
      };
      items.add(new SettingsItem("logout", logoutCmd));
      final Vet vet = (Vet) PaasplusPetClinicSession.getCurrent().getAttribute("USER");
      return DefaultSettingsMenu.createSettingsMenu(vet.getFirstName() + " " + vet.getLastName(), null, items);
    }
    return null;
  }

 
Further navigation

Right-Click on the row shows a menu for further navigating in the application, e.g. selecting details from the menu shows a window for the selected item.

## Feature and Deployment

The feature and deployment projects show how to deploy your code using an Apache Karaf feature and kar archivew. It uses feature dependency to install the required PaaS+ bundles automatically.

The kar archive is deployed to karaf/deploy folder to be automatically deployed in the PaaS+ application.
