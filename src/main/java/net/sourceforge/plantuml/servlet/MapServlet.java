/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * Project Info:  https://plantuml.com
 *
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */
package net.sourceforge.plantuml.servlet;

import java.io.IOException;

import javax.imageio.IIOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.plantuml.FileFormat;

import net.sourceforge.plantuml.servlet.utility.UmlExtractor;

/**
 * MAP servlet of the webapp.
 * This servlet produces the image map of the diagram in HTML format.
 */
@SuppressWarnings("SERIAL")
public class MapServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // build the UML source from the compressed request parameter
        String uml = UmlExtractor.getUmlSource(getSource(request.getRequestURI()));

        // generate the response
        DiagramResponse dr = new DiagramResponse(response, getOutputFormat(), request);
        try {
            dr.sendMap(uml);
        } catch (IIOException e) {
            // Browser has closed the connection, do nothing
        }
        dr = null;
    }

    public String getSource(String uri) {
        String[] result = uri.split("/map/", 2);
        if (result.length != 2) {
            return "";
        } else {
            return result[1];
        }
    }

    /**
     * Gives the wished output format of the diagram.
     * This value is used by the DiagramResponse class.
     *
     * @return the format for map responses
     */
    public FileFormat getOutputFormat() {
        return FileFormat.UTXT;
    }

}
