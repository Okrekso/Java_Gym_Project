# Java Gym Project
# About
This project represent backend part of sample gym. It contains client information, subscriptions and card information and many other part of gym infrostructure.
# Project Navigation
This project have some template structure with Vistors, Gyms, Gym Sections etc. But it could be modified easily to working copy of existing gym.<br/>

### Base structure of the project:
- **Database**: base class which represent basic connections and operations with database. Adaptive to any driver you give.
Database is abstract class, so you need to extend it in your project and override abstract methods
- **GymDB**: exact database for Gym. Handle all actions connected with Database.
- **DBEntity**: Single Entity from Database. Abstract as well and have many options to create new Entity _(For example Visitor or Gym)_.
- **DBValue**: Single value of Entity. Creates single value such like a VARCHAR or INT or any other. 
Contain many methods to add foreign keys, primary keys etc.
- **IDBEntityFactory**: factory for creating Entities. Every entity should have own factory because database use it for building and adding entities to DB.
### Patterns
Except MVC pattern I used Factory pattern. It was very useful for my project structure and give as much flexability
for project as possible.
# Installation
1. First of all you need to install server local server machine. In project development I used **Tomcat 9.0.33**.
1. **Intellij Idea 2019+** version must be installed.
1. **H2DB Driver must be installed!**
1. Open project in Intellij Idea.
1. Sepcify tomcat as a startup option. 
1. Run project.
1. You should see root page
![root page](https://i.ibb.co/tMzgWm8/chrome-KJRpp-D6tv-Z.png)
# Authors
_Oskerko Semen_