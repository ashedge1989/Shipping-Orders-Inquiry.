package org.odyssey.api;

import java.io.IOException;
import java.io.InputStream;

import org.odyssey.model.Order;
import org.xmlpull.v1.XmlPullParserException;

public interface IParser {

	public abstract Order parseXML(InputStream inputStream) throws XmlPullParserException, IOException;
	
}
