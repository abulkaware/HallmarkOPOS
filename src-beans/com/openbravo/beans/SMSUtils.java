/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.beans;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.StringBuilder;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author charlesbell
 */
public class SMSUtils {
    
    private SMSUtils() {}
    
    static final String LoginUrlModifier = "/login/%s/%s";
    static final String SingleTextUrlModifier = "/%s/send/sms/single";
    static final String GetAllContactsUrlModifier = "/%s/entity/addressbook.Contact?sortfield=null&sortDir=NONE&offset=0&limit=1000";
    static final String GetContactModfier = "/%s/entity/addressbook.Contact/%s";
    static final String UpdateContactModifier = "/%s/entity/addressbook.Contact/%s";

    static final String BaseApiUrl = "http://app.mobivatebulksms.com/bulksms/xmlapi";
    static final String Username = "info@hallmark-jewellers.com";
    static final String Password = "Fraser";
    
    static final String Originator = "Hallmark";
    static final String RouteId = "3d7c4a51-c68b-4ee4-b83b-822bf7eb293f";
    
    static String SingleTextDataBlock;
    static String UpdateContactInfo;
    
    static
    {
        SingleTextDataBlock = new StringBuilder()
                .append("xml=<message>")
                .append("<originator>%s</originator>")
                .append("<recipient>%s</recipient>")
                .append("<body>%s</body>")
                .append("<reference>test</reference>")
                .append("<routeId>%s</routeId>")
                .append("</message>").toString();

        UpdateContactInfo = new StringBuilder()
                .append("<id>%s</id>")
                .append("<birthday>%s</birthday>")
                .append("<custom1>%s</custom1>")
                .append("<custom2>%s</custom2>")
                .append("<custom3>%s</custom3>")
                .append("<custom4>%s</custom4>")
                .append("<custom5>%s</custom5>")
                .append("<email>%s</email>")
                .append("<firstName>%s</firstName>")
                .append("<lastName>%s</lastName>")
                .append("<mobile>%s</mobile>")
                .append("</contact>").toString();
    }
    
    private static String extractData(Document document, String xpathNode) throws XPathExpressionException
    {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        return xpath.evaluate(xpathNode, document);
    }

    private static String GetMobivateSessionId()
    {
        try 
        {
            String loginUri = String.format(LoginUrlModifier, URLEncoder.encode(Username, "ISO-8859-1"), Password);
            Document document = DoApiCall(loginUri, null);
            return extractData(document, "/xaresponse/session");
        }
        catch (XPathExpressionException | UnsupportedEncodingException ex)
        {
            return null;
        }

    }
    
    public static boolean SendText(String message, String recipient)
    {
        recipient = CleanupRecipient(recipient);
        String formattedData = String.format(SingleTextDataBlock, Originator, recipient, message, RouteId);
        String sessionId = GetMobivateSessionId();
        if (sessionId == null) return false;
        
        String sendTextUrl = String.format(SingleTextUrlModifier, sessionId);
        
        Document document = DoApiCall(sendTextUrl, formattedData);
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        return true; //document != null && xpath.evaluate("", document).equals("0") && xpath.evaluate("", document).equals("OK");

//        return result != null && result.Descendants("code").FirstOrDefault().Value == "0"
//               && result.Descendants("text").FirstOrDefault().Value == "OK";
    }

    private static Document DoApiCall(String requestUri, String requestXml)
    {
        URLConnection connection;
        try
        {
            URL url = new URL(BaseApiUrl + requestUri);
            connection = url.openConnection();
            connection.setRequestProperty("ContentType", "application/x-www-form-urlencoded");
            connection.setRequestProperty("User-Agent", "HALLMARK_STOCK_SYSTEM");
            connection.setDoOutput(true);
        }
        catch (IOException ex)
        {
            return null;
        }
        
        if (requestXml != null)
        {
            try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
                out.write(requestXml);
            }
            catch (IOException io)
            {
                return null;
            }
        }
        
        String response;
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
            String decodedString;
            StringBuilder responseBlock = new StringBuilder();

            while ((decodedString = in.readLine()) != null) {
                responseBlock.append(decodedString);
            }
            in.close();
            response = responseBlock.toString();
        }
        catch (IOException io)
        {
            return null;
        }
        
        Document doc;
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource source = new InputSource(new ByteArrayInputStream(response.getBytes("utf-8")));
            doc = db.parse(source);
        }
        catch (IOException | ParserConfigurationException | SAXException ex)
        {
            return null;
        }
        
        return doc;
        
        
                
//        var request = (HttpWebRequest)WebRequest.Create(HttpUtility.HtmlEncode(BaseApiUrl + requestUri));
//
//        request.ContentType = "application/x-www-form-urlencoded";
//        request.UserAgent = "HALLMARK_STOCK_SYSTEM";
//
//        if (requestXml != null && string.IsNullOrWhiteSpace(requestXml) == false)
//        {
//            request.Method = WebRequestMethods.Http.Post;
//            request.ContentLength = requestXml.Length;
//            using (StreamWriter writer = new StreamWriter(request.GetRequestStream()))
//            {
//                writer.Write(requestXml);
//                writer.Close();
//            }
//        }
//        else
//        {
//            request.Method = WebRequestMethods.Http.Get;
//        }
//
//        // probably need usings here
//        var response = request.GetResponse();
//        var responseString = string.Empty;
//        using (StreamReader reader = new StreamReader(response.GetResponseStream()))
//        {
//            responseString = reader.ReadToEnd();
//            response.Close();
//        }
//
//        return XDocument.Parse(responseString);
    }
    
    private static String CleanupRecipient(String recipient)
    {
        recipient = recipient.replaceAll("\\s", "").trim();

        if (recipient.startsWith("0") || recipient.startsWith("+"))
        {
            recipient = recipient.substring(1);
        }

        if (recipient.startsWith("44") == false)
        {
            recipient = "44" + recipient;
        }

        return recipient;
    }
    
}
