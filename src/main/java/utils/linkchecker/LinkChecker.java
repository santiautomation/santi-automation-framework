package utils.linkchecker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.html.HTML;

import org.apache.commons.validator.routines.UrlValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import seleniumutils.Utilities;

/**
 * Link Validator class, with navigation and recursion to go through all the URLs available from the
 * starting point passed in the constructor. 
 * If the failedLinks variable is empty after finishing then you should pass your test, otherwise print/report all the broken links.
 * 
 * 
 * @author Santiago Schumacher
 *
 */
public class LinkChecker {

	private final WebDriver driver;
	private List<String> linksPass = new ArrayList<>();
	private Multimap<String, String> linksFail = ArrayListMultimap.create();
	private Set<String> scannedLinks = new HashSet<>(); // Set to avoid duplicates.
	private String url;
	private UrlValidator urlValidator = new UrlValidator(); // From Apache Commons. More info at https://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/routines/UrlValidator.html
	private String host;
	
	/**
	 * 
	 * @param driver
	 * @param url - Starting point of the Link Checker
	 * @throws MalformedURLException
	 */
	public LinkChecker(final WebDriver driver, String url) throws MalformedURLException {
		this.driver = driver;
		this.url = url;
		this.host = extractHost(url);
	}
	
	/**
	 * Returns the host (String between http/https/www and .com/.net/io etc)
	 * Will throw an Exception if the URL is not valid.43333
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	private String extractHost(String url) throws MalformedURLException {
		URL u = new URL(url);
		return u.getHost();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public List<String> getLinksPass() {
		return linksPass;
	}

	public void setLinksPass(List<String> linksPass) {
		this.linksPass = linksPass;
	}

	public Multimap<String, String> getLinksFail() {
		return linksFail;
	}

	public void setLinksFail(Multimap<String, String> linksFail) {
		this.linksFail = linksFail;
	}

	public Set<String> getScannedLinks() {
		return scannedLinks;
	}

	public void setScannedLinks(Set<String> scannedLinks) {
		this.scannedLinks = scannedLinks;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Entry point of the Link Checker.
	 * 
	 * @throws MalformedURLException
	 */
	public void linkCheckerWithNavigation() throws MalformedURLException {
		this.getScannedLinks().add(driver.getCurrentUrl());
		System.out.println("Current URL checking: " + driver.getCurrentUrl());
		List<String> allHrefAtr = getLinks();
		
		for (String urlToScan: allHrefAtr) {
	
			boolean status = Utilities.getUrlResponse(urlToScan); // Actual check if the given URL String can be accessed.
			
			if (!status) {
				getLinksFail().put(driver.getCurrentUrl(), urlToScan); // Store data in format fromUrl - failedLink
			} else {
				getLinksPass().add(urlToScan);
			}
		}

		for (String newUrlToScan : getLinksPass()) { // For every valid link inside currently scanned URL
			List<String> temp = new ArrayList<>(getScannedLinks());
			if (!temp.contains(newUrlToScan) && newUrlToScan.contains(this.getHost())) { // Checking if the URL hasn't already been processed
				driver.get(newUrlToScan);
				linkCheckerWithNavigation(); // Recursion. Start validating links again from a different URL of the same host.
			}
		}
	}

	/**
	 * Returns a List of links inside current website. 
	 */
	private List<String> getLinks() {
		List<WebElement> allLinks = driver.findElements(By.tagName(HTML.Tag.A.toString()));
		List<String> stringLinks = new ArrayList<>();
		
		for (WebElement link : allLinks) {
			
			String theLink = link.getAttribute(HTML.Attribute.HREF.toString());
			// Skip repeated URL, URLs already checked, or if its equals to the current URL.
			if (isLinkValid(theLink) && !this.getLinksPass().contains(theLink)) {
				stringLinks.add(theLink);
			}
		}
				
		return stringLinks;
	}

	private boolean isLinkValid(String theLink) {
		return urlValidator.isValid(theLink) && 
				!scannedLinks.contains(theLink)	&& // Make sure URL wasn't already scanned
				!driver.getCurrentUrl().equalsIgnoreCase(theLink) && //This and below links remains on the same URL so we don't navigate to them
				!theLink.contains("collapse") && 
				!theLink.contains("#");
	}

	
}
