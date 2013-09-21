package core.service;

import core.ProcessService;

public class ReflectionService {

  public ProcessService loadProcessService(Class<?> newFixture, String clazz) {

    if (ProcessService.class.isAssignableFrom(newFixture)) {
      try {
        Object newInstance = newFixture.newInstance();
        return (ProcessService) newInstance;
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      return null;
    }
    return null;
  }
}
