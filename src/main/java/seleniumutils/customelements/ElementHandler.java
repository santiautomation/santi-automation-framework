package seleniumutils.customelements;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ClassUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class ElementHandler implements InvocationHandler {
	
	private final ElementLocator locator;
	private final Class<?> wrappingType;

	/*
	 * Generates a handler to retrieve the WebElement from a locator for a given
	 * WebElement interface descendant.
	 */
	public <T> ElementHandler(Class<T> interfaceType, ElementLocator locator) {
		this.locator = locator;
		if (!LogElement.class.isAssignableFrom(interfaceType)) {
			throw new RuntimeException("Interface not assignable to Element.");
		}

		this.wrappingType = ElementHandler.getWrapperClass(interfaceType);
	}

	@Override
	public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
		WebElement element = locator.findElement();

		if ("getWrappedElement".equals(method.getName())) {
			return element;
		}
		Constructor cons = wrappingType.getConstructor(WebElement.class);
		Object thing = cons.newInstance(element);
		try {
			return method.invoke(wrappingType.cast(thing), objects);
		} catch (InvocationTargetException e) {
			// Unwrap the underlying exception
			throw e.getCause();
		}
	}
	
	/**
	 * Gets the actual implementation of your interface 
	 * @param <T>
	 * @param iface
	 * @return
	 */
	public static <T> Class<?> getWrapperClass(Class<T> iface) {
        if (iface.isAnnotationPresent(ImplementedBy.class)) {
            ImplementedBy annotation = iface.getAnnotation(ImplementedBy.class);
            Class<?> clazz = annotation.value();
            if (LogElement.class.isAssignableFrom(clazz)) {
                return annotation.value();
            }
        }
        throw new UnsupportedOperationException("Apply @ImplementedBy interface to your Interface " + 
                iface.getCanonicalName() + " if you want to extend ");
    }
}
