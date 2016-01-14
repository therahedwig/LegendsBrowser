package legends.xml.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import legends.xml.annotation.XmlSubtype;
import legends.xml.annotation.XmlSubtypes;

public class AnnotationContentHandler extends StackContentHandler {
	private Object object;

	AnnotationConfig config, baseConfig;

	boolean subtypes = false;
	String subtypeElement;
	Map<String, AnnotationConfig> subtypeConfigs = new HashMap<>();

	String skipElement = null;
	int skipdepth = 0;

	String subtype;
	boolean unknownSubtype = false;
	private Set<String> unknownSubtypes = new HashSet<>();
	private Set<String> unknownElements = new HashSet<>();
	private List<CachedElement> cache = new ArrayList<>();

	public AnnotationContentHandler(Class<?> objectClass)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this("", objectClass);
	}

	public AnnotationContentHandler(String name, Class<?> objectClass)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(name);
		this.object = objectClass.newInstance();

		baseConfig = config = new AnnotationConfig(objectClass, this::getObject);

		analyzeSubtypes(objectClass);
	}

	private void analyzeSubtypes(Class<?> objectClass)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		XmlSubtypes subs = objectClass.getAnnotation(XmlSubtypes.class);
		if (subs == null)
			return;

		subtypes = true;
		subtypeElement = subs.value();

		for (Field field : config.getObjectClass().getDeclaredFields())
			field.setAccessible(true);

		for (Class<?> subClass : new Reflections("legends").getSubTypesOf(objectClass)) {
			XmlSubtype sub = subClass.getAnnotation(XmlSubtype.class);
			if (sub == null)
				continue;

			subtypeConfigs.put(sub.value(), new AnnotationConfig(subClass, this::getObject));
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if (skipElement != null) {
			skipdepth++;
			return;
		}

		StackContentHandler contentHandler = config.getHandlers().get(localName);
		if (contentHandler != null) {
			pushContentHandler(contentHandler);
			return;
		}

		StringConsumer consumer = config.getValues().get(localName);
		if (consumer != null)
			return;

		skipElement = localName;
		skipdepth = 0;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (skipElement != null) {
			if (localName.equals(skipElement)) {
				if (skipdepth == 0) {
					if (!unknownSubtype && !unknownElements.contains(subtype + " - " + localName)) {
						System.out.println(name + " - unknown element: " + (subtypes ? subtype + " - " : "") + localName
								+ " = " + value.trim());
						unknownElements.add(subtype + " - " + localName);
					}
					skipElement = null;
				}
			} else
				skipdepth--;
			return;
		}

		if (subtypes && localName.equals(subtypeElement)) {
			AnnotationConfig subConfig = subtypeConfigs.get(value);
			if (subConfig != null) {
				subtype = value;
				config = subConfig;
				try {
					object = subConfig.getObjectClass().newInstance();
					for (CachedElement el : cache) {
						StringConsumer consumer = config.getValues().get(el.getElement());
						if (consumer != null) {
							try {
								consumer.accept(el.getValue());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}

			} else {
				if (!unknownSubtypes.contains(value))
					System.out.println(name + " - UNKNOWN SUBTYPE: " + value);
				unknownSubtype = true;
				unknownSubtypes.add(value);
			}
		}

		StringConsumer consumer = config.getValues().get(localName);
		if (consumer != null) {
			if (!subtypes || subtype != null) {
				try {
					consumer.accept(value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				cache.add(new CachedElement(localName, value));
			}
		} else {
			super.endElement(uri, localName, qName);
		}

	}

	@Override
	protected void consume() {
		try {
			if (consumer != null && (!subtypes || subtype != null))
				consumer.accept(object);
		} catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e1) {
			e1.printStackTrace();
		}
		try {
			if (subtypes) {
				config = baseConfig;
				unknownSubtype = false;
				subtype = null;
				cache.clear();
			}

			object = config.getObjectClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}

}