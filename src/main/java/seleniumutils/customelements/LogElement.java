package seleniumutils.customelements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;

/**
 * 
 * @author Santiago Schumacher
 *
 */
@ImplementedBy(Element.class)
public interface LogElement extends WrapsElement, WebElement, Locatable  {
}
