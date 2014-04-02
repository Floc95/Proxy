package org.esgi.proxy.interfaces;

import java.io.IOException;
import java.util.HashMap;

public interface IHttpHandler {
	void execute(IRequestHttpHandler request, IResponseHttpHandler response, HashMap<String, ISession> sessions) throws IOException;
}
