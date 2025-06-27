- This is an automation framework in JAVA 
  + Selenium 4
  + BrowserStack integration
  + Local
  + Thread
  + Logs (Log4j)
  + TestNG 
  + MarkupHelper
  + ExtentReport 

- Change the config setting in config.json
- Change the environment setting in environment.json
- Adding more devices in deviceOnBS.json
- Adding deviceOrientation to JSON file due to not using rotate (Optional due to there is Rotate function)
  
![image](https://github.com/user-attachments/assets/7c0f1026-45a5-4154-8f32-6a9e8e38aa87)


- Report will be exported to a Reports folder after each tests
  + Capture screenshot when having a failed cases
  + Markup helper within custom markup
  + With ThreadLocal does support for parallel tests
![msedge_ElSEB72DsE](https://github.com/labaxibum/Java-selenium-fw/assets/47781346/3eb94ee8-bb8c-42c4-8777-c9aa0803ef62)

- Logs wild be exported to logs folder
  + After a day, log file will be compressed to a zip file
![image](https://github.com/labaxibum/Java-selenium-fw/assets/47781346/22546f6e-7f6c-4b0b-803c-18d867c87886)
![image](https://github.com/labaxibum/Java-selenium-fw/assets/47781346/2d5cc7c5-c75f-4998-bfe1-f7d0a7699032)

- Need to improve
- [x] Add API to the framework
- [ ] Integrate with Appium & Selenium 4
- [ ] Add custom exception
- [ ] Coding standard in JAVA and Clean code
