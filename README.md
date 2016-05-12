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
* Gauge framework http://getgauge.io/ with plugins "java" and "html-report"
* Maven
* JDK 6+

### Test plan
Step implementations available in /specs/booking.spec

### WebDriver settings
Please use correct path for your WebDriver in /env/default/default.properties

### Run
/specs/booking.spec

### Result
Html result available in /reports/html-reports/index.html

_Tested only with Chrome Webdriver_