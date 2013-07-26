package core.service;

import core.ProcessService;

public class ReflectionService {

	public ProcessService loadProcessService(String clazz) {
		Class<?> forName = null;
		try {
			forName = Class.forName(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(ProcessService.class.isAssignableFrom(forName)) {
			try {
				Object newInstance = forName.newInstance();
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
