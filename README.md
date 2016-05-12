## Technical test:

```
Specification
====================

Scenario:
- Open booking.com website
- Search for hotels in New York City for period Sept 3, 2016 - Sept 7, 2016
- Verify that all hotels on 1 page of results are located in NYC
- Add couple tests

```

### Requirements
* [Gauge framework](http://getgauge.io/) with plugins "java" and "html-report"
* Maven with Selenium libs
* JDK 6+

### Test plan
Step implementations available in /specs/booking.spec
```
Check Booking.com simple search
-------------------------------------------------------------
* Navigate to "http://www.booking.com"
* Choose EN-US language
* Check EN-US language
* Choose USD currency
* The currency title should show "US$" as result
* Set destination to "New York City"
* Set Check-In date to " 3" of "September 2016"
* Set Check-Out date to " 7" of "September 2016"
* Choose Traveling for Work
* Set Rooms to "1"
* Set Adults to "1"
* Do search
* Check search for "New York City"
```

### WebDriver settings
Please use correct path for your WebDriver in /env/default/default.properties

### Run
/specs/booking.spec

### Result
Html result available in /reports/html-reports/index.html

_Tested only with Chrome Webdriver_

For IntelliJ Idea settings I recommend
[10 minutes tutorial to Gauge](https://github.com/getgauge/gauge/wiki/Getting-Started-in-3-Steps)