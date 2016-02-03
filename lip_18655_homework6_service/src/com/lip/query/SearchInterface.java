/**
 *
 */
package com.lip.query;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * @author Li Pei
 *
 * Andrew ID : lip
 */

@WebService
@SOAPBinding(style = Style.RPC)
public interface SearchInterface {
    @WebMethod
    public String[] basicSearch(String query, int numResultsToSkip, int numResultsToReturn);

    @WebMethod
    public String[] spatialSearch(String query, String startYear, String endYear, int numResultsToSkip,
            int numResultsToReturn);

}
