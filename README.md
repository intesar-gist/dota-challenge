bayes-dota
==========

This is the [task](TASK.md).

All the required tasks in [task](TASK.md) are COMPLETED.

There are few implementation related things that I also wanted to do but ignored due to time constraints.

Things ignored due to time constraints (2 hours):
==============================================
* Did not fully implement DAO and DTO pattern (interfaces, layers etc) because of time constraints
* Not using separate service layers for every domain, would be better
* To keep things simple and fast, for time related data I have used LONG in all the model and domain classes
* Have used HashTables as a cache for quick persistent object retrieval, could have done in better way but.... (2 hours)
* Implemented very basic Exception handling 
  * ignored optimal/best way as it requires some time 
  * Could have made separate exceptions classes for  each layer (for instance, persistence layer, service layer and eventually a proper error message to the end user)
* Unfortunately could not implement Unit Tests.
* Efficiency of overall implementation could be improved 


HOW TO USE:
==========

* No external libraries were used
* This implementation uses in-memory H2 database and tables will be created once the application is started.
* To avoid any confusions the tables will be empty initially, therefore, please ingest the data file(s) first via `POST /api/match`
* Every ingested data file is considered as one complete match log and will be given a `unique match id.`
  * For instance, user ingest a file named File1.txt, once data is saved, the api will return `ID = 1`
  * Second ingested file will be given `ID = 2` and so on
  * In current implementation, same file can be ingested multiple times, however, data for each call will be considered as `a separate match`