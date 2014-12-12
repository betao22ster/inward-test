package br.com.inward.test.scanner;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.sf.corn.cps.CPResourceFilter;
import net.sf.corn.cps.RootedURL;

/**
 * 
 * <p>
 * Recriando a classe do projeto CPScanner com algumas adaptações
 * </p>
 * 
 * @since 12/12/2014
 * @author <a href="mailto:marcelosv@softplan.com.br">Marcelo de Souza
 *         Vieira</a>
 * @changelog
 */
public class InWardCPScanner {

	/**
	 * Finds list of classpath roots
	 * 
	 * @return list of class path root url's
	 */
	public static Set<URL> findRoots() {
		Set<URL> urls = new HashSet<URL>();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		if (loader instanceof URLClassLoader) {
			@SuppressWarnings("resource")
			URLClassLoader urlLoader = (URLClassLoader) loader;
//			urls.addAll(Arrays.asList(urlLoader.getURLs()));
			
			if( urlLoader.getURLs() != null ){
				for (URL url : urlLoader.getURLs()) {
					if( url.getPath().contains("WEB-INF/classes/") ){
						urls.add(url);
					}
				}
			}
			
		} else {
			Enumeration<URL> urlEnum;
			try {
				urlEnum = loader.getResources("");
				while (urlEnum.hasMoreElements()) {
					urls.add(urlEnum.nextElement());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return urls;
	}

	private static Set<URL> findRootsByLoader(ClassLoader loader) {
		Set<URL> urls = new HashSet<URL>();

		if (loader instanceof URLClassLoader) {
			URLClassLoader urlLoader = (URLClassLoader) loader;
			urls.addAll(Arrays.asList(urlLoader.getURLs()));
		} else {
			Enumeration<URL> urlEnum;
			try {
				urlEnum = loader.getResources("");
				while (urlEnum.hasMoreElements()) {
					URL url = urlEnum.nextElement();
					if (url.getProtocol().startsWith("bundleresource")) {
						continue;
					}
					urls.add(url);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return urls;
	}

	/**
	 * Scans resources with in the classpath and applies the given list of
	 * filters
	 * 
	 * @param filters
	 *            that need to be applied
	 * @return list of found resources
	 */
	public static List<URL> scanResources(CPResourceFilter... filters) {
		List<URL> resources = new ArrayList<URL>();
		Set<URL> roots = findRoots();
		filterResources(roots, filters);
		Set<RootedURL> allResources = new HashSet<RootedURL>();
		for (URL rootURL : roots) {
			List<RootedURL> rurls = scan(rootURL, false, filters);
			for (RootedURL rootedURL : rurls) {
				if (!allResources.contains(rootedURL))
					allResources.add(rootedURL);
			}
		}
		for (RootedURL rURL : allResources) {
			resources.add(rURL.getResourceURL());
		}
		return resources;
	}

	/**
	 * Scans classes with in the classpath and applies the given list of filters
	 * 
	 * @param filters
	 *            that need to be applied
	 * @return list of classes that found
	 */
	public static List<Class<?>> scanClasses(CPResourceFilter... filters) {
		List<Class<?>> classList = new ArrayList<Class<?>>();

		Set<URL> roots = findRoots();
		filterResources(roots, filters);
		if (roots.size() == 0)
			return classList;
		Set<RootedURL> allResources = new HashSet<RootedURL>();
		ClassLoader contextLoader = Thread.currentThread()
				.getContextClassLoader();
		ClassLoader appLoader = InWardCPScanner.class.getClassLoader();
		for (URL rootURL : roots) {
			List<RootedURL> resources = scan(rootURL, true, filters);
			for (RootedURL rootedURL : resources) {
				if (!allResources.contains(rootedURL))
					allResources.add(rootedURL);
			}

		}

		for (RootedURL rurl : allResources) {
			try {
				Class<?> clazz = loadClassByLoaders(rurl.getClassName(),
						contextLoader, appLoader);
				if (!clazz.isSynthetic()
						&& Modifier.isPublic(clazz.getModifiers())) {
					clazz.getName();
					clazz.getCanonicalName();
					if (acceptable(clazz, filters)) {
						classList.add(clazz);
					}
				}
			} catch (Exception e) {
				// Runtime dependencies not complete to load this class,
				// just skip it
			} catch (Error e) {
				// Runtime dependencies not complete to load this class,
				// just skip it
			}
		}
		return classList;
	}

	private static Class<?> loadClassByLoaders(String name,
			ClassLoader... loaders) throws ClassNotFoundException {
		ClassNotFoundException lastException = null;
		Class<?> clazz = null;
		for (ClassLoader loader : loaders) {
			try {
				lastException = null;
				clazz = loader.loadClass(name);
				break;
			} catch (ClassNotFoundException e) {
				lastException = e;
			}
		}
		if (lastException != null)
			throw lastException;
		return clazz;
	}

	private static void filterResources(Collection<?> resources,
			CPResourceFilter... filters) {
		Set<Object> removed = new HashSet<Object>();
		for (Object resource : resources) {
			if (!acceptable(resource, filters))
				removed.add(resource);
		}
		for (Object object : removed) {
			resources.remove(object);
		}
	}

	private static boolean acceptable(Object resource,
			CPResourceFilter... filters) {
		if (resource == null)
			return false;
		if (filters == null)
			return true;
		for (CPResourceFilter filter : filters) {
			if (filter.filterable(resource)) {
				if (!filter.accept(resource))
					return false;
			}
		}
		return true;
	}

	private static boolean isArchive(File f) {
		return f != null
				&& f.isFile()
				&& (f.getName().toLowerCase().endsWith("jar") || f.getName()
						.toLowerCase().endsWith("zip"));
	}

	private static boolean isClassUrl(URL url) {
		return url.toExternalForm().indexOf(".class") > 0;
	}

	private static URL convertToJarUrl(URL url) {
		if (url.toString().startsWith("jar"))
			return url;
		URL jarUrl;
		try {
			jarUrl = new URL("jar:" + url + "!/");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jarUrl = url;
		}
		return jarUrl;
	}

	private static List<RootedURL> scan(URL rootURL, boolean scan4Classes,
			CPResourceFilter... filters) {
		return scan(rootURL, rootURL, scan4Classes, filters);
	}

	private static List<RootedURL> scan(URL rootURL, URL url,
			boolean scan4Classes, CPResourceFilter... filters) {
		List<RootedURL> resources = new ArrayList<RootedURL>();
		File file = null;

		if (url.getFile() != null && url.getFile().length() > 0) {
			try {
				file = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// We don't expect to trapped here
				e.printStackTrace();
			}
		}

		if (file != null) {
			try {
				if (file.isDirectory()) {
					// package! scan for childs
					for (File child : file.listFiles()) {
						resources.addAll(scan(rootURL, child.toURI().toURL(),
								scan4Classes, filters));
					}
				} else if (isArchive(file)) {// archive
					URL jarUrl = convertToJarUrl(file.toURI().toURL());
					if (acceptable(jarUrl, filters)) {
						JarURLConnection connection = (JarURLConnection) jarUrl
								.openConnection();
						JarFile jarFile = connection.getJarFile();
						if (acceptable(jarFile, filters)) {
							Enumeration<JarEntry> entries = jarFile.entries();
							while (entries.hasMoreElements()) {
								JarEntry entry = entries.nextElement();
								if (entry.isDirectory()) {
									continue;
								} else {
									URL entryUrl = new URL(jarUrl
											+ entry.toString());
									RootedURL rurl = new RootedURL(jarUrl,
											entryUrl);
									if (((scan4Classes && isClassUrl(entryUrl)) || (!scan4Classes && !isClassUrl(entryUrl)))
											&& acceptable(rurl, filters)) {
										resources.add(rurl);
									}
								}
							}
						}
					}
				} else {
					// Resource under a package
					RootedURL rurl = new RootedURL(rootURL, file.toURI()
							.toURL());
					if (((scan4Classes && isClassUrl(file.toURI().toURL())) || (!scan4Classes && !isClassUrl(file
							.toURI().toURL()))) && acceptable(rurl, filters)) {
						resources.add(rurl);
					}
				}
			} catch (Exception e) {
				// TODO Handle this
				// We don't expect to trapped here
				e.printStackTrace();
			}
		}
		return resources;
	}

}
