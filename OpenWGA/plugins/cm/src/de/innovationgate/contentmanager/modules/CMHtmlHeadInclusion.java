/*******************************************************************************
 * Copyright 2009, 2010 Innovation Gate GmbH. All Rights Reserved.
 * 
 * This file is part of the OpenWGA server platform.
 * 
 * OpenWGA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * In addition, a special exception is granted by the copyright holders
 * of OpenWGA called "OpenWGA plugin exception". You should have received
 * a copy of this exception along with OpenWGA in file COPYING.
 * If not, see <http://www.openwga.com/gpl-plugin-exception>.
 * 
 * OpenWGA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OpenWGA in file COPYING.
 * If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package de.innovationgate.contentmanager.modules;

import de.innovationgate.utils.WGUtils;
import de.innovationgate.webgate.api.WGAPIException;
import de.innovationgate.webgate.api.WGContent;
import de.innovationgate.webgate.api.WGException;
import de.innovationgate.webgate.api.WGStructEntry;
import de.innovationgate.wgpublisher.webtml.utils.HTMLHeadInclusion;
import de.innovationgate.wgpublisher.webtml.utils.TMLContext;

public class CMHtmlHeadInclusion implements HTMLHeadInclusion {

    public CharSequence processInclusion(TMLContext context) {
        // Determine, which (if any) document can be edited in this request
        if (context.isbrowserinterface()) {

        	StringBuffer result = new StringBuffer();
        	WGContent content;
    		WGStructEntry structentry;
        	
        	try{
            	content = context.content();
        		structentry = content.getStructEntry();

				result.append("\n<script id=\"wga-cm-contentinfo\" type=\"text/javascript\">");
	        	result.append("\nWGA.contentinfo={");
	        	result.append("\n\tdbkey:\"" + content.getDatabase().getDbReference() + "\"");
	    		if(!content.isDummy()){
	    			result.append(",");
	    			result.append("\n\tstructkey:\"" + structentry.getStructKey() + "\",");
	    			result.append("\n\tcontentkey:\"" + content.getContentKey(true) + "\",");			
	    			result.append("\n\ttitle:\"" +  WGUtils.strReplace(WGUtils.strReplace(content.getTitle(), "\"", "\\\"", true), "script", "sc\"+\"ript", true));
	    			result.append("\",");
	    			result.append("\n\tlanguage:\"" + content.getLanguage().getName() + "\"");
	    		}
	    		result.append("\n}");
	    		result.append("\n</script>\n");
        	} catch (WGAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
        	
            return result.toString();
        }        
        else return null;
    }

}
