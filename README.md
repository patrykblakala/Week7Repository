# Week 7 - Web App development

## Overview
We have been tasked to explore the concepts of Web MVC, Thymeleaf, applying security to web applications and HTML , CSS and Bootstrap.
This task involves the creation of responsive and dynamic web pages in extension to the Week 6 project.
## Specific Requirements
We need to 
- Create a web application to provide access to the JPA entities created for the employees database in week 5
- Provide basic CRUD access for all relevant tables via the Web site (pages to find specific records, update, delete and create options, tables of items from the database)
- Provide access to the additional analytical methods as well
- Implement appropriate error handling and reporting, including logging
- Implement BASIC Web authentication and authorisation rules for the web site
  - Basic user (read-only access)
  - Update user (CRU access)
  - Admin user (full CRUD)
- Include testing using manual testing and Spring Boot Test as appropriate
- Provide a mechanism to allow a user to create a new employee, specifying all their details including the department they'll be in (from a drop-down menu), their title (from a drop-down with the option to add new) and salary (with a suitable value-entering mechanism), storing this information temporarily in the session, then allow the user to review what they've entered and submit all the updates in a single final *checkout* step

## User Stories

## Sprint 1

On the first day Patryk created a new repository for this extension of week 6 and mirrored the week 6 repo into this new repo.
```
In up to date week 6 local repo -
git push --mirror <gitlink> 
```

We started creating a product backlog and sprint backlog with tasks derived from the given requirements.

### Plan

We worked through one controller, DepartmentController, together so we knew what we were doing. Each controller has the CRUD methods with appropriate HTML files to display the information.
We split the tasks into development and styling (frontend) groups. The developement team on the controller and testing. The frontend team worked on styling and README. We all worked on the presentation.

| Patryk                       | Hanibal                         | Abdullah         | Liam            | Cameron            | Omari                                | Craig            |
|------------------------------|---------------------------------|------------------|-----------------|--------------------|--------------------------------------|------------------|
| Git manager, User Controller | Scrum Master, Bootstrap styling | Boostrap styling | TitleController | EmployeeController | DepartmentManagerController, logging | SalaryController |

If anyone had issues with code then they would share their screen, and we would troubleshoot together.

### Review
Most of the CRUD methods were completed, implementation of advanced methods are yet to be started.
It is harder to implement these methods than we expected.

### Retrospective

Workflow is good, progress should continue at a steady pace.

## Sprint 2

After the sprint 1 finished, we worked on the remaining tasks and worked on the presentation, security, testing...

### Plan

The plan for this sprint was:
- Finish implementing outstanding crud methods and their dynamic web pages
- Add the appropriate boostrap styling
- Add exception handling and logging
- Start JUnit testing our methods.
- Add security with user levels to API endpoints
- Complete the presentation as a group


| Patryk                              | Hanibal                         | Abdullah          | Liam            | Cameron  | Omari                                | Craig            |
|-------------------------------------|---------------------------------|-------------------|-----------------|----------|--------------------------------------|------------------|
| Git manager, Employee creation form | Scrum Master, Bootstrap styling | Bootstrap styling | TitleController | Security | DepartmentManagerController, logging | SalaryController |


### Review



### Retrospective





# Test Documentation

