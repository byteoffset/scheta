# scheta
Configurable library to create periodic and single jobs in Java.

### Exposed classes
* `TaskConfiguration` class represent configuration for both types of task: `SingleJob` and `PeriodicJob`. 
For `SingleJob` type task, it can be null, what cause immediate task lunch. `null` configuration for `PeriodicJob` cause `NullPointerException`. 

* `SingleJob` class is able to lunch one time task immediately or with delay. It's dependent of configuration. 
Configuration `time` property is in `HH:MM:SS` format. If property is set properly, task will be triggered at set hour, minute and second. 

* `PeriodicJob` class is able to lunch periodic task immediately or with delay. If `time` property is not set, task starts now. 
`unit` defines `period` time representation in `HOURS`, `MINUTES` and `SECONDS`. 
`period` property defines time between task lunches, remember that it is represent as `int`. 
